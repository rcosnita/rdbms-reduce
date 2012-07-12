package com.rcosnita.experiments.rdbmsreduce.reductor;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
	 * Method used to provide join values of a list into strings. They
	 * are separated by a given character.
	 * 
	 * @param values
	 * @param delimitator
	 * @return
	 */
	private static String joinStrings(List<Integer> values, String delimitator) {
		StringBuffer buffer = new StringBuffer();
		
		for(Integer value : values) {
			buffer.append(value);
			buffer.append(delimitator);
		}
		
		return buffer.substring(0, buffer.length()).toString();
	}
	
	/**
	 * Many thanks to http://stackoverflow.com/questions/2286648/named-placeholders-in-string-formatting
	 * 
     * An interpreter for strings with named placeholders.
     *
     * For example given the string "hello %(myName)" and the map <code>
     *      <p>Map<String, Object> map = new HashMap<String, Object>();</p>
     *      <p>map.put("myName", "world");</p>
     * </code>
     *
     * the call {@code format("hello %(myName)", map)} returns "hello world"
     *
     * It replaces every occurrence of a named placeholder with its given value
     * in the map. If there is a named place holder which is not found in the
     * map then the string will retain that placeholder. Likewise, if there is
     * an entry in the map that does not have its respective placeholder, it is
     * ignored.
     *
     * @param str
     *            string to format
     * @param values
     *            to replace
     * @return formatted string
     */
    public static String formatString(String str, Map<String, Object> values) {

        StringBuilder builder = new StringBuilder(str);

        for (Entry<String, Object> entry : values.entrySet()) {

            int start;
            String pattern = "%(" + entry.getKey() + ")";
            String value = entry.getValue().toString();

            // Replace every occurence of %(key) with value
            while ((start = builder.indexOf(pattern)) != -1) {
                builder.replace(start, start + pattern.length(), value);
            }
        }

        return builder.toString();
    }	
	
	/**
	 * Method used to reduce the resultset for a given sql in multithread approach. 
	 * The number of ids will be divided into smaller ranges so that the inClause
	 * gets executed correctly by the database we are currently working with. 
	 * 
	 * @param conn The current sql connection we are using.
	 * @param sql The current sql statement that we want to use for our problem (it must not include the ids in clause). It 
	 * 	is mandatory that the last 
	 * @param sqlValues Map containing all named parameters from sql.

	 * @return A List of rows combined after the reduce occurred. Each entry has a map in which keys are the name of the columns from the sql statement.  
	 */
	public List<Map<String, Object>> reduce(Connection conn, String sql, List<Integer> ids, Map<String, Object> sqlValues) throws Exception {
		logger.info(String.format("Reducing %s provisioning ids for statement %s.", sql, ids.size()));
		
		SupportedEngines.EngineConstraints constraints = this.engine.getEngineConstraints();
		int requiredReduceOps = (int)Math.ceil((float)ids.size() / constraints.MAX_INCLAUSE_LENGTH);
				
		ExecutorService executor = Executors.newFixedThreadPool(maxThreads);
		
		List<Future<List<Map<String, Object>>>> queued = new ArrayList<Future<List<Map<String,Object>>>>();
		List<Map<String, Object>> results = new ArrayList<Map<String,Object>>();
		
		long startDate = Calendar.getInstance().getTimeInMillis();
		
		for(int i = 0; i < requiredReduceOps; i++) {
			int startIndex = i * constraints.MAX_INCLAUSE_LENGTH; 
			int endIndex = startIndex + constraints.MAX_INCLAUSE_LENGTH;
			
			List<Integer> idsSlice = ids.subList(startIndex, endIndex);
			String idsStr = joinStrings(idsSlice, ",");
			
			Map<String, Object> reduceValues = new HashMap<String, Object>();
			reduceValues.putAll(sqlValues);
			reduceValues.put("prov_ids", idsStr);
			
			String sqlReduce = formatString(sql, reduceValues);
			
			ReduceThread reduceTask = new ReduceThread(conn, sqlReduce);
			queued.add(executor.submit(reduceTask));
		}
		
		for(Future<List<Map<String, Object>>> task : queued) {
			List<Map<String, Object>> rows = task.get();
			
			results.addAll(rows);
		}
		
		long endDate = Calendar.getInstance().getTimeInMillis();
		
		logger.info(String.format("Reduce algorithm took %s milliseconds for query %s.", 
						(endDate - startDate), sql));
		
		return results;
	}	
}