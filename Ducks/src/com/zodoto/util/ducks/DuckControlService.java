package com.zodoto.util.ducks;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Core of a duck
 *
 */
public class DuckControlService implements DuckControl {

	private DuckConfiguration duckConfiguration;
	
	private DuckPersist	duckPersist;
	
	private DuckSource duckSource;
	
	private Map<String,DuckEgg> eggs = new HashMap<>();
	

	@Override
	public DuckControl setDuckConfiguration(DuckConfiguration duckConfiguration) {
		this.duckConfiguration = duckConfiguration;
		return this;
	}

	@Override
	public DuckControl setDuckPersist(DuckPersist duckPersist) {
		this.duckPersist = duckPersist;
		return this;
	}

	@Override
	public DuckControl setDuckSource(DuckSource duckSource) {
		this.duckSource = duckSource;
		return this;
	}
	
	/**
	 * Load the keys from persistent store
	 * @throws Exception
	 */
	@Override
	public DuckControl initialize() throws Exception	{
		for(DuckData data : duckPersist.getAll())	{
			eggs.put(data.getName(), new DuckEgg(data));
		}
		return this;
	}

	@Override
	public DuckControl preLoad(String name) {
		duckSource.refresh(name.trim().toLowerCase(), this);
		return this;
	}

	/**
	 * Get a single key
	 * @param name
	 * @return
	 * @throws Exception
	 */
	@Override
	public DuckRange getOne(String name) throws Exception	{
		return get(name.trim().toLowerCase(), 1);
	}
	
	/**
	 * Get a single key
	 * @param name
	 * @return
	 * @throws Exception
	 */
	@Override
	public DuckRange get(String name) throws Exception	{
		return get(name.trim().toLowerCase(), duckConfiguration.getResponseSize());
	}
	
	/**
	 * Get a key range
	 * @param name
	 * @param maximumSize
	 * @return
	 * @throws Exception
	 */
	@Override
	public DuckRange get(String name, int maximumSize) throws Exception	{
		if(maximumSize > duckConfiguration.getResponseSize())	{
			maximumSize = duckConfiguration.getResponseSize();
		}
		
		name = name.trim().toLowerCase();
		int count = duckConfiguration.getRetries();
		DuckEgg egg = getEgg(name);
		DuckRange duckRange = getRange(egg, maximumSize);
		
		while(duckRange.isEmpty() && count-- > 0)	{
			DuckStatus status = duckSource.get(name, this, maximumSize);
			if(status == DuckStatus.FAILURE || status == DuckStatus.TIMEOUT)	{
				return new DuckRange(status);
			}
			duckRange = getRange(egg, maximumSize);
		}
		
		if(egg.needMore(duckConfiguration.getWatermark()))	{
			duckSource.refresh(name, this);
		}
		if(duckRange.isEmpty() && count <= 0)	{
			duckRange.timeout();
		}

		return duckRange;
	}
	
	/**
	 * Call back from source
	 * @param name
	 * @param duckRange
	 */
	@Override
	public void updateEgg(String name, DuckRange duckRange)	{
		name = name.trim().toLowerCase();
		DuckEgg egg = null;
		synchronized(eggs)	{
			egg = eggs.get(name);
		}
		if(egg != null)	{
			synchronized(egg)	{
				egg.addRange(duckRange);
				try {
					duckPersist.save(egg.getData());
				} 
				catch (Exception e) {
					// TODO Auto-generated catch block
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
