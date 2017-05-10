package com.zodoto.util.ducks;

/**
 * 
 * Status of a request response
 * Success: Worked, we have a key range
 * Empty: We have no keys
 * Timeout: Ran out of time waiting response
 * Failure: Error occurred trying to get a key range
 *
 */
public enum DuckStatus {
	
	SUCCESS ("Success"), 	//	Retrieved a duck range
	EMPTY ("Empty"), 		//	There is no duck range
	TIMEOUT ("Timeout"), 	//	Timed out waiting for a response
	FAILURE ("Failure"); 	//	Request returned failure
	
	DuckStatus(String name)	{
		this.name = name;
	}
	
	private final String name;
	
	/**
	 * Gets the text version if this enum
	 * @return
	 */
	public String value()	{
		return name;
	}
}
