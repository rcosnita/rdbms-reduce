package com.rcosnita.experiments.rdbmsreduce.examples;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Class used to provide helper meth
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 13.07.2012
 */
public class DomainsUtils {
	private final static Logger logger = Logger.getLogger(DomainsUtils.class);
	
	/**
	 * Method used to display a given list of domains.
	 * 
	 * @param results
	 */
	public static void displayDomains(List<Map<String, Object>> results) {
		logger.info(String.format("Displaying %s domains.", results.size()));
		
		for(Map<String, Object> domain : results) {
			logger.info(String.format("Domain ----> %s", domain.get("name")));
		}
	}

}