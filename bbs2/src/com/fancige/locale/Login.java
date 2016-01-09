package com.fancige.locale;

import java.util.ListResourceBundle;

public class Login extends ListResourceBundle
{
	private static Object[][] contents;
	static
	{
		contents = Load.load("Login.properties");
	}

	@Override
	protected Object[][] getContents()
	{
		return contents;
	}
}
