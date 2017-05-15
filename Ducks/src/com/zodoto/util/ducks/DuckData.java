package com.zodoto.util.ducks;

/**
 * 
 * Contains the record for persistence
 * 
 * @author Pete Guard
 *
 */
public class DuckData {

	private String name	= "";
	private long nextKey = -1L;	
	private long endKey = -1L;	
	private long onDeckStartKey = -1L;	
	private long onDeckEndKey = -1L;
	
	/**
	 * Get the name of this key set
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set the name of this key set
	 * @param name
	 * @return
	 */
	public DuckData setName(String name) {
		this.name = name.trim().toLowerCase();
		return this;
	}
	
	/**
	 * Get the value of the next available key
	 * @return
	 */
	public long getNextKey() {
		return nextKey;
	}
	
	/**
	 * Set the value of the next available key
	 * @param nextKey
	 * @return
	 */
	public DuckData setNextKey(long nextKey) {
		this.nextKey = nextKey;
		return this;
	}
	
	/**
	 * Get the last key in the available set
	 * @return
	 */
	public long getEndKey() {
		return endKey;
	}
	
	/**
	 * Set the last key in the available set
	 * @param endKey
	 * @return
	 */
	public DuckData setEndKey(long endKey) {
		this.endKey = endKey;
		return this;
	}
	
	/**
	 * Get the start of the on deck key range
	 * @return
	 */
	public long getOnDeckStartKey() {
		return onDeckStartKey;
	}
	
	public DuckData setOnDeckStartKey(long onDeckStartKey) {
		this.onDeckStartKey = onDeckStartKey;
		return this;
	}
	
	public long getOnDeckEndKey() {
		return onDeckEndKey;
	}
	
	public DuckData setOnDeckEndKey(long onDeckEndKey) {
		this.onDeckEndKey = onDeckEndKey;
		return this;
	}
	
	@Override
	public String toString() {
		return "DuckData [name=" + name + ", nextKey=" + nextKey + ", endKey=" + endKey + ", nextStartKey="
				+ onDeckStartKey + ", nextEndKey=" + onDeckEndKey + "]";
	}	
	
}
