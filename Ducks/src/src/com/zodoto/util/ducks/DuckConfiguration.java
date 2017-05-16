package com.zodoto.util.ducks;

/**
 * 
 * Contains the configuration settings for a duck
 * 
 * @author Pete Guard
 *
 */
public class DuckConfiguration {
	
	//	Type of duck: Local / Relay / Super
	private String identity;
	
	//	Type of duck: Local / Relay / Super
	private DuckType duckType;

	//	Size of the request this duck will send to the parent duck
	private int	requestSize;
	
	//	Maximum size of the response from this duck
	private int responseSize;
	
	//	Difference between current next key and end key at which point a refresh will be requested
	private int watermark;
	
	//	Number of milliseconds per wait
	private int waitMillis;
	
	//	Number of waits
	private int waitCount;
	
	//	Number of times to retry if we get an empty response (could be caused by so many requests taking all the keys)
	private int retries;
	
	//	Full name of the persist file (if name is empty, then there is no persistence)
	private String persistFileName;
	
	//	URL for the source (required for all but super)
	private String sourceURL;
	
	//	URL for the source (required for all but super)
	private int sourcePort;
	
	//	URL for the source (required for all but super)
	private int listenPort;
	
	//	Folder where the log files are located
	private String logFolderName;
	
	//	Turns off logging
	private boolean silent;
	
	
	/**
	 * Gets the name of this duck, sent in messages to identify sender
	 * @return Name of this duck
	 */
	public String getIdentity() {
		return identity;
	}

	/**
	 * Sets the name of this duck, sent in messages to identify sender
	 * @param identity Name of this node
	 * @return This object
	 */
	public DuckConfiguration setIdentity(String identity) {
		this.identity = identity;
		return this;
	}

	/**
	 * Get the type of duck: Super, Relay, Local, or Final
	 * @return Type of Duck
	 */
	public DuckType getDuckType() {
		return duckType;
	}
	
	/**
	 * Set type of duck: Super, Relay, Local, or Final
	 * @param duckType This duck's type
	 * @return This object
	 */
	public DuckConfiguration setDuckType(DuckType duckType) {
		this.duckType = duckType;
		return this;
	}

	/**
	 * Get number of keys this duck will request from its source
	 * @return request size
	 */
	public int getRequestSize() {
		return requestSize;
	}
	
	/**
	 * Set the number of keys this duck will request from its source
	 * @param requestSize
	 * @return This object
	 */
	public DuckConfiguration setRequestSize(int requestSize) {
		this.requestSize = requestSize;
		return this;
	}

	/**
	 * Get the number of keys this duck will return
	 * @return
	 */
	public int getResponseSize() {
		return responseSize;
	}
	
	/**
	 * Set the number of keys thus duck will return
	 * @param responseSize
	 * @return This object
	 */
	public DuckConfiguration setResponseSize(int responseSize) {
		this.responseSize = responseSize;
		return this;
	}

	/**
	 * Get the number of keys remaining where a request for another set is triggered.
	 * @return
	 */
	public int getWatermark() {
		return watermark;
	}

	/**
	 * Set the number of keys remaining where a request for another set is triggered.
	 * @param watermark
	 * @return This object
	 */
	public DuckConfiguration setWatermark(int watermark) {
		this.watermark = watermark;
		return this;
	}

	/**
	 * Get the number of milliseconds to wait for a response from the source duck
	 * @return
	 */
	public int getWaitMillis() {
		return waitMillis;
	}
	
	/**
	 * Set the number of milliseconds to wait for a response from the source duck
	 * @param waitMillis
	 * @return This object
	 */
	public DuckConfiguration setWaitMillis(int waitMillis) {
		this.waitMillis = waitMillis;
		return this;
	}

	/**
	 * Get the number of times to wait for a response from the source duck
	 * @return Number of times to wait for a response
	 */
	public int getWaitCount() {
		return waitCount;
	}
	
	/**
	 * Set the number of times to wait for a response from the source duck
	 * @param waitCount
	 * @return This object
	 */
	public DuckConfiguration setWaitCount(int waitCount) {
		this.waitCount = waitCount;
		return this;
	}

	/**
	 * Get the number of times to reissue a request if we receive an empty result.  This could happen if multiple threads contend for the same key set
	 * @return Number of retries to resolve an empty return set
	 */
	public int getRetries() {
		return retries;
	}
	
	/**
	 * Set the number of times to reissue a request if we receive an empty result.  This could happen if multiple threads contend for the same key set
	 * @param retries
	 * @return
	 */
	public DuckConfiguration setRetries(int retries) {
		this.retries = retries;
		return this;
	}

	/**
	 * Get the name of the file to hold the current and on deck set of keys
	 * @return
	 */
	public String getPersistFileName() {
		return persistFileName;
	}
	
	/**
	 * Set the name of the file to hold the current and on deck set of keys
	 * @param persistFileName
	 * @return This object
	 */
	public DuckConfiguration setPersistFileName(String persistFileName) {
		this.persistFileName = persistFileName;
		return this;
	}

	/**
	 * Get the URL of the source duck
	 * @return
	 */
	public String getSourceURL() {
		return sourceURL;
	}
	
	/**
	 * Set the URL of the source duck
	 * @param sourceURL
	 * @return This object
	 */
	public DuckConfiguration setSourceURL(String sourceURL) {
		this.sourceURL = sourceURL;
		return this;
	}

	/**
	 * Get the port number of the source duck
	 * @return Port number the source is listent on
	 */
	public int getSourcePort() {
		return sourcePort;
	}
	
	/**
	 * Set the port number of the source duck
	 * @param sourcePort
	 * @return This object
	 */
	public DuckConfiguration setSourcePort(int sourcePort) {
		this.sourcePort = sourcePort;
		return this;
	}

	/**
	 * Get the port number to listen for a request from another duck
	 * @return
	 */
	public int getListenPort() {
		return listenPort;
	}
	
	/**
	 * Set the port number to listen for a request from another duck
	 * @param listenPort
	 * @return This object
	 */
	public DuckConfiguration setListenPort(int listenPort) {
		this.listenPort = listenPort;
		return this;
	}

	/**
	 * Get the name of the folder to hold the log files
	 * @return
	 */
	public String getLogFolderName() {
		return logFolderName;
	}
	
	/**
	 * Set the name of the folder to hold the log files
	 * @param logFolderName
	 * @return This object
	 */
	public DuckConfiguration setLogFolderName(String logFolderName) {
		this.logFolderName = logFolderName;
		return this;
	}

	/**
	 * Get status of logging
	 * @return True if no logging is produced
	 */
	public boolean isSilent() {
		return silent;
	}
	
	/**
	 * Set status of logging
	 * @param silent
	 * @return This object
	 */
	public DuckConfiguration setSilent(boolean silent) {
		this.silent = silent;
		return this;
	}

	
	@Override
	public String toString() {
		return "DuckConfiguration [duckType=" + duckType + ", requestSize=" + requestSize + ", responseSize="
				+ responseSize + ", watermark=" + watermark + ", waitMillis=" + waitMillis + ", waitCount=" + waitCount
				+ ", retries=" + retries + ", persistFileName=" + persistFileName + ", sourceURL=" + sourceURL
				+ ", sourcePort=" + sourcePort + ", listenPort=" + listenPort + ", logFolderName=" + logFolderName
				+ ", silent=" + silent + "]";
	}

}
