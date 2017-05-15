package com.zodoto.util.ducks;

public class DuckLogTest {

	public static void main(String[] args) throws Exception	{
		
		
		DuckConfiguration duckConfiguration = new DuckConfiguration()
				.setLogFolderName("/Users/Ducks/Logs")
				.setIdentity("Soozan");
		
		DuckLog duckLog = new DuckLogFile()
				.setDuckConfiguration(duckConfiguration)
				.initialize();
		
		duckLog.log("Jello Whirled");
	}
}
