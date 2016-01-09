package com.fancige.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JFrame;

import com.fancige.bean.UserInfo;
import com.fancige.component.MainFrame;

public class Main
{
	private static final int minAcceptPort = 10001;
	private static final int maxAcceptPort = 10003;
	private static int threadCount = 0;
	private static final int maxThreadCount = 512;

	private ArrayList<Socket> sockets = new ArrayList<>();
	private ServerSocket ss;
	private int acceptPort;

	private MainFrame frame;

	public Main()
	{
		frame = new MainFrame();
		frame.addParentLabel("默认列表");
		frame.getMainFrame().setVisible(true);
		frame.getMainFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		accept();
		connectAll();
	}

	// private void updateSockets()
	// {
	// synchronized (sockets)
	// {
	// for (int i = 0; i < sockets.size(); i++)
	// {
	// }
	// }
	// }

	private void accept()
	{
		for (acceptPort = minAcceptPort; acceptPort <= maxAcceptPort; acceptPort++)
		{
			try
			{
				ss = new ServerSocket(acceptPort);
				break;
			}
			catch (IOException e)
			{
				System.out.println(e.getMessage());
			}
		}
		if (null == ss)
			exit("Ports:" + minAcceptPort + "-" + maxAcceptPort
					+ " have been used, try to close one of these applications"
					+ " using these ports and start again.");

		threadCount++;
		new Thread()
		{
			public void run()
			{
				while (true)
					try
					{
						addSocket(ss.accept());
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
			}
		}.start();
	}

	private void exit(String mess)
	{
		System.out.println(mess);
		System.exit(0);
	}

	private void addSocket(Socket s)
	{
		synchronized (sockets)
		{
			if (!sockets.contains(s))
			{
				sockets.add(s);
				UserInfo info = new UserInfo();
				info.setParent("默认列表");
				info.setIp(s.getInetAddress().getHostAddress());
				info.setPort(s.getPort());
				info.setName(s.getInetAddress().getHostName());
				frame.addChildLabel(info);
			}
		}
	}

	class Connect extends Thread
	{
		private String ip;
		private int port;

		Connect(String ip, int port)
		{
			this.ip = ip;
			this.port = port;
		}

		public void run()
		{
			try
			{
				Socket s = new Socket(ip, port);
				addSocket(s);
			}
			catch (UnknownHostException e)
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				System.out.println(ip + ":" + port + " " + e.getMessage());
			}
			finally
			{
				threadCount--;
			}
		}
	}

	private void connectAll()
	{
		try
		{
			String ip = InetAddress.getLocalHost().getHostAddress();
			ip = ip.substring(0, ip.lastIndexOf('.') + 1);

			for (int i = 1; i < 255; i++)
			{
				for (int port = minAcceptPort; port <= maxAcceptPort; port++)
				{
					// you can not connect yourselft.
					if (acceptPort != port)
					{
						while (threadCount > maxThreadCount)
							Thread.sleep(3000);

						new Connect(ip + i, port).start();
					}
				}
			}
		}
		catch (UnknownHostException | InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
