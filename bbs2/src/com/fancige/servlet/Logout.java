package com.fancige.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/sec/user/logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String root = req.getContextPath();
		Cookie c = new Cookie("LOGIN_ID", "deleted");
		c.setMaxAge(0);
		c.setPath(root + "/");
		resp.addCookie(c);

		c = new Cookie("LOGIN_KEY", "deleted");
		c.setMaxAge(0);
		c.setPath(root + "/sec/");
		resp.addCookie(c);

		req.getSession().invalidate();
		resp.sendRedirect(root + "/forum.jsp");
	}
}
