package com.fancige.servlet;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fancige.manager.FileManager;

@WebServlet("/preference")
public class Preference extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		HttpSession session = req.getSession();
		String locale = req.getParameter("locale");
		Properties props = FileManager.loadProperties(FileManager.CONF
				+ "global.txt");
		String list = props.getProperty("localeList");
		String[] ls = list.split(",");
		for (String l : ls)
		{
			if (l.equals(locale))
			{
				session.setAttribute("locale", locale);
				break;
			}
		}
		resp.sendRedirect(req.getHeader("Referer"));
	}
}
