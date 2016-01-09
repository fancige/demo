package com.fancige.util;

import java.io.File;

public class PathManager
{
	private static String getRootPath()
	{
		return "E:/workspace/chat2/data";
	}
	
	private String queryResult;
	
	private void query(File file, String target)
	{
		// If queryResult is not null, ok, finds it, stops the query.
		if (null == queryResult)
		{
			if (file.getName().equals(target))
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

	public static String getPath(String fileName)
	{
		PathManager pm = new PathManager();
		pm.query(new File(getRootPath()), fileName);
		return pm.queryResult;
	}
}
