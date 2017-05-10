package com.zodoto.util.ducks;

public class DuckServer {

	public static final void main(String[] a)	{
		try {
			if(a.length == 1)	{
				new DuckServer().runDuckServer(a[0]);
			}
			else	{
				
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void runDuckServer(String configurationFileName) throws Exception	{
		runListener(new DuckMaker().setConfigurationFileName(configurationFileName));
	}
	
	public void runDuckServer(String[] configurationBody) throws Exception	{
		runListener(new DuckMaker().setConfigurationBody(configurationBody));
	}

	private void runListener(DuckMaker duckMaker) throws Exception	{
		duckMaker.initialize();
		
		new DuckSocketListener()
			.setDuckConfiguration(duckMaker.getDuckConfiguration())
			.setDuckControl(duckMaker.getDuckControl())
			.initialize()
			.go();
	}
}

