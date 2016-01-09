package com.fancige.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
public class Upload extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/plain; charset=UTF-8");
		Collection<Part> parts = null;
		try
		{
			parts = req.getParts();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return;
		}

		for (Part part : parts)
		{
			String filename = part.getName();
			
			

			File file = new File(FileManager.DISPOSITION + filename);

			try (InputStream in = part.getInputStream())
			{
				try (FileOutputStream fos = new FileOutputStream(file))
				{
					int length;
					byte[] b = new byte[1024 * 512];
					while ((length = in.read(b)) != -1)
					{
						fos.write(b, 0, length);
					}
				}
			}
			catch (Exception e)
			{
				file.delete();
				e.printStackTrace();
			}
		}
	}
}
