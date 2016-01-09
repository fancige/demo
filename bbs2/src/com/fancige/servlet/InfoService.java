package com.fancige.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Info
 */
@WebServlet("/infoService")
public class InfoService extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		System.out.println(Calendar.getInstance().getTime() + " com.fancige.servlet.InfoService init");
		super.init();
	}

	@Override
	public void destroy() {
		System.out.println(Calendar.getInstance().getTime() + " com.fancige.servlet.InfoService destroy");
		super.destroy();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String type = req.getParameter("type");
		resp.setContentType("text/plain;charset=utf-8");
		PrintWriter out = resp.getWriter();
		HttpSession session = req.getSession();
		if ("userid".equals(type)) {
			out.print(session.getAttribute("userid"));
		}
	}
}
