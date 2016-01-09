package com.fancige.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fancige.util.VerificodeProvider;

/**
 * Servlet implementation class VerificationCode
 */
@WebServlet("/verificode")
public class Verificode extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		resp.setContentType("image/png");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setHeader("Pragma", "no-cache");
		resp.setDateHeader("Expires", 0);
		VerificodeProvider.getVerificationCode(req, resp);
	}
}
