package com.zodoto.util.ducks;

/**
 * 
 * Source to be used by the Super Duck.  It doesn't query another Duck, instead it returns the entire set.
 *
 */
public class DuckSourceMaster implements DuckSource {

	@Override
	public DuckSource setDuckConfiguration(DuckConfiguration duckConfiguration) {
		//	Doesn't do anything - happily violating the Interface Separation principle
		return this;
	}

	@Override
	public DuckSource setDuckSourceRemote(DuckSourceRemote duckSourceRemote) {
		//	Doesn't do anything - happily violating the Interface Separation principle
		return this;
	}

	@Override
	public DuckStatus get(String name, DuckControl duckControl, int maximumSize) {
		DuckRange reply = new DuckRange(1, Long.MAX_VALUE);
		duckControl.updateEgg(name, reply);
		return DuckStatus.SUCCESS;
	}

	@Override
	public void refresh(String name, DuckControl duckControl) {
		//	Doesn't do anything - happily violating the Interface Separation principle
	}
}
