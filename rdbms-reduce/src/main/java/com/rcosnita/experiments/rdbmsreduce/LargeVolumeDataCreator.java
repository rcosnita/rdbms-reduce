package com.rcosnita.experiments.rdbmsreduce;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.log4j.Logger;

import com.rcosnita.experiments.rdbmsreduce.entities.ProvisioningItem;
import com.rcosnita.experiments.rdbmsreduce.sessions.JPABuilder;

/**
 * Class used to generate a large amount of data required for our 
 * distributed proof of concept.
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 10.07.2012
 */
public class LargeVolumeDataCreator {
	private final static Logger logger = Logger.getLogger(LargeVolumeDataCreator.class);
	
	/**
	 * Method used to generate a large number of provisioning ids.
	 */
	public void generateProvisioningIds(int maxProvIds, List<Integer> accountIds) {
		logger.info(String.format("Generating total %s provisioning items for %s accounts.", 
				maxProvIds, accountIds.size()));
		
		EntityManager provEm = null;
		EntityTransaction tran = null;
		
		try {
			provEm = JPABuilder.getEntityManager(JPABuilder.PROVISIONING);
			
			tran = provEm.getTransaction();
			tran.begin();
			
			this.insertProvisioningItems(maxProvIds, accountIds, provEm);
			
			tran.commit();	
		}
		finally {
			if(provEm != null) {
				provEm.close();
			}
			
			if(tran != null && tran.isActive()) {
				tran.rollback();
			}
		}
	}
	
	/**
	 * Method used to generate and insert provisioning items into database..
	 *
	 * @param maxProvIds The maximum number of provisioning items we want to insert.
	 * @param accountIds The account identifiers under which we insert the provisioning items.
	 * @param em This is the entity manager we want to use for persistence.
	 */
	private void insertProvisioningItems(int maxProvIds, List<Integer> accountIds, EntityManager em) {				
		int idsPerAccount = maxProvIds / accountIds.size(); 
		
		logger.info(String.format("Inserting %s provisioning ids per account.", idsPerAccount));		
		
		for(Integer accountId : accountIds) {
			for(int i = 0; i < idsPerAccount; i++) {
				ProvisioningItem item = new ProvisioningItem();
				
				int provId = Integer.valueOf("" + i + accountId); 
				
				item.setAccountId(accountId);
				item.setProvType(1);
				item.setProvId(provId);
				
				em.persist(item);
			}
		}
	}
	
	private void
	
	public static void main(String[] args) {
		LargeVolumeDataCreator generator = new LargeVolumeDataCreator();
		
		int maxProvIds = 400000;
		
		List<Integer> accountIds = new ArrayList<Integer>();
		
		for(int i = 100; i < 130; i++) {
			accountIds.add(i);
		}
		
		generator.generateProvisioningIds(maxProvIds, accountIds);
	}
}