package com.zodoto.util.ducks;



public class DuckSourceMock implements DuckSource {
	
	public DuckStatus duckStatus;
	public DuckStatus duckRangeStatus;
	public int duckRangeStartKey;
	public int duckRangeEndKey;
	public int delay;
	
	public String name;
	public int maximumSize;
	

	@Override
	public DuckSource setDuckConfiguration(DuckConfiguration duckConfiguration) {
		return this;
	}

	@Override
	public DuckSource setDuckSourceRemote(DuckSourceRemote duckSourceRemote) {
		return this;
	}

	@Override
	public DuckStatus get(String name, DuckControl duckControl, int maximumSize) {
		this.name = name;
		this.maximumSize = maximumSize;
		
		DuckRange duckRange = new DuckRange(duckRangeStartKey, duckRangeEndKey);
		
		if(duckStatus == DuckStatus.TIMEOUT)	{
			duckRange.timeout();
		}
		if(duckStatus == DuckStatus.FAILURE)	{
			duckRange.failure();
		}
		
		try	{
			Thread.sleep(delay);
		}
		catch(Exception e)		{
		}

		duckControl.updateEgg(name,duckRange);
		return duckStatus;
	}

	@Override
	public void refresh(String name, DuckControl duckControl) {
		
	}
}
