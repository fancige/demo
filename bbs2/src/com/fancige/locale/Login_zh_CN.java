package com.fancige.locale;

import java.util.ListResourceBundle;

public class Login_zh_CN extends ListResourceBundle
{
	private static Object[][] contents;
	static
	{
		contents = Load.load("Login_zh_CN.properties");
	}

	@Override
	protected Object[][] getContents()
	{
		return contents;
	}
}
