package com.zodoto.util.ducks;

import java.util.Scanner;

/**
 * For testing or demonstration purposes.  Enter name and optional number of keys.
 * DuckReader will contact source server for range of keys.
 * 
 * @author Pete Guard
 *
 */
public class DuckReader {

	public static final void main(String[] a)	{
		if(a.length != 1)	{
			System.out.println("Missing configuration file name command line argument");
			return;
		}
		
		try {
			DuckLocal duckLocal = new DuckLocal().initialize(a[0]);
			@SuppressWarnings("resource")
			Scanner scan = new Scanner(System.in);
			DuckRange range;
			
			for(;;)	{
				System.out.print("> ");
				String row = scan.nextLine();
				if(row.trim().length() == 0)	{
					return;
				}
				
				String[] parts = row.split("\\s+");
				if(parts.length == 1)	{
					range = duckLocal.getOne(parts[0]);
					
					if("success".equalsIgnoreCase(range.getStatus()))	{
						System.out.println("Key=" + range.getStartKey());
					}
					else	{
						System.out.println(range.getStatus());
					}

				}
				else	{
					int count = 1;
					try	{
						count = Integer.parseInt(parts[1]);
					}
					catch(Exception e)	{
						System.out.println("Second argument must be numeric");
						continue;
					}
					range = duckLocal.get(parts[0], count);
					
					if("success".equalsIgnoreCase(range.getStatus()))	{
						System.out.println("Start=" + range.getStartKey() + " Last=" + range.getEndKey());
					}
					else	{
						System.out.println(range.getStatus());
					}
				}
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
