package com.fancige.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MainTest
{
	static ArrayList<Socket> sockets = new ArrayList<>();

	public static void accept() throws IOException
	{
		final ServerSocket ss = new ServerSocket(8080);

		new Thread()
		{
			public void run()
			{
				try
				{
					while (true)
					{
						Socket s = ss.accept();
						System.out.println(sockets.contains(s));
						sockets.add(s);
						System.out.println(sockets.contains(s));
						if(sockets.size() > 2)
						{
							ss.close();
							break;
						}
					}
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}.start();
	}

	public static void test() throws IOException
	{
		try (Socket s = new Socket(InetAddress.getLocalHost().getHostAddress(), 8080))
		{
			
		}
	}

	public static void main(String[] args)
	{
		try
		{
			MainTest.accept();
			MainTest.test();
			MainTest.test();
			MainTest.test();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
