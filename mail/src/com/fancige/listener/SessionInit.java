package com.fancige.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionInit implements HttpSessionListener
{
	public void sessionCreated(HttpSessionEvent se)
	{
		se.getSession().setAttribute("locale", "zh_CN");
	}

	public void sessionDestroyed(HttpSessionEvent se)
	{
	}
}
