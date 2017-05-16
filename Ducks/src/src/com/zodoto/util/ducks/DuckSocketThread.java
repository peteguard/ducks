package com.zodoto.util.ducks;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Receives a single request, contacts the local control for the next set of keys, and returns them
 *
 * @author Pete Guard
 */
public class DuckSocketThread extends Thread	{
	
	private DuckControl duckControl;
	private DuckSecurity duckSecurity;
	private DuckLog duckLog;
	private Socket socket;
	
	/**
	 * Set the duckControl that responds to key requests
	 * @param duckControl
	 * @return This object
	 */
	public DuckSocketThread setDuckControl(DuckControl duckControl)	{
		this.duckControl = duckControl;
		return this;
	}
	
	/**
	 * Sets the object to encrypt and decrypt objects
	 * @param duckSecurity Encrypt decrypt object
	 * @return This object
	 */
	public DuckSocketThread setDuckSecurity(DuckSecurity duckSecurity)	{
		this.duckSecurity = duckSecurity;
		return this;
	}
	
	/**
	 * Sets the logger
	 * @param duckLog Logger
	 * @return This object
	 */
	public DuckSocketThread setDuckLog(DuckLog duckLog)	{
		this.duckLog = duckLog;
		return this;
	}

	/**
	 * Set the socket that contains the request
	 * @param socket
	 * @return
	 */
	public DuckSocketThread setSocket(Socket socket)	{
		this.socket = socket;
		return this;
	}

	/**
	 * Run by the thread to marshal the request from the socket to the control and the response back to the socket
	 */
	@Override
	public void run()	{
		try	{
			DuckRequest duckRequest = acceptRequest();
			DuckRange duckRange = duckControl.get(duckRequest.getName(), duckRequest.getRequestedSize());
			sendResponse(duckRange, duckRequest.getIdentity());
			socket.close();
		}
		catch(Exception e)	{
			duckLog.log(e.getLocalizedMessage(), e);
		}
	}

	
	/**
	 * Accepts the request for a key range
	 * @return Request
	 * @throws Exception Error
	 */
	private DuckRequest acceptRequest() throws Exception	{
		String encryptedRequest = null;
		BufferedReader in = null;
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		encryptedRequest = in.readLine();
		
		String request = duckSecurity.decryptRequest(encryptedRequest);	
		String[] array = request.split(DuckConstants.DIVIDER);
		DuckRequest duckRequest = new DuckRequest().setSuccess(false);
		try	{
			if(array.length == 3)	{
				duckRequest.setIdentity(array[0]);
				duckRequest.setName(array[1]);
				duckRequest.setRequestedSize(Integer.parseInt(array[2]));
			}
 		}
		catch(Exception e)	{
		}
		
		duckRequest.setComplete(true);
		duckLog.logReceivedRequest(duckRequest);
		return duckRequest;
	}

	/**
	 * Send response back to the requesting duck
	 * @param duckRange New set of keys
	 * @throws Exception error
	 */
	private void sendResponse(DuckRange duckRange, String identity) throws Exception	{
		
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		String response = duckRange.getStatus() + DuckConstants.DIVIDER + duckRange.getStartKey() + DuckConstants.DIVIDER + duckRange.getEndKey();
		String encryptedResponse = duckSecurity.encryptResponse(response, identity);
		out.println(encryptedResponse);
		duckLog.logSentRange(duckRange);
	}
}	

