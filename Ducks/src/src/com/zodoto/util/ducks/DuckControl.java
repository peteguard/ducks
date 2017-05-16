package com.zodoto.util.ducks;

/**
 * 
 * Describes the central controller for a duck key source.
 * 
 * @author Pete Guard
 *
 */
public interface DuckControl {
	
	/**
	 * Provides the controller with the configuration
	 * @param duckConfiguration Duck configuration object
	 * @return This object
	 */
	public DuckControl setDuckConfiguration(DuckConfiguration duckConfiguration);
	
	/**
	 * Set the logger
	 * @param duckLog The logger
	 * @return This object
	 */
	public DuckControl setDuckLog(DuckLog duckLog);

	/**
	 * Provides the controller with the persistence object
	 * @param duckPersist Duck persist service
	 * @return This object
	 */
	public DuckControl setDuckPersist(DuckPersist duckPersist);
	
	/**
	 * Provides the controller with the source object (where we get more keys)
	 * @param duckSource Duck source service
	 * @return This object
	 */
	public DuckControl setDuckSource(DuckSource duckSource);
	
	/**
	 * Load the keys from persistent store
	 * @return This object
	 * @throws Exception Error
	 */
	public DuckControl initialize() throws Exception;
	
	/**
	 * After startup have the Duck preload some known keys to prime the keysource
	 * Makes the first retrieve move faster
	 * @param name Name of the key set
	 * @return This object
	 */
	public DuckControl preLoad(String name);

	/**
	 * Get a single key
	 * @param name Name of key set
	 * @return Duck Range containing a single key 
	 * @throws Exception Error
	 */
	public DuckRange getOne(String name) throws Exception;
	
	/**
	 * Get a range of keys using the default response size
	 * @param name Name of key set
	 * @return Duck Range containing a range of keys
	 * @throws Exception Error
	 */
	public DuckRange get(String name) throws Exception;
	
	/**
	 * Get a key range up to the default response size
	 * @param name Name of the key set
	 * @param maximumSize Maximum number of keys to return
	 * @return Duck Range containing a range of keys
	 * @throws Exception Error
	 */
	public DuckRange get(String name, int maximumSize) throws Exception;
	
	/**
	 * Call back from source
	 * @param name Name of the key set to be modified
	 * @param duckRange key set
	 */
	public void updateEgg(String name, DuckRange duckRange);
	
}
