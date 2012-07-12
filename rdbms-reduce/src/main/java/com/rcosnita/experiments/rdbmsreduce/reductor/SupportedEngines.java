package com.rcosnita.experiments.rdbmsreduce.reductor;

import java.util.HashMap;
import java.util.Map;

/**
 * This is the enumeration containing all database supported engines of 
 * reduce algorithm. 
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 12.07.2012
 */
public enum SupportedEngines {
	MySQL(1);
	
	/**
	 * Class used to define constraints for each engine.
	 */
	class EngineConstraints {
		public final int MAX_INCLAUSE_LENGTH;
		
		public EngineConstraints(int maxInClause) {
			this.MAX_INCLAUSE_LENGTH = maxInClause;
		}
	}
	
	private final int engineId;
	private final Map<Integer, EngineConstraints> engineConstraints = new HashMap<Integer, EngineConstraints>();	
	
	
	SupportedEngines(int engineId) {
		this.engineId = engineId;
		
		engineConstraints.put(1, new EngineConstraints(1000));
	}
	
	/**
	 * 
	 * 
	 * @return The engine constraints for the current item.
	 * @throws EngineNotFound in case no constraints are defined
	 */
	EngineConstraints getEngineConstraints() {
		return engineConstraints.get(this.engineId);
	}
}
