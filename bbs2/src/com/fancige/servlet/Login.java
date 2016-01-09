package com.fancige.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fancige.manager.UserManager;
import com.fancige.manager.UserManager.Column;

@WebServlet("/sec/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/plain;charset=utf-8");
		HttpSession session = req.getSession();
		PrintWriter out = resp.getWriter();

		String userkey = req.getParameter("userkey");
		String password = req.getParameter("password");
		String verificode = req.getParameter("verificode");
		String autoLogin = req.getParameter("autoLogin");
		Object sessionCode = session.getAttribute("verificode");

		// Removes the verification code to prevent being used again.
		session.removeAttribute("verificode");

		if (null == sessionCode || !sessionCode.toString().equalsIgnoreCase(verificode)) {
			out.print("verificode");
		}
		// Tests if the userkey and password is legal.
		else if (!UserManager.login(userkey, password)) {
			out.print("fail");
		}
		// success to login;
		else {
			String userid = UserManager.getUnique(Column.userid, userkey);
			session.setAttribute("userid", userid);
			String loginID = UserManager.getUnique(Column.loginID, userkey);
			session.setAttribute("loginID", loginID);
			String loginKey = UserManager.get(userid, Column.loginKey);

			if ("true".equals(autoLogin) && loginKey != null && loginID != null) {
				String root = req.getContextPath();
				Cookie c = new Cookie("LOGIN_KEY", loginKey);
				c.setMaxAge(7 * 24 * 60 * 60);
				c.setPath(root + "/sec/");
				c.setHttpOnly(true);
				c.setSecure(true);
				resp.addCookie(c);

				c = new Cookie("LOGIN_ID", loginID);
				c.setMaxAge(7 * 24 * 60 * 60);
				c.setPath(root + "/");
				resp.addCookie(c);
			}
			out.print("success");
		}
	}
}
