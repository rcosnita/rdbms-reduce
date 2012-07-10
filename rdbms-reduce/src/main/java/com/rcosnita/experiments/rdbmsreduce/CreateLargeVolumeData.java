package com.rcosnita.experiments.rdbmsreduce;

import javax.persistence.EntityManager;

import com.rcosnita.experiments.rdbmsreduce.sessions.JPABuilder;

/**
 * Class used to generate a large amount of data required for our 
 * distributed proof of concept.
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 10.07.2012
 */
public class CreateLargeVolumeData {
	private final static int PROVISIONING_IDS = 20000;
	private final static Integer[] ACCOUNT_IDS = new Integer[] {
		1000, 1001, 1002, 1003, 1004, 1005
	};
	
	/**
	 * Method used to generate a large number of provisioning ids.
	 */
	public void generateProvisioningIds() {
		EntityManager provEm = JPABuilder.getEntityManager(JPABuilder.PROVISIONING);
		
		provEm.close();
	}
}
