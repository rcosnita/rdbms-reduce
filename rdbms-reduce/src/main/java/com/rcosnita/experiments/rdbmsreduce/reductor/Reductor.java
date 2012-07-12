package com.rcosnita.experiments.rdbmsreduce.reductor;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Class used to provide reduce algorithm for relational environment.
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 11.07.2012
 */
public class Reductor {
	private final static Logger logger = Logger.getLogger(Reductor.class);
	
	private int maxThreads; 
	private SupportedEngines engine;
	
	public Reductor(int maxThreads) {
		this(maxThreads, SupportedEngines.MySQL);
	}
	
	public Reductor(int maxThreads, SupportedEngines engine) {
		this.maxThreads = maxThreads;
		this.engine = engine;
	}
	
	public int getMaxThreads() {
		return this.maxThreads;
	}

	/**
	 * Method used to reduce the resultset for a given sql in multithread approach. 
	 * The number of ids will be divided into smaller ranges so that the inClause
	 * gets executed correctly by the database we are currently working with. 
	 * 
	 * @param conn The current sql connection we are using.
	 * @param sql The current sql statement that we want to use for our problem (it must not include the ids in clause).
	 * @param idsClause This is the clause we want to use for identifying the items included in each reduce algorithm.
	 * @param args This is a list containing optional arguments that we want to pass to the sql statement.

	 * @return A List of rows combined after the reduce occurred. Each entry has a map in which keys are the name of the columns from the sql statement.  
	 */
	public List<Map<String, Object>> reduce(Connection conn, String sql, String idsClause, List<Integer> ids, String ... args) {
		logger.info(String.format("Reducing %s provisioning ids for statement %s.", sql, ids.size()));
		
		SupportedEngines.EngineConstraints constraints = this.engine.getEngineConstraints();
		
		
		
		return null;
	}
}
