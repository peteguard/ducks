package com.zodoto.util.ducks;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;


/**
 * 
 * Loads the configuration from the file
 * 
 * @author Pete Guard
 *
 */
public class DuckConfigurationFileLoader {
	
	/**
	 * Loads the configuration from the file by the given name
	 * @param configurationFileName
	 * @return Duck configuration object
	 * @throws Exception
	 */
	public DuckConfiguration load(String configurationFileName)	throws Exception	{
		
		Map<String,String> temporary = loadFile(configurationFileName);
		DuckConfiguration duckConfiguration = createConfiguration(temporary);
		validateDuckConfiguration(duckConfiguration);
		logDuckConfiguration(duckConfiguration);
	
		return duckConfiguration;
	}
	
	/**
	 * Creates the configuration from the array
	 * @param array
	 * @return Duck configuration object
	 * @throws Exception
	 */
	public DuckConfiguration create(String[] array) throws Exception	{
		
		Map<String,String> temporary = loadArray(array);
		DuckConfiguration duckConfiguration = createConfiguration(temporary);
		validateDuckConfiguration(duckConfiguration);
		logDuckConfiguration(duckConfiguration);
	
		return duckConfiguration;
	}
	
	/**
	 * Extract a map of key value pairs from a file
	 * @param configurationFileName
	 * @return Map of configuration key value pairs
	 * @throws Exception
	 */
	private Map<String,String> loadFile(String configurationFileName)	throws Exception	{
		
		Map<String,String> temporary = new HashMap<>();
		
		//	Is this a valid file name
		if(configurationFileName == null || configurationFileName.length() == 0)	{
			throw new Exception("Invalid configuration file name [" + configurationFileName + "]");
		}
		
		//	Does a valid configuration file exist?
		File file = new File(configurationFileName);
		if(! file.exists())	{
			throw new Exception("Configuration file name not found [" + configurationFileName + "]");
		}
		if(! file.isFile())	{
			throw new Exception("Configuration file name is not a file [" + configurationFileName + "]");
		}
		if(! file.canRead())	{
			throw new Exception("Configuration file name is not readable [" + configurationFileName + "]");
		}
			
		//	Load the file
		try (Stream<String> stream = Files.lines(Paths.get(configurationFileName)))	{
			stream.forEach(row -> extractFromRow(row, temporary));
		}
		
		return temporary;
	}
	
	/**
	 * Extract a confguration from an array
	 * @param array
	 * @return Map of configuration key value pairs
	 * @throws Exception
	 */
	private Map<String,String> loadArray(String[] array) throws Exception	{
		
		Map<String,String> temporary = new HashMap<>();
		
		for(int index = 0; index < array.length; index++)	{
			extractFromRow(array[index], temporary);
		}
	
		return temporary;
	}
	
	/**
	 * Extract a key value pair from the row, value may be empty
	 * Ignores blank lines and comments (starts with '#')
	 * @param row
	 * @param temporary
	 */
	private void extractFromRow(String row, Map<String,String> temporary)	{
		
		//	ignore lines that couldn't have a key=value pair
		row = row.trim();
		if(row.length() == 0)	{
			return;
		}
		if(row.startsWith("#"))	{
			return;
		}

		//	find the end of the key, may be whitespace or a separator
		int index = 0;
		while(index < row.length())	{
			char ch = row.charAt(index);
			if(':' == ch || '=' == ch || Character.isWhitespace(ch))	{
				break;
			}
			index++;
		}
		
		String key = row.substring(0, index).trim();
		String value = ++index < row.length() ? row.substring(index).trim() : "";
		
		temporary.put(key, value);
	}
	
	/**
	 * Convert the key value map into a configuration object
	 * @param temporary
	 * @return Duck configuration object
	 * @throws Exception
	 */
	private DuckConfiguration createConfiguration(Map<String,String> temporary) throws Exception	{
		
		DuckConfiguration duckConfiguration = new DuckConfiguration();
		
		for(String key: temporary.keySet())	{
			String value = temporary.get(key);
		
			switch(key.toLowerCase())	{
			case "identity":		duckConfiguration.setIdentity(value);										break;
			case "ducktype":		duckConfiguration.setDuckType(getDuckType(value));							break;
			case "requestsize":		duckConfiguration.setRequestSize(stringToInt(value, "RequestSize"));		break;
			case "responsesize":	duckConfiguration.setResponseSize(stringToInt(value, "ResponseSize"));		break;
			case "watermark":		duckConfiguration.setWatermark(stringToInt(value, "WaterMark"));			break;
			case "waitmillis":		duckConfiguration.setWaitMillis(stringToInt(value, "WaitMillis"));			break;
			case "waitcount":		duckConfiguration.setWaitCount(stringToInt(value, "WaitCount"));			break;
			case "retries":			duckConfiguration.setRetries(stringToInt(value, "Retries"));				break;
			case "persistfilename":	duckConfiguration.setPersistFileName(value);								break;
			case "sourceurl":		duckConfiguration.setSourceURL(value);										break;
			case "sourceport":		duckConfiguration.setSourcePort(stringToInt(value, "SourcePort"));			break;
			case "listenport":		duckConfiguration.setListenPort(stringToInt(value, "ListenPort"));			break;
			case "logfoldername":	duckConfiguration.setLogFolderName(value);									break;
			case "silent":			duckConfiguration.setSilent(StringToBoolean(value, "Silet"));				break;
			default:
				throw new Exception("Unknown configuration type key=[" + key +  "] value=[" + value + "]");
			}
		}
		
		return duckConfiguration;
	}
	
