package com.fancige.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fancige.manager.UserManager;
import com.fancige.manager.UserManager.Column;
import com.fancige.security.Toolkit;
import com.fancige.util.StringHandler;

@WebServlet("/sec/register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/plain; charset=utf-8");
		HttpSession session = req.getSession();
		PrintWriter out = resp.getWriter();
		try {
			// tests the length of the password.
			String password = req.getParameter("password");
			if (null == password || password.length() < UserManager.PASSWORD_LENGTH_MIN
					|| password.length() > UserManager.PASSWORD_LENGTH_MAX) {
				out.println("密码长度不符合规范！");
				return;
			}

			// tests the characters of the password.
			int numbers = StringHandler.getCharLength(password, "[0-9]");
			int chars = StringHandler.getCharLength(password, "[a-zA-Z]");
			if ((numbers + chars) < password.length() || numbers == password.length() || chars == password.length()) {
				out.println("密码字符要求不符合规范！");
				return;
			}

			// tests the verification code.
			String verificode = req.getParameter("verificode");
			Object sessionCode = session.getAttribute("verificode");
			// Removes the verification code to prevent being used again.
			session.removeAttribute("verificode");

			if (null == sessionCode || !sessionCode.toString().equalsIgnoreCase(verificode)) {
				out.print("验证码错误！");
				return;
			}

			// register successfully.

			String userid = UserManager.register(password);
			session.setAttribute("userid", userid);
			UserManager.set(userid, Column.loginKey, Toolkit.random(32));
			String loginID = UserManager.getUnique(Column.loginID, userid);
			session.setAttribute("loginID", loginID);
			out.print(userid);
		} catch (Exception e) {
			out.println("服务器错误！");
		}
	}
}
