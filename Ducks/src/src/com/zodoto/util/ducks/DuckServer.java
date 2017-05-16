package com.zodoto.util.ducks;

/**
 * Entry point to a duck server, which can be either the source of the keys or a relay that holds sets of keys to be distributed.
 * This class can be run stand alone by executing the class from the command.  It expects one argument, the name of the configuration file.
 * This class can also be run from within another class, using either the method that receives the name of the configuration file
 * or the method that takes a configuration as an array.
 * 
 * @author Pete Guard
 *
 */
public class DuckServer {

	/**
	 * Entry point to the server.
	 * @param a
	 */
	public static final void main(String[] a)	{
		try {
			if(a.length == 1)	{
				new DuckServer().runDuckServer(a[0]);
			}
			else	{
				System.out.println("Missing configuration file name command line argument");
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Entry point that takes the name of a file that holds the configuration
	 * @param configurationFileName
	 * @throws Exception
	 */
	public void runDuckServer(String configurationFileName) throws Exception	{
		runListener(new DuckMaker().setConfigurationFileName(configurationFileName));
	}
	
	/**
	 * Entry point that takes an array of strings for the configuration
	 * @param configurationBody
	 * @throws Exception
	 */
	public void runDuckServer(String[] configurationBody) throws Exception	{
		runListener(new DuckMaker().setConfigurationBody(configurationBody));
	}

	/**
	 * Initializes the internal controller and then creates and starts the listener
	 * @param duckMaker
	 * @throws Exception
	 */
	private void runListener(DuckMaker duckMaker) throws Exception	{
		duckMaker.initialize();
	
		new DuckSocketListener()
			.setDuckConfiguration(duckMaker.getDuckConfiguration())
			.setDuckControl(duckMaker.getDuckControl())
			.setDuckSecurity(duckMaker.getDuckSecurity())
			.setDuckLog(duckMaker.getDuckLog())
			.initialize()
			.go();
	}
}

