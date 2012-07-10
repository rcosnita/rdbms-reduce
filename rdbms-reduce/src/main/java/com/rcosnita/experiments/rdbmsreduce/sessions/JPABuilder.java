package com.rcosnita.experiments.rdbmsreduce.sessions;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Class used to provide methods for easily accessing defined jpa.
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 10.07.2012
 */
public class JPABuilder {
	/**
	 * Method used to obtain an entity manager instance for a given persistence unit.
	 *  
	 * @param persistenceUnit The name of the persistence unit we want to obtain.
	 * @return
	 */
	public static EntityManager getEntityManager(String persistenceUnit) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnit);
			
		return emf.createEntityManager();
	}
}
