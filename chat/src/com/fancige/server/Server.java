package com.fancige.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import com.fancige.util.XMLHandle;

public class Server
{
	private HashMap<Socket, String> sockets = new HashMap<Socket, String>();
	private ArrayList<Document> messages = new ArrayList<Document>();
	private ServerSocket ss;
	private ServerJFrame frame;
	private Collection<String> userNames;
	private Set<Socket> keys;
	private Document doc = XMLHandle.constructDocument();

	public synchronized void message()
	{
		output(messages.get(0));
		messages.remove(0);
	}

	public synchronized void updateUserList()
	{

		String userList = "";
		userNames = sockets.values();

		for (String userName : userNames)
		{

			userList = userList + userName + "\n";
		}

		frame.area.setText(userList);
		XMLHandle.setType(doc, XMLHandle.UPDATE_USER_LIST);
		XMLHandle.setContent(doc, userList);
		output(doc);
	}

	public synchronized void output(Document doc)
	{

		String str = doc.asXML();
		keys = sockets.keySet();
		for (Socket key : keys)
		{

			try
			{
				DataOutputStream dos = new DataOutputStream(
						key.getOutputStream());
				dos.writeInt(str.getBytes().length);
				dos.write(str.getBytes());

			}
			catch (IOException e)
			{

				e.printStackTrace();
			}
		}
	}

	public synchronized boolean CheckUserName(Socket socket, String userName)
	{

		userNames = sockets.values();

		if (userNames.contains(userName))
		{

			return true;
		}
		else
		{

			sockets.put(socket, userName);
			return false;
		}
	}

	public synchronized void remove(Socket socket)
	{

		sockets.remove(socket);
	}

	public void accept()
	{

		new Thread()
		{

			@Override
			public void run()
			{

				Socket socket = null;
				Document doc = null;
				String userName = null;
				DataInputStream dis = null;
				DataOutputStream dos = null;

				try
				{
					socket = ss.accept();
					accept();
					dis = new DataInputStream(socket.getInputStream());
					dos = new DataOutputStream(socket.getOutputStream());
					boolean checkName = true;
					while (checkName)
					{

						byte[] bytes = new byte[dis.readInt()];
						dis.read(bytes);
						doc = DocumentHelper.parseText(new String(bytes));
						userName = XMLHandle.getUserName(doc);
						checkName = CheckUserName(socket, userName);
						dos.writeBoolean(!checkName);
					}
					frame.field.setText(userName + " 上线了！");
					updateUserList();

					while (true)
					{

						byte[] bytes = new byte[dis.readInt()];
						dis.read(bytes);
						doc = DocumentHelper.parseText(new String(bytes));
						messages.add(doc);
						message();
					}

				}
				catch (IOException | DocumentException e)
				{

					e.printStackTrace();

				}
				finally
				{

					if (sockets.containsKey(socket))
					{

						remove(socket);
					}

					if (socket != null)
					{

						try
						{
							dis.close();
							dos.close();
							socket.close();
						}
						catch (IOException e)
						{
							e.printStackTrace();
						}
					}

					frame.field.setText(userName + "   下线了！");
					updateUserList();
				}
			}
		}.start();
	}

	public int getPort()
	{
		int port;
		while (true)
		{
			String input = JOptionPane
					.showInputDialog("输入服务端端口号, 范围在3000-65535之间。");
			for (int i = 0; i < input.length(); i++)
			{
				if (input.charAt(i) < 48 || input.charAt(i) > 57)
				{
					JOptionPane.showMessageDialog(null, "端口号不合法！");
					return -1;
				}
			}
			port = Integer.parseInt(input);
			if (port >= 3000 && port <= 65535)
			{
				return port;
			}
			else
			{
				JOptionPane.showMessageDialog(null, "端口号不合法！");
				return -1;
			}
		}
	}

	public void start()
	{
		while (true)
		{
			int port;
			while (true)
			{
				port = getPort();
				if (port != -1)
					break;
			}
			try
			{
				ss = new ServerSocket(port);
				frame = new ServerJFrame();
				frame.setVisible(true);
				frame.field.setText(ss.getLocalSocketAddress().toString());
				accept();
				return;
			}
			catch (IOException e)
			{
				if (e.getMessage().equals("Address already in use: JVM_Bind"))
				{
					JOptionPane.showMessageDialog(null, "端口已被占用！");
				}
				else
				{
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		}
	}

	public static void main(String args[])
	{

		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException ex)
		{
			Logger.getLogger(ServerJFrame.class.getName()).log(Level.SEVERE,
					null, ex);
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
		new Server().start();
	}
}