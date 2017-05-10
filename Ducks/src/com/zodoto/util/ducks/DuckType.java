package com.zodoto.util.ducks;

/**
 * 
 * Type of duck
 * FINAL,	returns single key only,not thread sage, no persistence, gets keys from local 
 * LOCAL,	returns key range, thread safe, by default no persistence, gets keys from relay or super
 * RELAY,	returns key range, stand alone, listens on port, has persistence, gets keys from relay or super
 * SUPER	returns key range, stand alone, listens on port, must have persistence, generates keys locally, only one
 */
public enum DuckType {
	
	FINAL,
	LOCAL,
	RELAY,
	SUPER
}
