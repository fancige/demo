package com.fancige.util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;

import org.w3c.dom.Document;

import com.fancige.annotation.Path;
import com.fancige.annotation.Path.FileType;
import com.fancige.main.PersonalPostManager;

/**
 * <p>
 * Manages files and corresponding paths.
 * </p>
 * <strong>Note: </strong> <li>All operations is file-oriented, for example, it
 * means that the {@link #deletePost(String)} method will delete the file which
 * may store all posts of a user, instead of the post of a user (
 * {@link PersonalPostManager #deletePost(String)}). <li>The <code>filename</code>, the
 * parameter of some methods below, should contain its suffix, for example,
 * 'a.xml', 'b.txt', and only if the file is a directory.
 * 
 * @author fancige
 * 
 */
public class FileManager
{
	@Path(FileType.XML)
	public static String userlist;
	@Path(FileType.XML)
	public static String postlist;
	
	@Path(FileType.FOLDER)
	private static String posts;
	@Path(FileType.XML)
	private static String post_template;

	private static final String ROOT_PATH = "E:/workspace/bbs/data";
	static
	{
		Class<FileManager> c = FileManager.class;
		Field[] fs = c.getDeclaredFields();
		for (Field f : fs)
		{
			if (f.isAnnotationPresent(Path.class))
			{
				String filename;
				if (FileType.FOLDER == f.getAnnotation(Path.class).value())
				{
					filename = f.getName();
				}
				else
				{
					filename = f.getName() + "."
							+ f.getAnnotation(Path.class).value();
				}
				try
				{
					f.set(null, getPath(filename));
				}
				catch (IllegalArgumentException | IllegalAccessException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	private String queryResult;

	/**
	 * 
	 * @param file The path starting to query.
	 * @param target The name of the target file.
	 */
	private void query(File file, String target)
	{
		// If queryResult is not null, ok, finds it, stops the query.
		if (null == queryResult)
		{
			if (file.getName().equalsIgnoreCase(target))
			{
				queryResult = file.getAbsolutePath();
				return;
			}
			File[] fs = file.listFiles();

			if (null != fs)
			{
				for (File f : fs)
				{
					query(f, target);
				}
			}
		}
	}


	private static String getRootPath()
	{
		return ROOT_PATH;
	}

	/**
	 * Gets the path by the <code>filename</code>.
	 * 
	 * @param filename
	 *            The file name.
	 * @return The file absolute path or null if the file does not exist.
	 */
	public static String getPath(String filename)
	{
		FileManager fm = new FileManager();
		fm.query(new File(getRootPath()), filename);
		return fm.queryResult;
	}

	/**
	 * Tests whether the file exists or not.
	 * 
	 * @param filename
	 *            The file name.
	 * @return True if the file exists, false if not.
	 */
	public static boolean isExist(String filename)
	{
		return getPath(filename) != null ? true : false;
	}

	/**
	 * "post_" + userid + ".xml";
	 */
	private static String getPostName(String userid)
	{
		return "post_" + userid + ".xml";
	}
	/**
	 * Gets the path of the post by userid, if the post doesn't exist, it will
	 * be created automatically.
	 * 
	 * @param userid
	 *            The id of the owner of the post.
	 * @return The absolute path, or null if any exceptions occur.
	 */
	public static String getPostPath(String userid)
	{
		String postName = getPostName(userid);

		if (!isExist(postName))
		{
			String root = posts + File.separator;
			String source = root + "post_template.xml";
			String result = root + "post_" + userid + ".xml";
			
			if(!createFile(source, result))
				return null;
			
			XMLManager m = new XMLManager(result);
			Document doc = m.readDocument();
			doc.getDocumentElement().getFirstChild().setTextContent(userid);
			m.writeDocumentWithDTD(doc);
		}
		return getPath(postName);
	}

	/**
	 * Deletes the post by userid.
	 * 
	 * @param userid
	 *            The id of the owner of the post.
	 */
	public static void deletePost(String userid)
	{
		new File(getPostPath(userid)).delete();
	}

	/**
	 * Creates a file by a template file.
	 * 
	 * @param source
	 *            The path of the template file.
	 * @param result
	 *            The path of the file you want to create.
	 * @return True if the file creates successfully, false if not.
	 */
	private static boolean createFile(String source, String result)
	{
		FileWriter writer = null;
		FileReader reader = null;
		try
		{
			File file = new File(result);
			file.createNewFile();

			reader = new FileReader(source);
			writer = new FileWriter(result);

			char[] cbuf = new char[1024];
			int length;
			while (-1 != (length = reader.read(cbuf)))
			{
				writer.write(cbuf, 0, length);
				cbuf = new char[1024];
			}
			writer.flush();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (writer != null)
					writer.close();
				if (reader != null)
					reader.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return new File(result).exists();
	}
}
