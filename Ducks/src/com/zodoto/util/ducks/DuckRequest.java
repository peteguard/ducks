package com.zodoto.util.ducks;

/**
 * 
 * Request for set of keys
 * RequestedSize is the maximum key size.
 * The source duck may respond with a smaller size
 *
 */
public class DuckRequest {

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
	
	public String getName() {
		return name;
	}
	public DuckRequest setName(String name) {
		this.name = name;
		return this;
	}
	
	public int getRequestedSize() {
		return requestedSize;
	}
	public DuckRequest setRequestedSize(int requestedSize) {
		this.requestedSize = requestedSize;
		return this;
	}
	
	public DuckControl getDuckControl() {
		return duckControl;
	}
	public DuckRequest setDuckControl(DuckControl duckControl) {
		this.duckControl = duckControl;
		return this;
	}
	
	public boolean isComplete() {
		return complete;
	}
	public DuckRequest setComplete(boolean complete) {
		this.complete = complete;
		return this;
	}
	
	public boolean isSuccess() {
		return success;
	}
	public DuckRequest setSuccess(boolean success) {
		this.success = success;
		return this;
	}
	
	
	@Override
	public String toString() {
		return "DuckRequest [name=" + name + ", requestedSize=" + requestedSize + ", duckControl=" + duckControl
				+ ", complete=" + complete + ", success=" + success + "]";
	}
}
