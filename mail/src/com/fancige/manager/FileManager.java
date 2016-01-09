package com.fancige.manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Properties;

import com.fancige.util.MyLogger;
import com.fancige.util.MyLogger.Name;

public class FileManager
{
	private String queryResult;
	public static final String ROOT = "e:/workspace/mail/data";
	public static final String DISPOSITION = ROOT + "/disposition/";
	public static final String LOCALE_WEBCONTENT = ROOT + "/locale/WebContent/";
	public static final String LOCALE_SRC = ROOT + "/locale/src/";
	public static final String CONF = ROOT + "/conf/";
	public static final String CONF_MAIL = ROOT + "/conf/mail/";
	public static final String LOG = ROOT + "/log/";

	/**
	 * 
	 * @param file
	 *            The path starting to query.
	 * @param target
	 *            The name of the target file.
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
		fm.query(new File(ROOT), filename);
		return fm.queryResult;
	}

	public static File[] listFiles()
	{
		File dir = new File(DISPOSITION);
		File[] files = dir.listFiles();
		Arrays.sort(files, new Comparator<File>()
		{
			@Override
			public int compare(File f1, File f2)
			{
				return f1.lastModified() > f2.lastModified() ? -1 : 1;
			}
		});
		return files;
	}

	public static boolean exist(String filename)
	{
		File dir = new File(DISPOSITION);
		File[] files = dir.listFiles();
		for (File file : files)
		{
			if (file.getName().equalsIgnoreCase(filename))
			{
				return true;
			}
		}
		return false;
	}

	public static Properties loadProperties(String path)
	{
		Properties props = new Properties();
		try (BufferedReader br = Files.newBufferedReader(Paths.get(path)))
		{
			String s;
			while ((s = br.readLine()) != null)
			{
				String[] kv = s.split("=");
				if (kv.length == 2)
					props.put(kv[0].trim(), kv[1].trim());
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			MyLogger.getLogger(Name.error).severe(e.toString());
		}
		return props;
	}
}
