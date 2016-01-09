package com.fancige.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import javax.swing.JFrame;

import com.fancige.component.ChatFrame;

public class Chat extends Thread
{
	private static final String ENDKEY = "m3j=:oiFEj12.(j3A#928";
	private static final String SEND_CLOSEKEY = "w3l9.20'.329;i2";
	private static final String RECEIVE_CLOSEKEY = "f,e23;3ji(8'";
	
	private ChatFrame frame;
	private Socket socket;

	public Chat(Socket socket)
	{
		this.socket = socket;
	}

	public void run()
	{
		frame = new ChatFrame();
		// Receive messages
		new Thread()
		{
			public void run()
			{
				receive();
			}
		}.start();
		// Send messages.
		frame.btnSend.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				send(frame.enter.getText());
				frame.display.append("send: " + frame.enter.getText() + "\n");
				frame.enter.setText("");				
			}
		});
		// Clear messages.
		frame.btnClear.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				frame.enter.setText("");
			}
		});
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				close();
			}
		});
		frame.setVisible(true);
	}

	public void close()
	{
		if (!socket.isClosed())
		{
			send(SEND_CLOSEKEY);
		}
	}

	private void send(String mess)
	{
		try
		{
			if(!socket.isClosed())
				socket.getOutputStream().write((mess + ENDKEY).getBytes());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void receive()
	{
		try
		{
			InputStream is = socket.getInputStream();

			while (true)
			{
				String mess = "";
				while (!mess.endsWith(ENDKEY))
				{
					byte[] bytes = new byte[1024];
					mess += new String(bytes, 0, is.read(bytes));
				}
				mess = mess.substring(0, mess.length() - ENDKEY.length());
				if (mess.equals(SEND_CLOSEKEY))
				{
					send(RECEIVE_CLOSEKEY);
					socket.close();
					return;
				}
				if (mess.equals(RECEIVE_CLOSEKEY))
				{
					socket.close();
					return;
				}
				frame.display.append("receive: " + mess + "\n");
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
