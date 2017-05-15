package com.zodoto.util.ducks;

/**
 * 
 * Describes a simple logger to events such as requests, responses, and any errors.
 * 
 * @author Pete Guard
 *
 */
public interface DuckLog {

	/**
	 * Sets the configuration file
	 * @param duckConfiguration Configuration
	 * @return This object
	 */
	public DuckLog setDuckConfiguration(DuckConfiguration duckConfiguration);
	
	/**
	 * Do any set up prior to logging
	 * @return This object
	 */
	public DuckLog initialize() throws Exception;
	
	/**
	 * Formats and writes a text message
	 * @param message Text message to be written to log
	 */
	public void log(String message);
	
	/**
	 * Formats and writes a text message along with a stack trace
	 * @param message Text message to be written to log
	 * @param e Exception for stack trace
	 */
	public void log(String message, Exception e);
	
	/**
	 * Logs a duck request to be sent
	 * @param duckRequest Request to be logged
	 */
	public void logSentRequest(DuckRequest duckRequest);

	/**
	 * Logs a received duck request
	 * @param duckRequest Request to be logged
	 */
	public void logReceivedRequest(DuckRequest duckRequest);

	/**
	 * Logs a duck range to be sent
	 * @param duckRange Duck range to be logged
	 */
	public void logSentRange(DuckRange duckRange);

	/**
	 * Logs a received duck range
	 * @param duckRange Duck range to be logged
	 */
	public void logReceivedRange(DuckRange duckRange);

}
