package com.fancige.tag;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.fancige.manager.FileManager;

public class Setting extends SimpleTagSupport {
	private String page;

	public void setPage(String page) {
		this.page = page;
	}

	@Override
	public void doTag() throws JspException, IOException {
		String locale = (String) getJspContext().getAttribute("locale", PageContext.SESSION_SCOPE);
		String path = FileManager.LOCALE_WEBCONTENT + page + "_" + locale + ".txt";
		Properties props = FileManager.loadProperties(path);
		for (Object key : props.keySet()) {
			getJspContext().setAttribute((String) key, props.get(key));
		}
	}
}
