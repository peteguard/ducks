package com.zodoto.util.ducks;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of DuckPersist that doesn't save any thing, and returns an empty list on getAll.
 * Used to configure Ducks that don't use their own persistence. Final Ducks and sometimes Local Ducks
 * won't use persistence.
 * 
 * @author Pete Guard
 *
 */
public class DuckPersistNoOp implements DuckPersist {

	/**
	 * Placeholder to save configuration
	 */
	@Override
	public DuckPersist setDuckConfiguration(DuckConfiguration duckConfiguration) {
		return this;
	}

	/**
	 * Returns an empty list
	 */
	@Override
	public List<DuckData> getAll() throws Exception {
		return new ArrayList<DuckData>();
	}

	/**
	 * Doesn't do anything.
	 */
	@Override
	public void save(DuckData duckData) throws Exception {
		//	Does nothing
	}

}
