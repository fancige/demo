package com.fancige.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import com.fancige.util.MyImages;
import com.fancige.util.XMLHandle;

public class Client
{
	private ClientMainFrame mainFrame;
	private ClientChatFrame chatFrame;
	private String currentUserName;
	private Socket socket;
	private DataInputStream dis;
	private DataOutputStream dos;
	private Document doc;

	// 用XML处理将需要发送的聊天消息。

	public void constructMessage()
	{

		if (chatFrame.jTextAreaInput.getText().length() != 0)
		{

			XMLHandle.setContent(doc, chatFrame.jTextAreaInput.getText());
			XMLHandle.setType(doc, XMLHandle.MESSAGE);
			chatFrame.jTextAreaInput.setText("");

			output();
		}
	}

	// 将经处理后的XML写入到输出流中。

	public void output()
	{
		String str = doc.asXML();
		try
		{
			dos.writeInt(str.getBytes().length);
			dos.write(str.getBytes());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void close()
	{

		if (!socket.isClosed())
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
	}

	// 为显示每位用户名字和头像的标签增加鼠标监听器，包括标签的颜色改变。

	class LabelMouseListener extends MouseAdapter
	{

		private final Color USER_LIST_LABEL_ENTER_COLOR = new Color(227, 232,
				252);
		private final Color USER_LIST_LABEL_EXIT_COLOR = new Color(244, 246,
				255);

		@Override
		public void mouseEntered(MouseEvent e)
		{
			e.getComponent().setBackground(USER_LIST_LABEL_ENTER_COLOR);
		}

		@Override
		public void mouseExited(MouseEvent e)
		{
			e.getComponent().setBackground(USER_LIST_LABEL_EXIT_COLOR);
		}
	}

	private LabelMouseListener labelMouseListener = new LabelMouseListener();
	private HashMap<String, JLabel> userMap = new HashMap<String, JLabel>();
	private Color userLabelColor = new Color(244, 246, 255);

	// 更新用户列表。将包含用户名和标签的HashMap清空，重新加入用户名和标签。

	private void updateUserList(String content)
	{

		String[] userNames = content.split("\n");

		userMap.clear();
		for (String userName : userNames)
		{

			if (userName.equals(currentUserName))
			{
				continue;
			}
			JLabel userLabel = new JLabel();
			userLabel.setIcon(MyImages.headIcon);
			userLabel.setText(userName);
			userLabel.setBackground(userLabelColor);
			userLabel.setOpaque(true);
			userLabel.addMouseListener(labelMouseListener);
			userMap.put(userName, userLabel);
		}

		mainFrame.jPanelUserList
				.setPreferredSize(new Dimension(244, userMap.size() * 55));
		mainFrame.jPanelUserList.setBounds(0, 30, 244, userMap.size() * 55);
		mainFrame.jPanelUserList.removeAll();

		Collection<JLabel> labels = userMap.values();
		int i = 0;
		for (JLabel label : labels)
		{

			label.setBounds(5, i * 55, 234, 55);
			mainFrame.jPanelUserList.add(label);
			i++;
		}
		mainFrame.jLabelUserList
				.setText(mainFrame.jLabelUserList.getText().substring(0, 23)
						+ userMap.size());
		Dimension dimension = new Dimension(244,
				30 + mainFrame.jPanelUserList.getPreferredSize().height);
		mainFrame.jPanelUserListRoot.setPreferredSize(dimension);
		mainFrame.jScrollPaneUserList.getViewport().setPreferredSize(dimension);
	}

	private void enterGroupChat()
	{

		chatFrame = new ClientChatFrame();

		chatFrame.jButtonClear.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{

				chatFrame.jTextAreaInput.setText("");
			}
		});

		chatFrame.jButtonSend.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{

				constructMessage();
			}
		});
		// 接收消息。

		try
		{

			while (true)
			{

				byte[] bytes = new byte[dis.readInt()];
				dis.read(bytes);
				Document doc = DocumentHelper
						.parseText(new String(bytes, 0, bytes.length));
				String content = XMLHandle.getContent(doc);
				String userName = XMLHandle.getUserName(doc);
				int type = XMLHandle.getType(doc);

				if (type == XMLHandle.MESSAGE)
				{

					chatFrame.jTextAreaOutput
							.append(userName + " : \n" + content + "\n\n");
					chatFrame.jTextAreaOutput.setSelectionStart(
							chatFrame.jTextAreaOutput.getText().length());

				}
				else if (type == XMLHandle.UPDATE_USER_LIST)
				{

					updateUserList(content);
				}
			}

		}
		catch (IOException | DocumentException e)
		{

			e.printStackTrace();
		}

	}

	// 与服务器通讯检查用户名是否可用。

	private String createName()
	{
		String name = JOptionPane.showInputDialog("创建一个用户名");
		if (name == null)
		{
			System.exit(0);
		}
		if (name.length() == 0)
		{
			JOptionPane.showMessageDialog(null, "用户名不能为空");
		}
		else if (name.length() > 10)
		{
			JOptionPane.showMessageDialog(null, "用户名不能大于10个字符。");
		}
		else
		{
			try
			{
				XMLHandle.setUserName(doc, name);
				String str = doc.asXML();
				dos.writeInt(str.getBytes().length);
				dos.write(str.getBytes());
				if (dis.readBoolean())
				{
					return name;
				}
				else
				{
					JOptionPane.showMessageDialog(null, "用户名已存在！");
				}

			}
			catch (IOException e)
			{

				e.printStackTrace();
			}
		}
		return null;
	}

	private int getPort()
	{
		int port;
		while (true)
		{
			String input = JOptionPane
					.showInputDialog("输入服务端端口号, 范围在3000-65535之间。");
			if (input == null)
			{
				System.exit(0);
			}

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

	private void enterMainFrame()
	{
		String host = JOptionPane.showInputDialog("输入服务端IP地址：");
		if (host == null)
		{
			System.exit(0);
		}
		int port;
		while (true)
		{
			port = getPort();
			if (port != -1)
				break;
		}
		try
		{
			socket = new Socket(host, port);
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
			doc = XMLHandle.constructDocument();

			// 打开创建用户名的对话框。
			while (true)
			{
				currentUserName = createName();
				if (currentUserName != null)
					break;
			}

			// 用户名创建成功后进入到主界面。

			mainFrame = new ClientMainFrame();
			mainFrame.jLabelCurrentUser.setIcon(MyImages.headIcon);
			mainFrame.jLabelCurrentUser.setText(currentUserName);
			mainFrame.setVisible(true);
			mainFrame.jLabelGroupChat.addMouseListener(new MouseAdapter()
			{

				private Color colorExit = new Color(65, 158, 253);

				@Override
				public void mouseClicked(MouseEvent e)
				{

					mainFrame.jLabelGroupChat.setBackground(colorExit);
					chatFrame.setVisible(true);
				}
			});
			mainFrame.addWindowListener(new WindowAdapter()
			{

				@Override
				public void windowClosing(WindowEvent e)
				{
					close();
				}
			});

			enterGroupChat();

		}
		catch (IOException e)
		{
			e.printStackTrace();
			String mess = e.getMessage();
			if (e instanceof UnknownHostException)
				JOptionPane.showMessageDialog(null, "未知IP：" + host);
			else
			{
				JOptionPane.showMessageDialog(null, mess);
			}
			System.exit(-1);
		}
	}

	public static void main(String[] args)
	{

		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}
		new Client().enterMainFrame();
	}

}