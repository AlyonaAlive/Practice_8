package com.example;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

@Transactional
public class WorkWithStudent {
	@Autowired
	private StudentsDao studentsDao;

	public Student update(final Student student) {
		Student createdAddressInfo = null;
		if (student != null) {
			final javax.persistence.EntityManagerFactory entityManagerFactory = EntityManagerFactory
					.getEntityManagerFactory();
			EntityManager entityManager = entityManagerFactory.createEntityManager();
			try {
				entityManager.getTransaction().begin();
				Student createdEntity = entityManager.merge(student);
				entityManager.getTransaction().commit();
			} catch (Exception ex) {
				
				entityManager.getTransaction().rollback();
			} finally {
				entityManager.close();
			}
		}
		return createdAddressInfo;
	}

	public Student addStudent(final Student student) {
		transactionTemplate.execute(new TransactionCallback<Void>() {
			public Void doInTransaction(TransactionStatus txStatus) {
				try {
					studentsDao.addStudent(student);
					System.out.println("Teacher has been added " + student);
				} catch (RuntimeException e) {
					txStatus.setRollbackOnly();
					throw e;
				}
				return null;
			}
		});
		return student;
	}

	@Autowired
	private TransactionTemplate transactionTemplate;

}
