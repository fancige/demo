package com.fancige.servlet;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/service")
public class Service extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		resp.setContentType("plain/text; charset=utf-8");
//		HttpSession sn = req.getSession();
		// if (sn.getAttribute("userid") != null)
		// {
		String id = req.getParameter("id");
		if ("finfos".equals(id))
		{
			JsonArrayBuilder jab = Json.createArrayBuilder();
			JsonObject folder = Json.createObjectBuilder().add("name", "收件箱")
					.add("fid", "inbox").build();
			jab.add(folder);
			folder = Json.createObjectBuilder().add("name", "垃圾箱")
					.add("fid", "garbage").build();
			jab.add(folder);
			folder = Json.createObjectBuilder().add("name", "已删除的邮件")
					.add("fid", "delete").build();
			jab.add(folder);
			JsonWriter jw = Json.createWriter(resp.getWriter());
			jw.writeArray(jab.build());
			jw.close();
		}
		// }
		// else
		// {
		//
		// }
	}
}
