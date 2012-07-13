package com.rcosnita.experiments.rdbmsreduce.sessions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

/**
 * Class used to provide methods for easily accessing defined jpa.
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 10.07.2012
 */
public class JPABuilder {
	private final static Logger logger = Logger.getLogger(JPABuilder.class);
	
	private static final Map<String, EntityManagerFactory> managers = 
			new HashMap<String, EntityManagerFactory>();
	
	public static final String PROVISIONING = "provisioning-jpa";
	public static final String STORE = "store1-jpa";
	
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
	
	/**
	 * Method used to obtain the provisioning ids for a specified account.
	 * 
	 * @param accountId
	 * @return
	 */
	public static List<Integer> getProvisioningIds(final Integer accountId) {
		logger.info(String.format("Obtaining provisioning ids for account %s", accountId));
		
		EntityManager em = JPABuilder.getEntityManager(JPABuilder.PROVISIONING);		
		
		final long startDate = Calendar.getInstance().getTimeInMillis();
		
		final List<Integer> provIds = new ArrayList<Integer>();
		
		Session session = em.unwrap(Session.class); 
		
		session.doWork(new Work() {			
			@Override
			public void execute(Connection conn) throws SQLException {
				PreparedStatement stmt = conn.prepareStatement("SELECT prov_id FROM items WHERE account_id = ?");
				stmt.setInt(1, accountId);
				
				ResultSet res = stmt.executeQuery();
				
				long endDate = Calendar.getInstance().getTimeInMillis();
				
				logger.info(String.format("Query of provids execution took %s milliseconds.", (endDate - startDate)));
				
				while(res.next()) {
					provIds.add(res.getInt("prov_id"));
				}
			}
		});
		
		long endDate = Calendar.getInstance().getTimeInMillis();
		
		logger.info(String.format("Provisioning ids retrieval took %s milliseconds.", (endDate - startDate)));
		
		return provIds;
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
