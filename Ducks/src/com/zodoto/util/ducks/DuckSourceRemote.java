package com.zodoto.util.ducks;

/**
 * 
 * Connection to the remote server
 *
 */
public interface DuckSourceRemote {

	/**
	 * Provides the controller with the configuration
	 * @param duckConfiguration
	 * @return
	 */
	public DuckSourceRemote setDuckConfiguration(DuckConfiguration duckConfiguration);
	
	/**
	 * Extracts needed information from the configuration
	 */
	public DuckSourceRemote initialize() throws Exception;
	
	/**
	 * Retrieves a range of keys from another duck
	 * @param name
	 * @param maximumSize
	 * @return
	 * @throws Exception
	 */
	public DuckRange get(String name, int maximumSize) throws Exception;
}
