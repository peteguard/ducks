package com.zodoto.util.ducks;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * 
 * Saves the current working state to a file.
 * The getAll and save methods are synchronized to prevent file corruption or an invalid read
 * 
 * @author Pete Guard
 *
 */
public class DuckPersistFile implements DuckPersist {
	
	private DuckConfiguration duckConfiguration;
	private Map<String,DuckData> contents;

	
	@Override
	public DuckPersist setDuckConfiguration(DuckConfiguration duckConfiguration) {
		this.duckConfiguration = duckConfiguration;
		return this;
	}

	@Override
	public List<DuckData> getAll() throws Exception {
		synchronized(this)	{
			contents = new HashMap<>();
			
			String dataFileName = duckConfiguration.getPersistFileName();
			createFile(dataFileName);
			List<DuckData> reply = new ArrayList<>();
			
			if(dataFileName != null && dataFileName.length() > 0)	{
				try (Stream<String> stream = Files.lines(Paths.get(dataFileName)))	{
					stream.forEach(row -> readRow(row, reply));
				}
			}
			return reply;
		}
	}

	@Override
	public void save(DuckData duckData) throws Exception {
		synchronized(this)	{
			
			//	first populate a temporary file
			String dataFileName = duckConfiguration.getPersistFileName() + ".tmp";
			createFile(dataFileName);
			contents.put(duckData.getName(), duckData);
			
			try(BufferedWriter writer = Files.newBufferedWriter(Paths.get(dataFileName)))	{
				for(DuckData row : contents.values())	{
					writeRow(row, writer);
				}
			}
			
			File srce = new File(dataFileName);
			File dest = new File(duckConfiguration.getPersistFileName());
			srce.renameTo(dest);
		}
	}

	/**
	 * Load a row into the list to be returned and to the 
	 * @param row
	 * @param reply
	 */
	private void readRow(String row, List<DuckData> reply)	{
		
		String[] columns = row.trim().split("\\s+");
		if(columns.length != 5)	{
			return;
		}
		
		DuckData duckData = new DuckData()
				.setName(columns[0].toLowerCase())
				.setNextKey(stringToLong(columns[1]))
				.setEndKey(stringToLong(columns[2]))
				.setOnDeckStartKey(stringToLong(columns[3]))
				.setOnDeckEndKey(stringToLong(columns[4]));
			
		reply.add(duckData);
		contents.put(duckData.getName(), duckData);
	}
	
	/**
	 * Writes a single row to the file
	 * @param duckData
	 * @param writer
	 * @throws IOException
	 */
	private void writeRow(DuckData duckData, BufferedWriter writer) throws IOException	{
		String row = new StringBuilder()
			.append(duckData.getName())
			.append(" ")
			.append(duckData.getNextKey())
			.append(" ")
			.append(duckData.getEndKey())
			.append(" ")
			.append(duckData.getOnDeckStartKey())
			.append(" ")
			.append(duckData.getOnDeckEndKey())
			.append(System.getProperty("line.separator"))
			.toString();
		writer.write(row);
	}	
	
	/**
	 * Creates the file if it doesn't already exist
	 * @param fileName
	 * @throws Exception
	 */
	private void createFile(String fileName) throws Exception	{
		File file = new File(fileName);
		file.createNewFile();
	}
	
	/**
	 * Why isn't this just part of java, no doubt so third party solution exists
	 * @param in
	 * @return
	 */
	private long stringToLong(String in)	{
		long reply = 0;
		try	{
			reply = Long.parseLong(in);
		}
		catch(Exception e)	{
		}
		return reply;
	}
}
