package com.zodoto.util.ducks;

/**
 * Constants 
 * 
 * @author Pete Guard
 *
 */
public class DuckConstants {

	/**
	 * Separator for fields in the messages.  Can be any String provided it is consistent and is not a number or is used in the key name.
	 */
	public final static String DIVIDER		= ";";
	
	/**
	 * A key range was successfully acquired from the source
	 */
	public final static String SUCCESS		= "SUCCESS";
	
	/**
	 * A key range was successfully acquired, but it is empty
	 */
	public final static String EMPTY		= "EMPTY";
	
	/**
	 * Either the requester or the respondent ran out of time before getting the key range
	 */
	public final static String TIMEOUT		= "TIMEOUT";
	
	/**
	 * An error occurred.  See log for more details
	 */
	public final static String FAILURE		= "FAILURE";
}