	/**
	 * Extracts the type of duck
	 * @param input
	 * @return Type of Duck
	 * @throws Exception
	 */
	private DuckType getDuckType(String input) throws Exception	{
		switch(input.toUpperCase())	{
		case "FINAL":	return DuckType.FINAL;
		case "LOCAL":	return DuckType.LOCAL;
		case "RELAY":	return DuckType.RELAY;
		case "SUPER":	return DuckType.SUPER;
		}
		throw new Exception("Invalid Duck Type [" + input + "]");
	}
	
	/**
	 * Extracts an integer value, which must be a non-negative number
	 * @param in
	 * @param name
	 * @return Numeric value
	 * @throws Exception
	 */
	private int stringToInt(String in, String name) throws Exception	{
		try	{
			int reply = Integer.parseInt(in);
			if(reply >= 0)	{
				return reply;
			}
		}
		catch(Exception e)	{
		}
		throw new Exception("Invalid value [" + in + "] for [" + name + "], must be a non-negative integer");
	}
	
	/**
	 * Extract a boolean value from a text representation
	 * @param in
	 * @param name
	 * @return Boolean value
	 * @throws Exception
	 */
	private boolean StringToBoolean(String in, String name) throws Exception	{
		in = in.toLowerCase();
		switch(in)	{
		case "true":
		case "yes":
		case "on":
		case "1":
			return true;
		case "false":
		case "no":
		case "off":
		case "0":
			return false;
		}
		throw new Exception("Invalid boolean [" + in + "] for [" + name + "] valid values are true/false, yes/no, on/off, 1/0,");
	}
	
	/**
	 * Check the configuration to make sure it will actually work
	 * @param duckConfiguration
	 * @throws Exception 
	 */
	private void validateDuckConfiguration(DuckConfiguration duckConfiguration) throws Exception	{

		
		switch(duckConfiguration.getDuckType()){
		case SUPER:
			notZero(duckConfiguration.getListenPort(), "Super duck requires a listen port");
			notZero(duckConfiguration.getPersistFileName().length(), "Super duck requires a persistence file");
			break;
		case RELAY:
			validateRequestParameters(duckConfiguration);
			notZero(duckConfiguration.getListenPort(), "Relay duck requires a listen port");
			notZero(duckConfiguration.getSourceURL().length(), "Relay duck requires a source URL");
			notZero(duckConfiguration.getSourcePort(), "Relay duck requires a source port");
			break;
		case LOCAL:
			validateRequestParameters(duckConfiguration);
			notZero(duckConfiguration.getSourceURL().length(), "Relay duck requires a source URL");
			notZero(duckConfiguration.getSourcePort(), "Relay duck requires a source port");
			break;
		case FINAL:
		}
	}
	
	/**
	 * Apply validations to parameters required by ducks that get keys from other ducks
	 * @param duckConfiguration
	 * @throws Exception
	 */
	private void validateRequestParameters(DuckConfiguration duckConfiguration) throws Exception	{
		if(duckConfiguration.getResponseSize() >= duckConfiguration.getRequestSize())	{
			throw new Exception("ResponseSize=" + duckConfiguration.getResponseSize() + " must be less than RequestSize=" + duckConfiguration.getRequestSize());
		}
		if(duckConfiguration.getWatermark() >= duckConfiguration.getRequestSize())	{
			throw new Exception("Watermark=" + duckConfiguration.getWatermark() + " must be less than RequestSize=" + duckConfiguration.getRequestSize());
		}
	}
	
	/**
	 * Throws an exception if the value is zero
	 * @param value
	 * @param message
	 * @throws Exception
	 */
	private void notZero(int value, String message) throws Exception	{
		if(value == 0)	{
			throw new Exception(message);
		}
	}
	
	/**
	 * Outputs the configuration, useful for debugging
	 * @param duckConfiguration
	 */
	private void logDuckConfiguration(DuckConfiguration duckConfiguration)	{
		
		if(duckConfiguration.isSilent())	{
			return;
		}
		
		System.out.println("identity: " + duckConfiguration.getIdentity());
		System.out.println("ducktype: " + duckConfiguration.getDuckType());
		System.out.println("requestsize: " + duckConfiguration.getRequestSize());
		System.out.println("responsesize: " + duckConfiguration.getResponseSize());
		System.out.println("watermark: " + duckConfiguration.getWatermark());
		System.out.println("waitmillis: " + duckConfiguration.getWaitMillis());
		System.out.println("waitcount: " + duckConfiguration.getWaitCount());
		System.out.println("retries: " + duckConfiguration.getRetries());
		System.out.println("persistfilename: " + duckConfiguration.getPersistFileName());
		System.out.println("sourceurl: " + duckConfiguration.getSourceURL());
		System.out.println("sourceport: " + duckConfiguration.getSourcePort());
		System.out.println("listenport: " + duckConfiguration.getListenPort());
		System.out.println("logfoldername: " + duckConfiguration.getLogFolderName());
		System.out.println("silent: " + duckConfiguration.isSilent());
	}
}
