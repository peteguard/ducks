package com.zodoto.util.ducks;

public class SampleSuper {
	
	private static String[] superConfig = {
			"ducktype=super",
			"responsesize=1000",
			"persistfilename=/Users/Ducks/Data/Super",
			"logfoldername=/Users/Ducks/Logs",
			"listenport=9756"
	};

	public static void main(String[] a) throws Exception	{
		
		new DuckServer().runDuckServer(superConfig);
	}
}

/*

			"identity=",
			"ducktype=super",
			"requestsize=",
			"responsesize=1000",
			"watermark=",
			"waitmillis=",
			"waitcount=",
			"retries=",
			"persistfilename=/Users/peteguard/Desktop/Ducks/Super000",
			"sourceurl=",
			"sourceport=",
			"listenport=9756",
			"logfoldername=",
			"silent="

*/

