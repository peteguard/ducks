package com.zodoto.util.ducks;

public class SimpleLocal {

	private static String[] localConfig = {
			"identity=almond",
			"ducktype=local",
			"responsesize=10",
			"requestsize=100",
			"watermark=30",
			"waitmillis=100",
			"waitcount=30",
			"retries=3",
			"sourceurl=localhost",
			"sourceport=9757",
	};

	public static void main(String[] a) throws Exception	{
		
		DuckLocal duckLocal = new DuckLocal().initialize(localConfig);
		
		for(int x = 0; x < 1000; x++)	{
			for(int i = 0; i < 13; i++)	{
				System.out.println(duckLocal.get("Octavia"));
			}
			for(int i = 0; i < 9; i++)	{
				System.out.println(duckLocal.get("Harper"));
			}
			for(int i = 0; i < 17; i++)	{
				System.out.println(duckLocal.get("Clarke"));
			}
			for(int i = 0; i < 15; i++)	{
				System.out.println(duckLocal.get("Raven"));
			}
		}
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

