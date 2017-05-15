package com.zodoto.util.ducks;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Sends request to and receives response from another server using a simple TCP socket connection
 * 
 * @author Pete Guard
 *
 */
public class DuckSourceRemoteSocket implements DuckSourceRemote {

	private DuckConfiguration duckConfiguration;
	private DuckSecurity duckSecurity;
	private DuckLog duckLog;
	private String url;
	private int port;
	
	
	@Override
	public DuckSourceRemote setDuckConfiguration(DuckConfiguration duckConfiguration) {
		this.duckConfiguration = duckConfiguration;
		return this;
	}
	
	@Override
	public DuckSourceRemote setDuckSecurity(DuckSecurity duckSecurity)	{
		this.duckSecurity = duckSecurity;
		return this;
	}
	
	@Override
	public DuckSourceRemote setDuckLog(DuckLog duckLog)	{
		this.duckLog = duckLog;
		return this;
	}

	@Override
	public DuckSourceRemote initialize() throws Exception {
		
		url = duckConfiguration.getSourceURL();
		port = duckConfiguration.getSourcePort();
		
		return this;
	}
	
	@Override
	public DuckRange get(DuckRequest duckRequest) throws Exception {
		
		DuckRange duckRange = null;
	    Socket socket = new Socket(url, port);
	    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

	    try	{
	    	//	log the request
	    	duckRequest.setIdentity(duckConfiguration.getIdentity());
	    	duckLog.logSentRequest(duckRequest);
		
	    	//	send the request
	    	String requestString = getIdentity() + DuckConstants.DIVIDER + duckRequest.getName() + DuckConstants.DIVIDER + duckRequest.getRequestedSize();
	    	String encryptedRequest = duckSecurity.encryptRequest(requestString, duckConfiguration.getIdentity());
	    	out.println(encryptedRequest);
	    	
	    	//	get the request and close the socket
	    	String encryptedResponse = in.readLine();
			if(encryptedResponse == null)	{
				duckRange = new DuckRange().failure();
			}
			else	{
				String response = duckSecurity.decryptResponse(encryptedResponse);
				duckRange = extractDuckRange(response);
			}
	    }
	    catch(Exception e)	{
	    	duckLog.log(e.getMessage(), e);
			duckRange = new DuckRange().failure();
	    }
	    finally	{
			socket.close();
	    }
	    
		duckLog.logReceivedRange(duckRange);
		return duckRange;
	}

	
	private DuckRange extractDuckRange(String response) 	{
		DuckRange duckRange = null;
		
		//	split into status and keys
		String[] array = response.split(DuckConstants.DIVIDER);
		
		//	If we got the correct size
		if(array.length == 3)	{
			switch(array[0])	{
			case DuckConstants.FAILURE:	
				duckRange = new DuckRange().failure();
				break;
			case DuckConstants.TIMEOUT:	
				duckRange = new DuckRange().timeout();
				break;
			case DuckConstants.EMPTY:	
				duckRange = new DuckRange();
				break;
			case DuckConstants.SUCCESS:
				Long startKey = Long.parseLong(array[1]);
				Long endKey = Long.parseLong(array[2]);
				duckRange = new DuckRange(startKey, endKey);
				break;
			default:
				duckRange = new DuckRange().failure();
			}
		}
		else	{
			duckRange = new DuckRange().failure();
		}
		
		return duckRange;
	}
	
	private String getIdentity()	{
		String identity = duckConfiguration.getIdentity();
		return identity == null ? "" : identity;
	}
}
