package com.zodoto.util.ducks;

/**
 * 
 * Request for set of keys
 * RequestedSize is the maximum key size.
 * The source duck may respond with a smaller size
 * 
 * @author Pete Guard
 *
 */
public class DuckRequest {

	//	Identifies the sender
	private String identity;
	
	//	Name of the key set
	private String name;
	
	//	Requested size of the key range, server may give us fewer keys, but never more
	private int	requestedSize;
	
	//	The controller that requested the new range, used for callback on asynchronous calls
	private DuckControl duckControl;
	
	//	Did we get a response, either success or a failure
	private boolean complete = false;
	
	//	Request was successfully filled or not filled, meaningless unless complete = true
	private boolean success = false;
	
	/**
	 * Gets the identity of the sender
	 * @return Sender's identity
	 */
	public String getIdentity() {
		return identity;
	}
	
	/**
	 * Sets the identity of the sender
	 * @param identity Name of the sender
	 * @return This Object
	 */
	public DuckRequest setIdentity(String identity) {
		this.identity = identity;
		return this;
	}
	
	/**
	 * Get the name of the requested key set
	 * @return Name of the requested key set
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set the name of the requested key set
	 * @param name Name of the requested key set
	 * @return This Object
	 */
	public DuckRequest setName(String name) {
		this.name = name;
		return this;
	}
	
	/**
	 * Get the requested number of keys
	 * @return Requested number of keys
	 */
	public int getRequestedSize() {
		return requestedSize;
	}
	
	/**
	 * Set the requested number of keys
	 * @param requestedSize Requested number of keys
	 * @return This Object
	 */
	public DuckRequest setRequestedSize(int requestedSize) {
		this.requestedSize = requestedSize;
		return this;
	}
	
	/**
	 * Get the duck control to which the response is returned
	 * @return Calling duck control
	 */
	public DuckControl getDuckControl() {
		return duckControl;
	}
	
	/**
	 * Set the duck control to which the response is returned
	 * @param duckControl Controller that sent this request
	 * @return This Object
	 */
	public DuckRequest setDuckControl(DuckControl duckControl) {
		this.duckControl = duckControl;
		return this;
	}
	
	/**
	 * Has this request been completed
	 * @return True if completed, false if not completed
	 */
	public boolean isComplete() {
		return complete;
	}
	
	/**
	 * Sets the request as complete (default incomplete)
	 * @param complete Set true when the request is complete
	 * @return This Object
	 */
	public DuckRequest setComplete(boolean complete) {
		this.complete = complete;
		return this;
	}
	
	/**
	 * Was the request success, indeterminate unless isComplete is true.
	 * @return True for successful, false if not successful
	 */
	public boolean isSuccess() {
		return success;
	}
	
	/**
	 * Sets request was successful (true) or unsuccessful (false)
	 * Be sure to call this method before calling setComplete
	 * @param success Set true if request succeeded, false otherwise.
	 * @return This Object
	 */
	public DuckRequest setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	
	@Override
	public String toString() {
		return "DuckRequest [identity=" + identity + ", name=" + name + ", requestedSize=" + requestedSize
				+ ", duckControl=" + duckControl + ", complete=" + complete + ", success=" + success + "]";
	}
}
