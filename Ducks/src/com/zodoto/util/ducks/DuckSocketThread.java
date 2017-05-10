package com.zodoto.util.ducks;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class DuckSocketThread extends Thread	{
	
	private DuckControl duckControl;
	private Socket socket;
	
	public DuckSocketThread setDuckControl(DuckControl duckControl)	{
		this.duckControl = duckControl;
		return this;
	}

	public DuckSocketThread setSocket(Socket socket)	{
		this.socket = socket;
		return this;
	}

	public void run()	{
		BufferedReader in = null;
		PrintWriter out = null;
		try	{
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
		
			String request = in.readLine();
			String response = execute(request);
			out.println(response);
        	socket.close();
		}
		catch(Exception e)	{
			if(out != null)	{
				out.println("FAILURE:-1:-1");
			}
			System.out.println("Disconnection");
			try	{
				socket.close();
			}
			catch(Exception f)	{
				
			}
		}
	}

	private String execute(String request) throws Exception 	{
		
		if(request == null)	{
			return "FAILURE:-1:-1";
		}
		String[] array = request.split(":");
		String name = array[0];
		int maximumSize = Integer.parseInt(array[1]);
		DuckRange duckRange = duckControl.get(name, maximumSize);
		String response = duckRange.getStatus() + ":" + duckRange.getStartKey() + ":" + duckRange.getEndKey();
		
		return response;
	}
}


