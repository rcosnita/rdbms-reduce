package com.rcosnita.experiments.rdbmsreduce.sessions;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

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
	private static final Map<String, EntityManagerFactory> managers = 
			new HashMap<String, EntityManagerFactory>();
	
	/**
	 * Method used to obtain an entity manager instance for a given persistence unit.
	 *  
	 * @param persistenceUnit The name of the persistence unit we want to obtain.
	 * @return
	 */
	public synchronized static EntityManager getEntityManager(String persistenceUnit) {
		EntityManagerFactory emf = null;
		
		if(managers.containsKey(persistenceUnit)) {
			emf = managers.get(persistenceUnit);
		}
		else {
			emf = Persistence.createEntityManagerFactory(persistenceUnit);
			managers.put(persistenceUnit, emf);
		}
			
		return emf.createEntityManager();
	}
	
	static {
		/**
		 * I add the cleanup of entity manager factories currently opened. 
		 */
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				for(EntityManagerFactory emf : managers.values()) {
					emf.close();
				}
			}
		});
	}
}
