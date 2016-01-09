package com.fancige.util;

import java.util.Calendar;

public class TimeTool
{
	public static String getCurrentTime()
	{
		Calendar c = Calendar.getInstance();
		int ms = c.get(Calendar.MILLISECOND);
		int min = c.get(Calendar.MINUTE);
		int s = c.get(Calendar.SECOND);
		int h = c.get(Calendar.HOUR_OF_DAY);

		return h + ":" + min + ":" + s + ":" + ms;
	}
	
	public static String getCurrentDate()
	{
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int date = c.get(Calendar.DATE);
		return year + "-" + month + "-" + date;
	}
	
	public static void main(String[] args)
	{
		System.out.println(TimeTool.getCurrentTime());
	}
}
