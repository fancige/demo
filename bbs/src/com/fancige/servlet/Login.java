package com.fancige.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fancige.main.UserManager;
import com.fancige.util.StringHandler;

@WebServlet("/login")
public class Login extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		// Checks if the verification code is required for login.
		if (req.getSession().getAttribute("failedLogins") != null
				&& (int) req.getSession().getAttribute("failedLogins") > 3)
			resp.getWriter().print("yes");
		else
			resp.getWriter().print("no");
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		HttpSession session = req.getSession();
		PrintWriter out = resp.getWriter();
		String nameOrId = req.getParameter("nameOrId");
		String password = req.getParameter("password");
		/*
		 * If failure to login is more than 3 times, verification code is
		 * requested.
		 */
		int failedLogins = -1;
		if (session.getAttribute("failedLogins") != null)
		{
			failedLogins = (int) session.getAttribute("failedLogins");
		}

		/*
		 * Only considers the successful case, if meets the conditions,
		 * failedLogins will be zero which means success.
		 */
		if (nameOrId != null && password != null
				&& UserManager.isMatches(nameOrId, password))
		{
			if (failedLogins <= 3)
			{
				failedLogins = 0;
			}
			else
			{
				String rcode = req.getParameter("code");
				Object scode = session.getAttribute("code");
				if (rcode != null && scode != null
						&& rcode.equalsIgnoreCase((String) scode))
				{
					failedLogins = 0;
				}
				else
				{
					out.print("codeError");
				}
			}
		}
		// Fails to login.
		else
		{
			// Tells the browser to display the "verification code" div element.
			if (failedLogins == 2)
			{
				out.print("needCode");
			}
			else
			{
				out.print("failure");
			}
		}

		/*
		 * Result processing.
		 */

		// Zero means success.
		if (failedLogins == 0)
		{
			out.print("success");
			// we supposes nameOrId is username.
			String username = nameOrId;
			// if true, we can know the nameOrId is userid, we should get the username.
			if (StringHandler.isAllDigits(nameOrId))
			{
				username = UserManager.getUserName(nameOrId);
			}
			session.setAttribute("username", username);
			session.removeAttribute("failedLogins");
		}
		// -1 means it is the first time to login unsuccessfully.
		else if (failedLogins == -1)
		{
			session.setAttribute("failedLogins", 1);
		}
		else
		{
			session.setAttribute("failedLogins", failedLogins + 1);
		}
		// Removes the verification code no matter whether it exists or not.
		session.removeAttribute("code");
	}
}
