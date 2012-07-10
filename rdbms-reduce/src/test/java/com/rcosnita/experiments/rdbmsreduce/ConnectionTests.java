package com.rcosnita.experiments.rdbmsreduce;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Test;

import com.rcosnita.experiments.rdbmsreduce.sessions.JPABuilder;

/**
 * This is a suite of integration tests that ensure the connection is
 * correctly working. 
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 10.07.2012
 */
public class ConnectionTests {
	/**
	 * Method used to test jpa connections to available data sources.
	 */
	@Test
	public void testJPAConnection() {
		EntityManager em = JPABuilder.getEntityManager(JPABuilder.STORE);
		
		em.close();
		
		em = JPABuilder.getEntityManager(JPABuilder.PROVISIONING);
		
		em.close();
	}
}
