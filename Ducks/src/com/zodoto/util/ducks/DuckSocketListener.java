package com.zodoto.util.ducks;

import java.net.ServerSocket;
import java.net.Socket;

public class DuckSocketListener {

	private DuckConfiguration duckConfiguration;
	private DuckControl duckControl;
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
	 * Get the listener ready to accept requests
	 * @return
	 * @throws Exception
	 */
	public DuckSocketListener initialize() throws Exception {

		port = duckConfiguration.getListenPort();
		live = true;
		
		return this;
	}
	
	public void go() throws Exception	{
		
		//	TODO: Log this
		ServerSocket serverSocket = new ServerSocket(port);

		//	TODO - how do I switch off live?
        while (live) {
        	Socket socket = serverSocket.accept();
        	new DuckSocketThread().setDuckControl(duckControl).setSocket(socket).start();
        }
        
        serverSocket.close();
	}
}


