package com.zodoto.util.ducks;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Attach to a final controller to provide a no load persist
 *
 */
public class DuckPersistNoOp implements DuckPersist {

	@Override
	public DuckPersist setDuckConfiguration(DuckConfiguration duckConfiguration) {
		return this;
	}

	@Override
	public List<DuckData> getAll() throws Exception {
		return new ArrayList<DuckData>();
	}

	@Override
	public void save(DuckData duckData) throws Exception {
		//	Does nothing
	}

}
