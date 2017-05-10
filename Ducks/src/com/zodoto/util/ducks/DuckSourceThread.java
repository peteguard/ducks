package com.zodoto.util.ducks;

/**
 * 
 * Querying another duck is delegated to a separate thread so that the requesting code doesn't block  
 *
 */
public class DuckSourceThread extends Thread {

	private DuckRequest duckRequest;
	private DuckSourceControl duckSourceControl;
	private DuckSourceRemote duckSourceRemote;
	
	
	public DuckSourceThread setDuckRequest(DuckRequest duckRequest) {
		this.duckRequest = duckRequest;
		return this;
	}

	public DuckSourceThread setDuckSourceControl(DuckSourceControl duckSourceControl) {
		this.duckSourceControl = duckSourceControl;
		return this;
	}

	public DuckSourceThread setDuckSourceRemote(DuckSourceRemote duckSourceRemote)	{
		this.duckSourceRemote = duckSourceRemote;
		return this;
	}
	

	public void run()	{
		try {
			//	Call object method that queries another server
			DuckRange duckRange = duckSourceRemote.get(duckRequest.getName(), duckRequest.getRequestedSize());
			
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
