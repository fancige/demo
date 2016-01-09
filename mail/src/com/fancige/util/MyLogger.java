package com.fancige.util;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.fancige.manager.FileManager;

public class MyLogger
{
	public enum Name
	{
		debug, event, error
	}

	public static Logger getLogger(Name name)
	{
		String path = FileManager.LOG + name + TimeTool.getCurrentDate()
				+ ".txt";
		try
		{
			new File(path).createNewFile();
			Logger logger = Logger.getAnonymousLogger();
			FileHandler fhd = new FileHandler(path, true)
			{
				@Override
				public synchronized void publish(LogRecord record)
				{
					super.publish(record);
					close();
				}
			};
			fhd.setFormatter(new SimpleFormatter());
			logger.addHandler(fhd);
			return logger;
		}
		catch (SecurityException | IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
