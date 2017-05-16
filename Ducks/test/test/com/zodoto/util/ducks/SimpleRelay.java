package com.zodoto.util.ducks;

public class SimpleRelay {
	
	private static String[] relayConfig = {
			"identity=cashew",
			"ducktype=relay",
			"ducktype=local",
			"responsesize=100",
			"requestsize=1000",
			"watermark=300",
			"waitmillis=100",
			"waitcount=30",
			"retries=3",
			"sourceurl=localhost",
			"sourceport=9756",
			"listenport=9757",
			"persistfilename=/Users/Ducks/Data/Relay",
			"logfoldername=/Users/Ducks/Logs"
	};

	public static void main(String[] a) throws Exception	{
		
		new DuckServer().runDuckServer(relayConfig);
	}
}
