package com.fancige.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.fancige.manager.FileManager;

@WebServlet("/upload")
@MultipartConfig
public class Upload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = resp.getWriter();

		if ("validate".equals(req.getParameter("type"))) {
			switch (validate(req.getParameter("filename"))) {
			case EMPTY:
				out.print("文件名不能为空");
				break;
			case ILLEGAL_CHAR:
				out.print("文件名不能包含以下字符：\\ / \" * ? < > | :");
				break;
			case OVER_LENGTH:
				out.print("文件名最多" + MAX_LENGTH + "个字符");
				break;
			case EXIST:
				out.print("文件名已存在");
				break;
			default:
				out.print("");
			}
		} else if ("upload".equals(req.getParameter("type"))) {
			Collection<Part> parts = null;
			boolean ajax = "true".equals(req.getParameter("ajax"));
			try {
				parts = req.getParts();
				if (ajax) {
					out.print("run:上传完成，正在保存文件...");
					resp.flushBuffer();
				}
			} catch (Exception e) {
				if (ajax)
					out.print("fail:文件上传失败！");
				else
					out.print("文件上传失败！");
				e.printStackTrace();
				return;
			}

			for (Part part : parts) {
				String filename = part.getName();

				if (validate(filename) == EMPTY)
					filename = Calendar.getInstance().getTimeInMillis() + "";

				if (validate(filename) == ILLEGAL_CHAR)
					filename = filename.replaceAll("[\\\\/\"\\*?<>:|]", "");

				if (validate(filename) == OVER_LENGTH) {
					filename = filename.substring(filename.length() - MAX_LENGTH);
				}

				if (validate(filename) == EXIST) {
					String ext = "";
					String prefix = "";
					int dot = filename.lastIndexOf(".");
					if (dot != -1) {
						prefix = filename.substring(0, dot);
						ext = filename.substring(dot);
					}
					int i = 0;
					while (validate(filename) == EXIST) {
						filename = prefix + "(" + ++i + ")" + ext;
					}
				}

				File file = new File(FileManager.DISPOSITION_PATH + filename);

				try (InputStream in = part.getInputStream()) {
					try (FileOutputStream fos = new FileOutputStream(file)) {
						int length;
						byte[] b = new byte[1024 * 512];
						while ((length = in.read(b)) != -1) {
							fos.write(b, 0, length);
						}
					}
					String s = ajax ? "success:上传成功！" : "文件：" + part.getName() + " 上传成功！";
					out.println(s);
				} catch (Exception e) {
					file.delete();
					if (ajax)
						out.print("fail:文件保存到服务器时发生异常，上传失败！");
					else
						out.println("文件：" + filename + " 保存到服务器时发生异常！");
					e.printStackTrace();
				}
			}
		}
	}

	private static final int OK = 0;
	private static final int EMPTY = 1;
	private static final int ILLEGAL_CHAR = 2;
	private static final int OVER_LENGTH = 3;
	private static final int EXIST = 4;

	private static final int MAX_LENGTH = 100;

	private int validate(String filename) {
		if (filename == null || filename.isEmpty())
			return EMPTY;
		else if (filename.matches(".*[\\\\/\"\\*?<>:|].*"))
			return ILLEGAL_CHAR;
		else if (filename.length() > MAX_LENGTH)
			return OVER_LENGTH;
		else if (FileManager.exist(filename))
			return EXIST;
		else
			return OK;
	}
}
