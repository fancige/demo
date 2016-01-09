package com.fancige.servlet;

import java.io.IOException;
import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.Folder;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.event.ConnectionAdapter;
import javax.mail.event.ConnectionEvent;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fancige.manager.FileManager;
import com.fancige.util.MyLogger;
import com.fancige.util.MyLogger.Name;
import com.fancige.util.StringHandler;

@WebServlet("/login")
public class Login extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		HttpSession session = req.getSession();
		String locale = (String) session.getAttribute("locale");
		String path = FileManager.LOCALE_SRC + "com.fancige.mail.Login_"
				+ locale + ".txt";
		Properties lc = FileManager.loadProperties(path);
		String reqCode = req.getParameter("verificode");
		String sessionCode = (String) session.getAttribute("verificode");
		session.removeAttribute("verificode");
		if (reqCode != null && reqCode.equals(sessionCode))
		{
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			if (!StringHandler.isEmpty(username, password))
			{

				Properties props = new Properties();
				Properties confs = FileManager
						.loadProperties(FileManager.CONF_MAIL + "connect.txt");
				String host = req.getParameter("host");
				String imapHost = (String) confs.get(host + ".imap.host");
				if (imapHost != null)
				{
					Session mailSession = Session.getInstance(props);
					try
					{
						Store store = mailSession.getStore("imaps");
						try
						{
							store.connect(imapHost, username + "@" + host,
									password);
							store.addConnectionListener(new ConnectionAdapter()
							{
								@Override
								public void opened(ConnectionEvent e)
								{
									System.out.println("连接成功");
								}
							});
							req.setAttribute("prompt", "登陆成功");
							Folder f = store.getFolder("INBOX");
							System.out.println(f.getMessageCount());
						}
						catch (AuthenticationFailedException e)
						{
							req.setAttribute("prompt", lc.get("5"));
						}
					}
					catch (Exception e)
					{
						e.printStackTrace();
						MyLogger.getLogger(Name.error).severe(e.getMessage());
						req.setAttribute("prompt", lc.get("4"));
					}
				}
				else
				{
					req.setAttribute("prompt", lc.get("3"));
				}
			}
			else
			{
				req.setAttribute("prompt", lc.get("2"));
			}
		}
		else
		{
			req.setAttribute("prompt", lc.get("1"));
		}
		req.getRequestDispatcher("login.jsp").forward(req, resp);
	}
}
