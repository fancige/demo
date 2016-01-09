package com.fancige.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fancige.javabean.User;
import com.fancige.main.UserManager;
import com.fancige.util.StringHandler;

@WebServlet("/register")
public class Register extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String code = request.getParameter("code");

		if (username != null && password != null && code != null)
		{
			// Checks verification code.
			if (session.getAttribute("code") == null || !code
					.equalsIgnoreCase((String) session.getAttribute("code")))
			{
				// Tells the browser verification code is wrong.
				out.print("code");
			}
			// Checks Length.
			else if (username.length() < UserManager.USERNAME_LENGTH_MIN
					|| username.length() > UserManager.USERNAME_LENGTH_MAX
					|| password.length() < UserManager.PASSWORD_LENGTH_MIN
					|| password.length() > UserManager.PASSWORD_LENGTH_MAX)
			{

			}
			// Checks illegal chars.
			else if (StringHandler.containsIllegalChar(username,
					UserManager.USERNAME_LEGAL_CHARS)
					|| StringHandler.containsIllegalChar(password,
							UserManager.PASSWORD_LEGAL_CHARS)
					|| StringHandler.isAllDigits(username))
			{

			}
			// Checks whether the username exists or not.
			else if (UserManager.isExist(UserManager.USERNAME, username))
			{

			}
			// Succeed to register.
			else
			{
				session.setAttribute("username", username);
				// Creates userid.
				String userid = UserManager.createUserid();
				while (UserManager.isExist(UserManager.USERID, userid))
				{
					userid = UserManager.createUserid();
				}
				User user = new User(userid, username, password);
				UserManager.createUser(user);
				out.print("success");
			}
			session.removeAttribute("code");
		}
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		String username = req.getParameter("username");
		if (username != null)
		{
			out.print(UserManager.isExist(UserManager.USERNAME, username));
		}
	}
}
