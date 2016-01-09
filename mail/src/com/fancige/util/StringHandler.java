package com.fancige.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHandler
{
	/**
	 * check whether the seq contains the chars which is not allowed, you should
	 * specify what chars can be allowed through the regex.
	 * 
	 * @param seq
	 *            the string to be checked.
	 * @param regex
	 *            as the parameter of Pattern.compile().
	 * @return true if the seq contains illeagal Chars, false if not.
	 */
	public static boolean containsIllegalChar(String seq, String regex)
	{
		int charLength = getCharLength(seq, regex);
		if (charLength != seq.length())
		{
			return true;
		}
		return false;
	}

	/**
	 * Tests whether the string just contains digits.
	 * 
	 * @param str
	 *            The target string.
	 * @return True if the string is pure numbers, or false if not.
	 */
	public static boolean isAllDigits(String str)
	{
		if (null == str || str.isEmpty())
			return false;

		for (int i = 0; i < str.length(); i++)
		{
			if (str.charAt(i) > 57 || str.charAt(i) < 48)
				return false;
		}
		return true;
	}

	/**
	 * Gets the special character's length from the string.
	 * 
	 * @param str
	 *            The string.
	 * @param regex
	 *            Regular expression which specifies the characters need to be
	 *            contained.
	 * @return the special characters' length.
	 */
	public static int getCharLength(String str, String regex)
	{
		int count = 0;
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		while (m.find())
		{
			count++;
		}
		return count;
	}
	
	public static String getRandomNumber(int length)
	{
		StringBuffer randomNumber = new StringBuffer();
		int firstNumber;
		do
		{
			firstNumber = (int) (Math.random() * 10);
		}
		while (0 == firstNumber);
		randomNumber.append(firstNumber);

		for (int i = 0; i < length - 1; i++)
		{
			randomNumber.append((int) (Math.random() * 10));
		}
		return randomNumber.toString();
	}

	public static int parseInt(String str, int def)
	{
		try
		{
			return Integer.parseInt(str);
		}
		catch (NumberFormatException e)
		{
			return def;
		}
	}

	public static Map<String, String> toMap(String str)
	{
		HashMap<String, String> map = new HashMap<>();
		if (str != null && !str.isEmpty())
		{
			str = str.trim();
			str = str.replaceAll("^\\{|\\}$", "");
			String[] ss = str.split(",");
			for (String s : ss)
			{
				String[] as = s.split("=");
				if (as.length == 2)
				{
					map.put(as[0].trim(), as[1].trim());
				}
			}
		}
		return map;
	}

	// public static String utf8(String iso88591)
	// throws UnsupportedEncodingException
	// {
	// if (iso88591 == null || iso88591.isEmpty())
	// return iso88591;
	// else
	// return new String(iso88591.getBytes("iso-8859-1"), "utf-8");
	// }

	public static String toStackTrace(Throwable th)
	{
		String mess = th.toString() + "\n";
		StackTraceElement[] es = th.getStackTrace();
		for (StackTraceElement e : es)
		{
			mess = mess + "\t" + e.toString() + "\n";
		}

		return th.getCause() == null ? mess : mess + "cause by:\n"
				+ toStackTrace(th.getCause());
	}

	public static boolean isEmpty(String... args)
	{
		for (String arg : args)
		{
			if (arg == null || arg.isEmpty())
				return true;
		}
		return false;
	}
	
	/**
	 * <p>
	 * Transforms the <code>id</code> between the Element id (like "_12345678")
	 * and Object id (like "12345678").
	 * </p>
	 * Some types, such as {@link Post}, contains a id, but it can not be saved
	 * into a XML file as a id attribute because it is pure number, you need to
	 * call this method to add the prefix "_". When you get it from the XML
	 * file, you also need to call this method to remove the prefix.
	 * 
	 * @param id
	 *            the Object id or Element id.
	 * @return Object id if the parameter is Element id, otherwise Element id.
	 */
	public static String transformId(String id)
	{
		return id.startsWith("_") ? id.substring(1) : "_" + id;
	}
}
