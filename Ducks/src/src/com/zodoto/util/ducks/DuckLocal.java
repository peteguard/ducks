package com.zodoto.util.ducks;

/**
 * 
 * Thread safe class that is intended to live within a JVM
 * It doesn't persist the keys, which sacrifices persistence for speed
 * Each restart gets a new set of keys and loses any still in the cache
 * 
 * @author Pete Guard
 *
 */
public class DuckLocal {
	
	private DuckControl duckControl;
	
	/**
	 * Set up a Duck Local from a configuration file
	 * @param configurationFileName
	 * @throws Exception
	 */
	public DuckLocal initialize(String configurationFileName) throws Exception	{
		duckControl = new DuckMaker()
				.setConfigurationFileName(configurationFileName)
				.initialize()
				.getDuckControl();
		return this;
	}
	/**
	 * Set up a Duck Local from an array of configuration information
	 * @param configurationBody
	 * @throws Exception
	 */
	public DuckLocal initialize(String[] configurationBody) throws Exception	{
		duckControl = new DuckMaker()
				.setConfigurationBody(configurationBody)
				.initialize()
				.getDuckControl();
		return this;
	}


	/**
	 * Tell the controller to preload known keys so they are ready when needed
	 * @param name
	 */
	public void preLoad(String name)	{
		duckControl.preLoad(name);
	}

	/**
	 * Get a single key
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public DuckRange getOne(String name) throws Exception	{
		return duckControl.getOne(name);
	}
	
	/**
	 * Get a single key
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public DuckRange get(String name) throws Exception	{
		return duckControl.get(name);
	}
	
	/**
	 * Get a key range
	 * @param name
	 * @param maximumSize
	 * @return
	 * @throws Exception
	 */
	public DuckRange get(String name, int maximumSize) throws Exception	{
		return duckControl.get(name, maximumSize);
	}

}


