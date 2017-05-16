package com.zodoto.util.ducks;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * This class is the core of the duck.  It controls the supply of keys,
 * provides keys when requested and more keys from the source as needed.
 * 
 * @author Pete Guard
 *
 */
public class DuckControlService implements DuckControl {

	private DuckConfiguration duckConfiguration;
	private DuckPersist	duckPersist;
	private DuckLog duckLog;
	private DuckSource duckSource;
	private Map<String,DuckEgg> eggs = new HashMap<>();
	

	/**
	 * Provides the controller with the configuration
	 * @param duckConfiguration The configuration for this duck
	 * @return This object
	 */
	@Override
	public DuckControl setDuckConfiguration(DuckConfiguration duckConfiguration) {
		this.duckConfiguration = duckConfiguration;
		return this;
	}

	@Override
	public DuckControl setDuckLog(DuckLog duckLog) {
		this.duckLog = duckLog;
		return this;
	}

	/**
	 * Provides the controller with the persistence object
	 * @param duckPersist The service to persist key ranges
	 * @return This object
	 */
	@Override
	public DuckControl setDuckPersist(DuckPersist duckPersist) {
		this.duckPersist = duckPersist;
		return this;
	}

	/**
	 * Provides the controller with the source object (where we get more keys)
	 * @param duckSource The service to retreive key ranges
	 * @return This object
	 */
	@Override
	public DuckControl setDuckSource(DuckSource duckSource) {
		this.duckSource = duckSource;
		return this;
	}
	
	/**
	 * Load the keys from persistent store
	 * @throws Exception Error
	 */
	@Override
	public DuckControl initialize() throws Exception	{
		for(DuckData data : duckPersist.getAll())	{
			eggs.put(data.getName(), new DuckEgg(data));
		}
		return this;
	}

	/**
	 * After startup have the Duck preload some known keys to prime the keysource
	 * Makes the first retrieve move faster
	 * @param name Name of key set
	 */
	@Override
	public DuckControl preLoad(String name) {
		duckSource.refresh(name.trim().toLowerCase(), this);
		return this;
	}

	/**
	 * Get a single key
	 * @param name Name of key set
	 * @return Duck Range containing a single key 
	 * @throws Exception Error
	 */
	@Override
	public DuckRange getOne(String name) throws Exception	{
		return get(name.trim().toLowerCase(), 1);
	}
	
	/**
	 * Get a range of keys using the default response size
	 * @param name Name of key set
	 * @return Duck Range containing a range of keys
	 * @throws Exception Error
	 */
	@Override
	public DuckRange get(String name) throws Exception	{
		return get(name.trim().toLowerCase(), duckConfiguration.getResponseSize());
	}
	
	/**
	 * Get a key range up to the default response size
	 * @param name Name of the key set
	 * @param maximumSize Maximum number of keys to return
	 * @return Duck Range containing a range of keys
	 * @throws Exception Error
	 */
	@Override
	public DuckRange get(String name, int maximumSize) throws Exception	{
		
		//	Trim response size to the maximum configured amount
		if(maximumSize > duckConfiguration.getResponseSize())	{
			maximumSize = duckConfiguration.getResponseSize();
		}
		
		//	Retrieve this key set.  getRange creates and inserts one if it did not already exist
		name = name.trim().toLowerCase().replaceAll("\\s+", "");
		DuckEgg egg = getEgg(name);
		DuckRange duckRange = getRange(egg, maximumSize);
		
		//	If the duck range is empty then we need to go to the source to get another range
		//	This step may be repeated.  It is possible that multiple requests will exhaust the key set
		//	and require another request
		int count = duckConfiguration.getRetries();
		while(duckRange.isEmpty() && count-- >= 0)	{
			DuckStatus status = duckSource.get(name, this, maximumSize);
			if(status == DuckStatus.FAILURE || status == DuckStatus.TIMEOUT)	{
				return new DuckRange(status);
			}
			duckRange = getRange(egg, maximumSize);
		}
		
		//	If this request lowers the current inventory below the water mark then request a key range
		//	to be put on deck, so that when the current set is exhausted there is another set ready
		if(egg.needMore(duckConfiguration.getWatermark()))	{
			duckSource.refresh(name, this);
		}
		
		//	If all retries are exhausted and we still have an empty set then the request is timed out
		if(duckRange.isEmpty() && count <= 0)	{
			duckRange.timeout();
		}

		return duckRange;
	}
	
	/**
	 * Call back from source to update the key set
	 * @param name Name of the key set to be modified
	 * @param duckRange key set
	 */
	@Override
	public void updateEgg(String name, DuckRange duckRange)	{
		
		//	Get the key set information for this name
		name = name.trim().toLowerCase().replaceAll("\\s+", "");
		DuckEgg egg = null;
		synchronized(eggs)	{
			egg = eggs.get(name);
		}
		
		//	Update the in memory key set and persist the key set
		if(egg != null)	{
			synchronized(egg)	{
				egg.addRange(duckRange);
				try {
					duckPersist.save(egg.getData());
				} 
				catch (Exception e) {
					duckLog.log(e.getMessage(), e);
				}
			} 
		}
	}
	
	/**
	 * Get a range of keys from the egg
	 * @param egg
	 * @param maximumSize
	 * @return
	 * @throws Exception
	 */
	private DuckRange getRange(DuckEgg egg, int maximumSize) throws Exception	{
		synchronized(egg)	{
			DuckRange reply = egg.getNextRange(maximumSize);
			if(reply.isSuccess())	{
				duckPersist.save(egg.getData());
				return reply;
			}
			return new DuckRange();
		}
	}
	
	/**
	 * Get an egg by name, if egg is not in map then create an empty egg and add it to the list.
	 * This step is synchronized to make sure that we don't end up with two eggs for the same named set
	 * @param name
	 * @return
	 */
	private DuckEgg getEgg(String name)	{
		synchronized(eggs)	{
			DuckEgg egg = eggs.get(name);
			if(egg == null)	{
				egg = new DuckEgg(name);
				eggs.put(name, egg);
			}
			return egg;
		}
	}
}
