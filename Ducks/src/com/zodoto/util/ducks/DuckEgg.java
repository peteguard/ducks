package com.zodoto.util.ducks;

/**
 * 
 * Container for a named key, keeps the current range, and an ondeck range
 *
 */
public class DuckEgg {
	
	private String name;
	private long nextKey;	
	private long endKey;	
	private long onDeckNextKey;	
	private long onDeckEndKey;
	private boolean requested;
	
	/**
	 * Create an empty egg
	 * @param name
	 */
	public DuckEgg(String name)	{
		this.name = name;
		nextKey = -1L;	
		endKey = -1L;	
		onDeckNextKey = -1L;	
		onDeckEndKey = -1L;
		requested = false;
	}
	
	/**
	 * Create egg from locally stored copy
	 * @param duckData
	 */
	public DuckEgg(DuckData duckData)	{
		name = duckData.getName();
		nextKey = duckData.getNextKey();
		endKey = duckData.getEndKey();
		onDeckNextKey = duckData.getNextStartKey();
		onDeckEndKey = duckData.getNextEndKey();
		requested = false;
	}

	/**
	 * Creates an egg from another response from another Duck
	 * @param duckRange
	 */
	public DuckEgg(String name, DuckRange duckRange)	{
		this.name = name;
		nextKey = duckRange.getStartKey();
		endKey = duckRange.getEndKey();
		onDeckNextKey = -1L;
		onDeckEndKey = -1L;
		requested = false;
	}
	
	/**
	 * Get the name of this keyset
	 * @return
	 */
	public String getName()	{
		return name;
	}
	
	/**
	 * Gets a copy of the contents to be persisted
	 * @return
	 */
	public DuckData getData()	{
		synchronized(this)	{
			return new DuckData()
					.setName(name)
					.setNextKey(nextKey)
					.setEndKey(endKey)
					.setNextStartKey(onDeckNextKey)
					.setNextEndKey(onDeckEndKey);
		}
	}

	/**
	 * Get the next range
	 * @param maximum
	 * @return
	 */
	public DuckRange getNextRange(int maximum)	{

		synchronized(this)	{
			//	Make sure we have a current range
			promoteFromOnDeck();
	
			//	Make sure we have any keys, if not return default not found range
			if(noCurrentKeys())	{
				return new DuckRange();
			}
			
			//	make sure we only take the available range of keys
			long available = 1 + endKey - nextKey;
			if(available < maximum)	{
				maximum = (int) available;
			}
			
			//	Create the reply
			DuckRange reply = new DuckRange(nextKey, nextKey + maximum - 1);
			
			//	increment next key
			nextKey += maximum;
	
			//	Make sure we have a current range
			promoteFromOnDeck();
	
			return reply;
		}
	}
	
	/**
	 * Query the egg to see if we need a refresh from the source
	 * @param watermark
	 * @return
	 */
	public boolean needMore(int watermark)	{
		
		synchronized(this)	{
			//	if we are out of current range and have on deck then promote it
			promoteFromOnDeck();
			
			//	if we have a request in process then we don't need another
			if(requested)	{
				return false;
			}
			
			//	if we have on deck keys we don't need more
			if(! noOnDeckKeys())	{
				return false;
			}
			
			//	if there is no current range then we need another range
			if(noCurrentKeys())	{
				requested = true;
				return true;
			}
			
			//	if the difference between the endKey and the nextKey are less than the watermark level then we need another range
			boolean reply = endKey - nextKey < watermark;
			
			//	If we are asking for more then flag this egg
			if(reply)	{
				requested = true;
			}
			
			return reply;
		}
	}
	
	/**
	 * Add a range from the source
	 * @param duckRange
	 */
	public void addRange(DuckRange duckRange)	{
		
		synchronized(this)	{
			requested = false;
			
			//	if this is an invalid duck range then reject it
			if(duckRange.isSuccess() == false)	{
				return;
			}
			
			//	if we are out of current range and have on deck then promote it
			promoteFromOnDeck();
			
			//	if we are still out of the current range then replace it
			if(noCurrentKeys())	{
				nextKey = duckRange.getStartKey();
				endKey = duckRange.getEndKey();
			}
			else if(noOnDeckKeys())	{
				onDeckNextKey = duckRange.getStartKey();
				onDeckEndKey = duckRange.getEndKey();
			}
		}
	}
	
	/**
	 * If we are out of current range
	 * Then copy the onDeck to current
	 * And clear the on
	 */
	private void promoteFromOnDeck()	{
		if(nextKey <= endKey)	{
			return;
		}
		if(noOnDeckKeys())	{
			return;
		}
		nextKey = onDeckNextKey;
		endKey = onDeckEndKey;
		onDeckNextKey = -1L;	
		onDeckEndKey = -1L;
		requested = false;
	}
	
	/**
	 * Are there no currently available keys
	 * @return
	 */
	private boolean noCurrentKeys()	{
		return (-1L == nextKey || -1L == endKey || nextKey > endKey);
	}
	
	/**
	 * Are there no on deck available key
	 * @return
	 */
	private boolean noOnDeckKeys()	{
		return (-1L == onDeckNextKey || -1L == onDeckEndKey || onDeckNextKey > onDeckEndKey);
	}

	@Override
	public String toString() {
		return "DuckEgg [name=" + name + ", nextKey=" + nextKey + ", endKey=" + endKey + ", onDeckNextKey="
				+ onDeckNextKey + ", onDeckEndKey=" + onDeckEndKey + ", requested=" + requested + "]";
	}

}
