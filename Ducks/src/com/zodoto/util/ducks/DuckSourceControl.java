package com.zodoto.util.ducks;

import java.util.HashMap;
import java.util.Map;

public class DuckSourceControl implements DuckSource {
	
	private DuckConfiguration duckConfiguration;

	private DuckSourceRemote duckSourceRemote;
	
	private Map<String,DuckRequest> requests = new HashMap<>();

	
	@Override
	public DuckSource setDuckConfiguration(DuckConfiguration duckConfiguration) {
		this.duckConfiguration = duckConfiguration;
		return this;
	}

	@Override
	public DuckSource setDuckSourceRemote(DuckSourceRemote duckSourceRemote) {
		this.duckSourceRemote = duckSourceRemote;
		return this;
	}

	@Override
	public DuckStatus get(String name, DuckControl duckControl, int maximumSize) {
		DuckRequest duckRequest = getRequest(name, duckControl);
		waitForCompletion(duckRequest);
		return computeReturn(duckRequest);
	}

	@Override
	public void refresh(String name, DuckControl duckControl) {
		getRequest(name, duckControl);
	}
	
	/**
	 * Thread call back with the response.  Call the control callback with the new key range, remove 
	 * @param duckRequest
	 * @param duckRange
	 */
	public void complete(DuckRequest duckRequest, DuckRange duckRange)	{
		if(duckRequest.isSuccess() && duckRange != null)	{
			duckRequest.getDuckControl().updateEgg(duckRequest.getName(), duckRange);
		}
		duckRequest.setComplete(true);
		synchronized(requests)	{
			requests.remove(duckRequest.getName());
		}
	}
	
	/**
	 * Wait until timeout or success
	 * @param duckRequest
	 */
	private void waitForCompletion(DuckRequest duckRequest)	{
		int waits = duckConfiguration.getWaitCount();
		int waitMillis = duckConfiguration.getWaitCount();
		
		while((waits >= 0) && (! duckRequest.isComplete()))	{
			try {
				Thread.sleep(waitMillis);
			} 
			catch (InterruptedException e) {
				waits--;
			}
		}
	}
	
	/**
	 * Determine the return status
	 * @param duckRequest
	 * @return
	 */
	private DuckStatus computeReturn(DuckRequest duckRequest)	{
		if(duckRequest.isComplete())	{
			if(duckRequest.isSuccess())	{
				return DuckStatus.SUCCESS;
			}
			return DuckStatus.FAILURE;
		}
		return DuckStatus.TIMEOUT;
	}

	/**
	 * Creates as request and a thread to contain the conversation with the source server
	 * @param egg
	 * @return
	 */
	private DuckRequest getRequest(String name, DuckControl duckControl)	{
		synchronized(requests)	{
			
			//	Look for an existing request for this set
			DuckRequest request = requests.get(name);
			
			//	Is there a request currently in process, don't start another one
			if(request != null)	{
				return request;
			}
			
			//	Create a new request
			request = new DuckRequest()
					.setName(name)
					.setDuckControl(duckControl)
					.setRequestedSize(duckConfiguration.getRequestSize());
			
			//	Add the request to the in process map
			requests.put(name, request);
			
			//	Fire up a thread to handle the request
			new DuckSourceThread()
					.setDuckRequest(request)
					.setDuckSourceControl(this)
					.setDuckSourceRemote(duckSourceRemote)
					.start();
			
			return request;
		}
	}
}
