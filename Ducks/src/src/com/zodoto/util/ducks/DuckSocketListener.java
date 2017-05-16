package com.zodoto.util.ducks;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Listens for a socket connection, which will have a request for a set of keys.
 * Spawns a DuckSocketThread to accept the request and return the response
 * 
 * @author Pete Guard
 */
public class DuckSocketListener {

	private DuckConfiguration duckConfiguration;
	private DuckControl duckControl;
	private DuckSecurity duckSecurity;
	private DuckLog duckLog;
	private int port;
	private boolean live;
	
	
	/**
	 * Set the configuration
	 * @param duckConfiguration
	 * @return
	 */
	public DuckSocketListener setDuckConfiguration(DuckConfiguration duckConfiguration) {
		this.duckConfiguration = duckConfiguration;
		return this;
	}
	
	/**
	 * Set the controller to be called when a request arrives
	 * @param duckControl
	 * @return
	 */
	public DuckSocketListener setDuckControl(DuckControl duckControl)	{
		this.duckControl = duckControl;
		return this;
	}
	
	/**
	 * Sets the object to encrypt and decrypt objects
	 * @param duckSecurity Encrypt decrypt object
	 * @return This object
	 */
	public DuckSocketListener setDuckSecurity(DuckSecurity duckSecurity)	{
		this.duckSecurity = duckSecurity;
		return this;
	}
	
	/**
	 * Sets the logger
	 * @param duckLog Logger
	 * @return This object
	 */
	public DuckSocketListener setDuckLog(DuckLog duckLog)	{
		this.duckLog = duckLog;
		return this;
	}

	/**
	 * Get the listener ready to accept requests
	 * @return
	 * @throws Exception
	 */
	public DuckSocketListener initialize() throws Exception {

		port = duckConfiguration.getListenPort();
		live = true;
		
		Runtime.getRuntime().addShutdownHook(new Thread()	{
			public void run()	{
				live = false;
			}
		});
		
		return this;
	}
	
	/**
	 * Start and run the listener
	 * @throws Exception
	 */
	public void go() throws Exception	{
		
		//	Listen for connections
		duckLog.log("Listening on port: " + port);
		ServerSocket serverSocket = new ServerSocket(port);

        while (live) {
        	Socket socket = serverSocket.accept();
        	new DuckSocketThread()
        		.setDuckControl(duckControl)
        		.setSocket(socket)
        		.setDuckSecurity(duckSecurity)
        		.setDuckLog(duckLog)
        		.start();
        }
        
        //	close port
        serverSocket.close();
	}
}


