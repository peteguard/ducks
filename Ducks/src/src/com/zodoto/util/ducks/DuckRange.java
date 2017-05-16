package com.zodoto.util.ducks;

/**
 * 
 * Response from a duck with the key range
 * If a Duck is out of keys it will request one from its source
 * The time to reply exceeds a configured setting then the requester 
 * times out.  It will still wait for the response so the caller
 * may choose to make additional requests.
 * 
 * @author Pete Guard
 *
 */
public class DuckRange {

	private DuckStatus duckStatus;
	private long startKey;
	private long endKey;
	
	/**
	 * Creates an empty Duck Range with a status (used to send back Failure, Timeout, and Empty statuses)
	 * @param duckStatus
	 */
	public DuckRange(DuckStatus duckStatus)	{
		this.duckStatus = duckStatus;
		startKey = -1;
		endKey = -1;
	}
	
	/**
	 * Creates a Duck Range with a range of keys, 
	 * sets status to Success unless either start or end key is less than 1
	 * or start key is greater than end key, in which case status is set to empty
	 * @param startKey
	 * @param endKey
	 */
	public DuckRange(long startKey, long endKey)	{
		if(startKey < 1 || endKey < 1 || startKey > endKey)	{
			duckStatus = DuckStatus.EMPTY;
		}
		else	{
			duckStatus = DuckStatus.SUCCESS;
			this.startKey = startKey;
			this.endKey = endKey;
		}
	}
	
	/**
	 * Create an empty duck range
	 */
	public DuckRange()	{
		duckStatus = DuckStatus.EMPTY;
		startKey = -1;
		endKey = -1;
	}
	
	/**
	 * Set status to timeout
	 * @return
	 */
	public DuckRange timeout()	{
		duckStatus = DuckStatus.TIMEOUT;
		startKey = -1;
		endKey = -1;
		return this;
	}
	
	/**
	 * Set status to failure
	 * @return
	 */
	public DuckRange failure()	{
		duckStatus = DuckStatus.FAILURE;
		startKey = -1;
		endKey = -1;
		return this;
	}

	/**
	 * Range contains keys
	 * @return
	 */
	public boolean isSuccess() {
		return duckStatus == DuckStatus.SUCCESS;
	}

	/**
	 * Didn't get any keys or we ran out of keys
	 * @return
	 */
	public boolean isEmpty() {
		return duckStatus == DuckStatus.EMPTY;
	}

	/**
	 * Timed out waiting for a response, might be a problem, or just slow traffic
	 * @return
	 */
	public boolean isTimeout() {
		return duckStatus == DuckStatus.TIMEOUT;
	}

	/**
	 * Key request failed, this is a problem, network, server down, something
	 * @return
	 */
	public boolean isFailure() {
		return duckStatus == DuckStatus.FAILURE;
	}

	/**
	 * Get the starting key in the range
	 * @return
	 */
	public long getStartKey() {
		return startKey;
	}

	/**
	 * Get the last key in the range, inclusive
	 * @return
	 */
	public long getEndKey() {
		return endKey;
	}

	public String getStatus()	{
		return duckStatus.value();
	}
	
	@Override
	public String toString() {
		return "DuckRange [result=" + duckStatus + ", startKey=" + startKey + ", endKey=" + endKey + "]";
	}
}
