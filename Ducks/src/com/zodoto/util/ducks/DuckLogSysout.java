package com.zodoto.util.ducks;

public class DuckLogSysout extends DuckLogBase{

	/**
	 * Forwards the formatted message to system out
	 * @param message
	 */
	@Override
	protected void output(String message) {
		System.out.println(message);
	}

	@Override
	protected void setup() throws Exception {
		
	}

}
