package com.rcosnita.experiments.rdbmsreduce.examples;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.rcosnita.experiments.rdbmsreduce.reductor.Reductor;
import com.rcosnita.experiments.rdbmsreduce.reductor.SupportedEngines;
import com.rcosnita.experiments.rdbmsreduce.sessions.JPABuilder;

/**
 * Class that simulates how top n domains can be retrieved for a given tld.
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 13.07.2012
 */
public class TopDomainsTld {
	private final static Logger logger = Logger.getLogger(TopDomainsTld.class);
	
	public static void main(String[] args) {
		if(args.length != 3) {
			throw new RuntimeException("Invalid usage... Ex: java com.rcosnita.experiments.rdbmsreduce.examples.TopDomains <account_id> <number_of_domains> <tld>");
		}
		
		int accountId = Integer.parseInt(args[0]);
		int maxDomains = Integer.parseInt(args[1]);
		String tld = args[2];
		
		long startTime = Calendar.getInstance().getTimeInMillis();
		
		List<Integer> provIds = JPABuilder.getProvisioningIds(accountId);
		
		String sql = "SELECT * FROM domains WHERE name LIKE '%%(tld)%' AND prov_id IN (%(prov_ids)) ORDER BY name ASC LIMIT 0,%(max_domains)";
		
		Reductor reductor = new Reductor(14, SupportedEngines.MySQL);
		
		Map<String, Object> sqlValues = new HashMap<String, Object>();
		sqlValues.put("tld", ".co.uk");
		sqlValues.put("max_domains", maxDomains);		
		TopDomains topDomains = new TopDomains(reductor, sql, sqlValues, maxDomains, provIds);
		
		long startTime2 = Calendar.getInstance().getTimeInMillis(); 
		List<Map<String, Object>> domains = topDomains.getTopDomains();
		long endTime2 = Calendar.getInstance().getTimeInMillis();
		
		logger.info(String.format("Get top domains operation took %s milliseconds.", (endTime2 - startTime2)));		
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		
		logger.info(String.format("Top domains use case took %s milliseconds.", (endTime - startTime)));
		
		DomainsUtils.displayDomains(domains);
		
		System.exit(0);		
	}
}
