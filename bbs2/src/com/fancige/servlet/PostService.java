package com.fancige.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fancige.bean.Post;
import com.fancige.manager.PostManager;

@WebServlet("/sec/user/postService")
public class PostService extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=utf-8");
		req.setCharacterEncoding("utf-8");
		String type = req.getParameter("type");
		String userid = (String) req.getSession().getAttribute("userid");
		if ("publish".equals(type)) {
			String title = req.getParameter("title");
			String content = req.getParameter("content");
			Post post = new Post();
			post.setUserid(userid);
			post.setTitle(title);
			post.setContent(content);
			String postid = PostManager.addPost(post);
			if (null != postid) {
				resp.sendRedirect("../../view.jsp?postid=" + postid);
			}
		}
	}
}
