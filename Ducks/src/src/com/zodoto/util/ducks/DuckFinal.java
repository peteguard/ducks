package com.zodoto.util.ducks;

import java.util.HashMap;
import java.util.Map;


/**
 * Non-thread safe duck.  In large containers with high volumes you don't want the overhead for each request
 * This class maintains a set of keys for use by a single thread.  The container would have a Local Duck, which
 * services the final Ducks.
 * 
 * @author Pete Guard
 *
 */
public class DuckFinal {

	private DuckLocal duckLocal;
	
	private Map<String,DuckEgg> eggs = new HashMap<>();

	
	/**
	 * Attach the final to a local duck
	 * @param duckLocal
	 */
	public void setDuckLocal(DuckLocal duckLocal) {
		this.duckLocal = duckLocal;
	}
	
	/**
	 * Get a single key
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public DuckRange getOne(String name) throws Exception	{
	
		name = name.trim().toLowerCase();
		DuckEgg duckEgg = getEgg(name);

		DuckRange duckRange = duckEgg.getNextRange(1);
		if(! duckRange.isSuccess())	{
			duckRange = getFromLocal(duckEgg);
		}
		
		return duckRange;
	}

	/**
	 * Gets a key range from this stash
	 * @param name
	 * @return
	 */
	private DuckEgg getEgg(String name)	{
		DuckEgg duckEgg = eggs.get(name);
		if(duckEgg == null)	{
			duckEgg = new DuckEgg(name);
			eggs.put(name, duckEgg);
		}
		return duckEgg;
	}
	
	/**
	 * Go to the thread safe repository
	 * @param duckEgg
	 * @return
	 * @throws Exception
	 */
	private DuckRange getFromLocal(DuckEgg duckEgg) throws Exception	{
		DuckRange duckRange = duckLocal.get(duckEgg.getName());
		if(duckRange.isSuccess())	{
			duckEgg.addRange(duckRange);
			duckRange = duckEgg.getNextRange(1);
		}
		return duckRange;
	}
}
