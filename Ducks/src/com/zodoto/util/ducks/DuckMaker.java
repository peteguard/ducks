package com.zodoto.util.ducks;

public class DuckMaker {

	private String	configurationFileName;
	private String[] configurationBody;
	private DuckConfiguration duckConfiguration;
	private DuckPersist duckPersist;
	private DuckSource duckSource;
	private DuckControl duckControl;
	
	/**
	 * Set the name of a configuration file
	 * @param configurationFileName
	 * @return
	 */
	public DuckMaker setConfigurationFileName(String configurationFileName) {
		this.configurationFileName = configurationFileName;
		return this;
	}

	/**	
	 * Set the contents of a configuration
	 * @param configurationBody
	 * @return
	 */
	public DuckMaker setConfigurationBody(String[] configurationBody) {
		this.configurationBody = configurationBody;
		return this;
	}

	/**
	 * Set up the duck
	 * @return
	 * @throws Exception
	 */
	public DuckMaker initialize() throws Exception	{
		makeConfiguration();
		makePersistence();
		makeSource();
		makeDuckControl();
		return this;
	}
	
	/**
	 * Get the duck controller
	 * @return
	 */
	public DuckControl getDuckControl() {
		return duckControl;
	}
	
	/**
	 * Get the duck configuration
	 * @return
	 */
	public DuckConfiguration getDuckConfiguration() {
		return duckConfiguration;
	}

	
	/**
	 * Make the configuration either from the file or the array, otherwise toss an exception
	 * @throws Exception
	 */
	private void makeConfiguration() throws Exception	{
		if(configurationFileName != null && configurationFileName.length() != 0)	{
			duckConfiguration = new DuckConfigurationFileLoader().load(configurationFileName);
		}
		else if(configurationBody != null && configurationBody.length != 0)	{
			duckConfiguration = new DuckConfigurationFileLoader().create(configurationBody);
		}
		else	{
			throw new Exception("No configurarion");
		}
	}
	
	/**	
	 * Make the persistence
	 */
	private void makePersistence()	{
		if(duckConfiguration.getPersistFileName().length() == 0)	{
			duckPersist = new DuckPersistNoOp();
		}
		else	{
			duckPersist = new DuckPersistFile().setDuckConfiguration(duckConfiguration);
		}
	}
	
	/**
	 * Makes the source
	 * @throws Exception
	 */
	private void makeSource() throws Exception	{
		if(duckConfiguration.getDuckType() == DuckType.SUPER)	{
			duckSource = new DuckSourceMaster();
			return;
		}
		DuckSourceRemote duckSourceRemote = new DuckSourceRemoteSocket()
				.setDuckConfiguration(duckConfiguration)
				.initialize();
		duckSource = new DuckSourceControl()
				.setDuckConfiguration(duckConfiguration)
				.setDuckSourceRemote(duckSourceRemote);
	}
	
	/**
	 * Makes the controller
	 * @throws Exception
	 */
	private void makeDuckControl() throws Exception	{
		duckControl = new DuckControlService()
				.setDuckConfiguration(duckConfiguration)
				.setDuckPersist(duckPersist)
				.setDuckSource(duckSource)
				.initialize();
	}

}
