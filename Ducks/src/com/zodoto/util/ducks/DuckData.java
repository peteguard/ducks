package com.zodoto.util.ducks;

/**
 * 
 * Pojo that contains the information about a key as currently persisted.
 *
 */
public class DuckData {

	private String name	= "";
	private long nextKey = -1L;	
	private long endKey = -1L;	
	private long nextStartKey = -1L;	
	private long nextEndKey = -1L;
	
	public String getName() {
		return name;
	}
	public DuckData setName(String name) {
		this.name = name.trim().toLowerCase();
		return this;
	}
	
	public long getNextKey() {
		return nextKey;
	}
	public DuckData setNextKey(long nextKey) {
		this.nextKey = nextKey;
		return this;
	}
	
	public long getEndKey() {
		return endKey;
	}
	public DuckData setEndKey(long endKey) {
		this.endKey = endKey;
		return this;
	}
	
	public long getNextStartKey() {
		return nextStartKey;
	}
	public DuckData setNextStartKey(long nextStartKey) {
		this.nextStartKey = nextStartKey;
		return this;
	}
	
	public long getNextEndKey() {
		return nextEndKey;
	}
	public DuckData setNextEndKey(long nextEndKey) {
		this.nextEndKey = nextEndKey;
		return this;
	}
	
	@Override
	public String toString() {
		return "DuckData [name=" + name + ", nextKey=" + nextKey + ", endKey=" + endKey + ", nextStartKey="
				+ nextStartKey + ", nextEndKey=" + nextEndKey + "]";
	}	
	
}
