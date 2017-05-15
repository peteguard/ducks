package com.zodoto.util.ducks;

import java.text.SimpleDateFormat;

public abstract class DuckLogBase implements DuckLog {
	
	private final static SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd.HHmmss.SSS");
	
	protected DuckConfiguration duckConfiguration;
	
	/**
	 * Set the configuration
	 * @param duckConfiguration Configuration
	 * @return This Object
	 */
	public DuckLog setDuckConfiguration(DuckConfiguration duckConfiguration) {
		this.duckConfiguration = duckConfiguration;
		return this;
	}
	
	protected abstract void setup() throws Exception;
	
	/**
	 * Forwards the formatted message to destination, system out, file, etc
	 * @param message
	 */
	protected abstract void output(String message);
	
	@Override
	public DuckLog initialize() throws Exception	{
		setup();
		return this;
	}

	@Override
	public void log(String message)	{
		output(dateTimestamp() + "|" + process() + "|" + message);
	}

	@Override
	public void log(String message, Exception e) {
		log(message);
		for(StackTraceElement ste : e.getStackTrace())	{
			log(ste.toString());
		}
	}

	@Override
	public void logSentRequest(DuckRequest duckRequest) {
		log("Send|" + format(duckRequest));
	}

	@Override
	public void logReceivedRequest(DuckRequest duckRequest) {
		log("Receive|" + format(duckRequest));
	}

	@Override
	public void logSentRange(DuckRange duckRange) {
		log("Send|" + format(duckRange));
	}

	@Override
	public void logReceivedRange(DuckRange duckRange) {
		log("Receive|" + format(duckRange));
	}

	
	private String format(DuckRequest duckRequest)	{
		return "Request|id=" + duckRequest.getIdentity() + "|name=" + duckRequest.getName() + "|size=" + duckRequest.getRequestedSize();
	}
	
	private String format(DuckRange duckRange){
		return "Range|status=" + duckRange.getStatus() + "|start=" + duckRange.getStartKey() + "|end=" + duckRange.getEndKey();
	}
	
	private String dateTimestamp()	{
		return dateformat.format(System.currentTimeMillis());
	}
	
	private String process()	{
		return Thread.currentThread().getName();
	}
	
	
}
