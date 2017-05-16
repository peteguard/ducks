package com.zodoto.util.ducks;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;

/**
 * Writes log messages to a file
 * Rolls over the log files each day
 * 
 * @author Pete Guard
 *
 */
public class DuckLogFile extends DuckLogBase {
	
	private final static SimpleDateFormat dateformat = new SimpleDateFormat("_yyyyMMdd");
	
	private PrintStream logFile;
	
	protected void setup() throws Exception	{
		
		String fileName = 
				duckConfiguration.getLogFolderName() 
				+ System.getProperty("file.separator")
				+ "Duck" 
				+ identifyLog()
				+ dateformat.format(System.currentTimeMillis());
		
		logFile = new PrintStream(new FileOutputStream(fileName, true)); 
		
		//	close the log file on exit
		Runtime.getRuntime().addShutdownHook(new Thread()	{
			public void run()	{
				logFile.flush();
				logFile.close();
			}
		});
	}
	
	@Override
	protected void output(String message) {
		synchronized(logFile)	{
			logFile.println(message);
		}
	}
	
	private String identifyLog()	{
		String identity = duckConfiguration.getIdentity();
		if(identity == null || identity.length() == 0)	{
			return "";
		}
		return "_" + identity;
	}
}
