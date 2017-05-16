package com.zodoto.util.ducks;

/**
 * Creates a source, persist, and control using the configuration.
 * 
 * @author Pete Guard
 *
 */
public class DuckMaker {

	private String	configurationFileName;
	private String[] configurationBody;
	private DuckConfiguration duckConfiguration;
	private DuckPersist duckPersist;
	private DuckSource duckSource;
	private DuckControl duckControl;
	private DuckLog duckLog;
	private DuckSecurity duckSecurity;
	
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
		makeDuckLog();
		makeDuckSecurity();
		makePersistence();
		makeSource();
		makeDuckControl();
		return this;
	}
	
	/**
	 * Get the duck controller
	 * @return Controller
	 */
	public DuckControl getDuckControl() {
		return duckControl;
	}
	
	/**
	 * Get the duck configuration
	 * @return Configuration
	 */
	public DuckConfiguration getDuckConfiguration() {
		return duckConfiguration;
	}
	
	/**
	 * Get the duck security
	 * @return Security
	 */
	public DuckSecurity getDuckSecurity() {
		return duckSecurity;
	}
	
	/**
	 * Get the duck log
	 * @return Log
	 */
	public DuckLog getDuckLog() {
		return duckLog;
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
	 * Create the duck log object
	 */
	private void makeDuckLog() throws Exception	{
		if(duckConfiguration.isSilent())	{
			duckLog = new DuckLogNoOp();
		}
		else if(duckConfiguration.getLogFolderName() != null && duckConfiguration.getLogFolderName().length() != 0)	{
			duckLog = new DuckLogFile().setDuckConfiguration(duckConfiguration).initialize();
		}
		else	{
			duckLog = new DuckLogSysout();
		}
	}
	
	/**
	 * Create the duck security object
	 */
	private void makeDuckSecurity()	{
		duckSecurity = new DuckSecurityPublicPrivate();
	}
	
	/**	
	 * Make the persistence object
	 */
	private void makePersistence()	{
		if(duckConfiguration.getPersistFileName() == null || duckConfiguration.getPersistFileName().length() == 0)	{
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
				.setDuckSecurity(duckSecurity)
				.setDuckLog(duckLog)
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
				.setDuckLog(duckLog)
				.setDuckPersist(duckPersist)
				.setDuckSource(duckSource)
				.initialize();
	}

}
