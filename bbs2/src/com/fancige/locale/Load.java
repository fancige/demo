package com.fancige.locale;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Set;

import com.fancige.manager.FileManager;

public class Load
{
	protected static Object[][] load(String filename)
	{
		String path = FileManager.LOCALE_PATH + filename;
		Properties props = new Properties();
		try (InputStream in = Files.newInputStream(Paths.get(path)))
		{
			props.load(in);
			Set<Object> sets = props.keySet();
			Object[][] contents = new Object[sets.size()][2];
			int i = 0;
			for (Object o : sets)
			{
				contents[i][0] = o;
				contents[i][1] = props.get(o);
				++i;
			}
			return contents;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			throw new RuntimeException("failed to load: " + path);
		}
	}
}
