package com.zodoto.util.ducks;

import java.util.List;

public class DuckPersistMock implements DuckPersist {
	
	public boolean getAllCalled;
	public List<DuckData> duckList;
	public DuckData duckData;

	@Override
	public DuckPersist setDuckConfiguration(DuckConfiguration duckConfiguration) {
		return this;
	}

	@Override
	public List<DuckData> getAll() throws Exception {
		getAllCalled = true;
		return duckList;
	}

	@Override
	public void save(DuckData duckData) throws Exception {
		this.duckData = duckData;
	}

}
