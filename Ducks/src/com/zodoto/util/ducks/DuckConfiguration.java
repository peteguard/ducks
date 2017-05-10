package com.zodoto.util.ducks;

/**
 * 
 * Contains the configuraion settings for a duck
 *
 */
public class DuckConfiguration {
	
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
	 * Get the type of duck: Super, Relay, Local, or Final
	 * @return Type of Duck
	 */
	public DuckType getDuckType() {
		return duckType;
	}
	
	/**
	 * Set type of duck: Super, Relay, Local, or Final
	 * @param duckType
	 * @return
	 */
	public DuckConfiguration setDuckType(DuckType duckType) {
		this.duckType = duckType;
		return this;
	}

	/**
	 * Get number of keys this duck will request from its parent
	 * @return request size
	 */
	public int getRequestSize() {
		return requestSize;
	}
	
	/**
	 * Set the number of keys to be returned 
	 * @param requestSize
	 * @return
	 */
	public DuckConfiguration setRequestSize(int requestSize) {
		this.requestSize = requestSize;
		return this;
	}

	public int getResponseSize() {
		return responseSize;
	}
	public DuckConfiguration setResponseSize(int responseSize) {
		this.responseSize = responseSize;
		return this;
	}

	public int getWatermark() {
		return watermark;
	}
	public DuckConfiguration setWatermark(int watermark) {
		this.watermark = watermark;
		return this;
	}

	public int getWaitMillis() {
		return waitMillis;
	}
	public DuckConfiguration setWaitMillis(int waitMillis) {
		this.waitMillis = waitMillis;
		return this;
	}

	public int getWaitCount() {
		return waitCount;
	}
	public DuckConfiguration setWaitCount(int waitCount) {
		this.waitCount = waitCount;
		return this;
	}

	public int getRetries() {
		return retries;
	}
	public DuckConfiguration setRetries(int retries) {
		this.retries = retries;
		return this;
	}

	public String getPersistFileName() {
		return persistFileName;
	}
	public DuckConfiguration setPersistFileName(String persistFileName) {
		this.persistFileName = persistFileName;
		return this;
	}

	public String getSourceURL() {
		return sourceURL;
	}
	public DuckConfiguration setSourceURL(String sourceURL) {
		this.sourceURL = sourceURL;
		return this;
	}

	public int getSourcePort() {
		return sourcePort;
	}
	public DuckConfiguration setSourcePort(int sourcePort) {
		this.sourcePort = sourcePort;
		return this;
	}

	public int getListenPort() {
		return listenPort;
	}
	public DuckConfiguration setListenPort(int listenPort) {
		this.listenPort = listenPort;
		return this;
	}

	public String getLogFolderName() {
		return logFolderName;
	}
	public DuckConfiguration setLogFolderName(String logFolderName) {
		this.logFolderName = logFolderName;
		return this;
	}

	public boolean isSilent() {
		return silent;
	}
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
