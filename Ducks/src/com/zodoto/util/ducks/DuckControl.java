package com.zodoto.util.ducks;

public interface DuckControl {
	
	/**
	 * Provides the controller with the configuration
	 * @param duckConfiguration
	 * @return
	 */
	public DuckControl setDuckConfiguration(DuckConfiguration duckConfiguration);

	/**
	 * Provides the controller with the persistence object
	 * @param duckPersist
	 * @return
	 */
	public DuckControl setDuckPersist(DuckPersist duckPersist);
	
	/**
	 * Provides the controller with the source object (where we get more keys)
	 * @param duckSource
	 * @return
	 */
	public DuckControl setDuckSource(DuckSource duckSource);
	
	/**
	 * Load the keys from persistent store
	 * @throws Exception
	 */
	public DuckControl initialize() throws Exception;
	
	/**
	 * After startup have the Duck preload some known keys to prime the keysource
	 * Makes the first retrieve move faster
	 * @param name
	 */
	public DuckControl preLoad(String name);

	/**
	 * Get a single key
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public DuckRange getOne(String name) throws Exception;
	
	/**
	 * Get a single key
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public DuckRange get(String name) throws Exception;
	
	/**
	 * Get a key range
	 * @param name
	 * @param maximumSize
	 * @return
	 * @throws Exception
	 */
	public DuckRange get(String name, int maximumSize) throws Exception;
	
	/**
	 * Call back from source
	 * @param name
	 * @param duckRange
	 */
	public void updateEgg(String name, DuckRange duckRange);
	
}
