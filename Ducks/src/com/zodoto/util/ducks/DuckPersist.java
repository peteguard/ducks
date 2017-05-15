package com.zodoto.util.ducks;

import java.util.List;

/**
 * 
 * Persists Duck Map to permanent storage 
 * 
 * @author Pete Guard
 *
 */
public interface DuckPersist {
	
	/**
	 * Provides the controller with the configuration
	 * @param duckConfiguration
	 * @return
	 */
	public DuckPersist setDuckConfiguration(DuckConfiguration duckConfiguration);

	/**
	 * Retrieves the entire set of keys known to this duck
	 * @return
	 */
	public List<DuckData> getAll() throws Exception;
	
	/**
	 * Saves a duck range, must do each time one changes
	 * @param duckData
	 */
	public void save(DuckData duckData) throws Exception;
}
