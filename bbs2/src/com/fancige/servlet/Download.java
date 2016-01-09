package com.fancige.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fancige.manager.FileManager;

@WebServlet("/download")
public class Download extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/plain; charset=utf-8");
		if ("list".equals(req.getParameter("type"))) {
			File[] files = FileManager.listFiles();
			if (files.length == 0) {
				resp.getWriter().print("");
			} else {
				JsonArrayBuilder jab = Json.createArrayBuilder();
				DateFormat df = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT,
						Locale.getDefault());
				for (File file : files) {
					Calendar cal = Calendar.getInstance();
					cal.setTimeInMillis(file.lastModified());
					String modified = df.format(cal.getTime());
					String url = "resource/" + file.getName();
					jab.add(Json.createObjectBuilder().add("name", file.getName()).add("size", file.length())
							.add("modification", modified).add("url", url));
				}
				String result;
				if (req.getParameter("jsonp") == null) {
					result = jab.build().toString();
				} else {
					result = req.getParameter("jsonp") + "(" + jab.build().toString() + ");";
				}
				resp.getWriter().println(result);
			}
		} else if ("download".equals(req.getParameter("type"))) {
			String filename = req.getParameter("filename");
			if (filename == null || filename.isEmpty()) {
				resp.getWriter().println("请指定下载文件的文件名！");
				return;
			}
			File file = new File(FileManager.DISPOSITION_PATH + filename);
			if (!file.exists()) {
				resp.getWriter().println("指定的下载文件不存在！");
				return;
			}

			resp.setContentType("application/x-msdownload");
			resp.setHeader("Content-Length", String.valueOf(file.length()));
			resp.setHeader("Accept-Ranges", "bytes");

			long pos = 0;
			if (req.getHeader("Range") != null) {
				Matcher mc = Pattern.compile("bytes=(\\d+)-").matcher(req.getHeader("Range"));
				if (mc.matches() && mc.groupCount() == 1) {
					pos = Long.parseLong(mc.group(1));
					resp.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
					resp.setHeader("Content-Range", "bytes " + pos + "-" + (file.length() - 1) + "/" + file.length());
				}
			}
			resp.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "utf-8"));
			try (FileInputStream fis = new FileInputStream(file)) {
				fis.skip(pos);
				try (ServletOutputStream os = resp.getOutputStream()) {
					byte[] bs = new byte[1024 * 512];
					int len;
					while ((len = fis.read(bs)) != -1) {
						os.write(bs, 0, len);
					}
				}
			} catch (IOException e) {
				String mess = e.getMessage() != null ? e.getMessage() : e.getCause().getMessage();
				System.err.println(mess);
			}
		}
	}
}
