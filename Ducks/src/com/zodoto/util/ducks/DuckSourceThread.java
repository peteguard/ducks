package com.zodoto.util.ducks;

/**
 * 
 * Querying another duck is delegated to a separate thread so that the requesting code doesn't block  
 * This class makes use of another class to do the communication, so that different means (socket,
 * http, message queue, etc can be used)
 * 
 * @author Pete Guard
 *
 */
public class DuckSourceThread extends Thread {

	private DuckRequest duckRequest;
	private DuckSourceControl duckSourceControl;
	private DuckSourceRemote duckSourceRemote;
	
	
	/**
	 * Set the request 
	 * @param duckRequest
	 * @return
	 */
	public DuckSourceThread setDuckRequest(DuckRequest duckRequest) {
		this.duckRequest = duckRequest;
		return this;
	}

	/**
	 * The calling control to which the callback goes with the response
	 * @param duckSourceControl
	 * @return
	 */
	public DuckSourceThread setDuckSourceControl(DuckSourceControl duckSourceControl) {
		this.duckSourceControl = duckSourceControl;
		return this;
	}

	/**
	 * Set the object to be called to message the source duck for the next set of keys
	 * @param duckSourceRemote
	 * @return
	 */
	public DuckSourceThread setDuckSourceRemote(DuckSourceRemote duckSourceRemote)	{
		this.duckSourceRemote = duckSourceRemote;
		return this;
	}
	

	/**
	 * Calls the remote source and returns the reply to the controller
	 */
	@Override
	public void run()	{
		try {
			//	Call object method that queries another server
			DuckRange duckRange = duckSourceRemote.get(duckRequest);
			
			//	Successfully got a range, tell the calling class method
			duckRequest.setSuccess(duckRange.isSuccess());
			duckSourceControl.complete(duckRequest, duckRange);
		} 
		catch (Exception e) {
			
			//	failed to get a range, tell the calling class method
			duckRequest.setSuccess(false);
			duckSourceControl.complete(duckRequest, null);
		}
	}
}
