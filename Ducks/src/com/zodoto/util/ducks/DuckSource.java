package com.zodoto.util.ducks;

/**
 * 
 * Used by a Duck Controller to contact a source Duck for another key range 
 * 
 * @author Pete Guard
 *
 */
public interface DuckSource  {

	/**	
	 * Set the configuration.
	 * @param duckConfiguration
	 * @return
	 */
	public DuckSource setDuckConfiguration(DuckConfiguration duckConfiguration);

	/**
	 * Set the object that talks to the remote source.
	 * @param duckSourceRemote
	 * @return
	 */
	public DuckSource setDuckSourceRemote(DuckSourceRemote duckSourceRemote);
	
	/**
	 * Requests a new range from the source.  If the step takes too long it returns a timeout.
	 * @param name
	 * @param maximumSize
	 * @return
	 */
	public DuckStatus get(String name, DuckControl duckControl, int maximumSize);

	/**
	 * Request the duck to get an additional range without waiting for the response.
	 * @param name
	 * @param duckControl
	 */
	public void refresh(String name, DuckControl duckControl);
}
