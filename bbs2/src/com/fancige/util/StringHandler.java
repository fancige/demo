package com.fancige.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHandler {
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
	public static boolean containsIllegalChar(String seq, String regex) {
		int charLength = getCharLength(seq, regex);
		if (charLength != seq.length()) {
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
	public static boolean isAllDigits(String str) {
		if (null == str || str.isEmpty())
			return false;

		for (int i = 0; i < str.length(); i++) {
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
	public static int getCharLength(String str, String regex) {
		int count = 0;
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		while (m.find()) {
			count++;
		}
		return count;
	}

	public static int parseInt(String str, int def) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return def;
		}
	}

	public static Map<String, String> toMap(String str) {
		HashMap<String, String> map = new HashMap<>();
		if (str != null && !str.isEmpty()) {
			str = str.trim();
			str = str.replaceAll("^\\{|\\}$", "");
			String[] ss = str.split(",");
			for (String s : ss) {
				String[] as = s.split("=");
				if (as.length == 2) {
					map.put(as[0].trim(), as[1].trim());
				}
			}
		}
		return map;
	}

}
