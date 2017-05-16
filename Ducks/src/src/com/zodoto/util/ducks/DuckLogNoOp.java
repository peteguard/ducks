package com.zodoto.util.ducks;

/**
 * Logger that silently discards messages
 * 
 * @author Pete Guard
 *
 */
public class DuckLogNoOp implements DuckLog {

	@Override
	public DuckLog setDuckConfiguration(DuckConfiguration duckConfiguration)	{
		return this;
	}
	
	@Override
	public DuckLog initialize()	{
		return this;
	}
	
	@Override
	public void log(String message) {	}

	@Override
	public void log(String message, Exception e) {	}

	@Override
	public void logSentRequest(DuckRequest duckRequest) {	}

	@Override
	public void logReceivedRequest(DuckRequest duckRequest) {	}

	@Override
	public void logSentRange(DuckRange duckRange) {	}

	@Override
	public void logReceivedRange(DuckRange duckRange) { }
}
