package com.zodoto.util.ducks;

/**
 * 
 * Connection to the remote server
 * 
 * @author Pete Guard
 *
 */
public interface DuckSourceRemote {

	/**
	 * Provides the controller with the configuration
	 * @param duckConfiguration
	 * @return This object
	 */
	public DuckSourceRemote setDuckConfiguration(DuckConfiguration duckConfiguration);
	
	/**
	 * Sets the security object
	 * @param duckSecurity Object to encrypt and decrypt 
	 * @return This object
	 */
	public DuckSourceRemote setDuckSecurity(DuckSecurity duckSecurity);
	
	/**
	 * Sets the logger object
	 * @param duckLog Logger
	 * @return This object
	 */
	public DuckSourceRemote setDuckLog(DuckLog duckLog);
	
	/**
	 * Extracts needed information from the configuration
	 */
	public DuckSourceRemote initialize() throws Exception;
	
	/**
	 * Retrieves a range of keys from another duck
	 * @param duckRequest Requested key range
	 * @return Response from source
	 * @throws Exception Error
	 */
	public DuckRange get(DuckRequest duckRequest) throws Exception;
}
