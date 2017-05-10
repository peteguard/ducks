package com.zodoto.util.ducks;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class DuckSourceRemoteSocket implements DuckSourceRemote {

	private DuckConfiguration duckConfiguration;
	
	private String url;
	private int port;
	
	
	@Override
	public DuckSourceRemote setDuckConfiguration(DuckConfiguration duckConfiguration) {
		this.duckConfiguration = duckConfiguration;
		return this;
	}

	@Override
	public DuckSourceRemote initialize() throws Exception {
		
		url = duckConfiguration.getSourceURL();
		port = duckConfiguration.getSourcePort();
		
		return this;
	}
	
	@Override
	public DuckRange get(String name, int maximumSize) throws Exception {
		
	    Socket socket = new Socket(url, port);
	    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

	    try	{
	    	//	make and send a request
	    	String request = name + ":" + maximumSize;
	    	out.println(request);
		
	    	//	get the request and close the socket
	    	String response = in.readLine();
			socket.close();
			
			//	split into status and keys
			String[] array = response.split(":");
			if(array.length != 3)	{
				return new DuckRange().failure();
			}
			
			//	If we don't have a success then return a non-success duck range
			switch(array[0])	{
			case "FAILURE":	return new DuckRange().failure();
			case "TIMEOUT":	return new DuckRange().timeout();
			case "EMPTY":	return new DuckRange();
			}
	    	
			//	Extract the keys
			Long startKey = Long.parseLong(array[1]);
			Long endKey = Long.parseLong(array[2]);
			
			return new DuckRange(startKey, endKey);
	    }
	    catch(Exception e)	{
			socket.close();
			return new DuckRange().failure();
	    }
	}

}
