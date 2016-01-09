package com.fancige.tag;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspException;

import com.fancige.util.StringHandler;

public class StringTag extends ResultTagSupport {
	private String string;
	private Map<String, String> param;

	public void setString(String string) {
		this.string = string;
	}

	public void setParam(String param) {
		this.param = StringHandler.toMap(param);
	}

	@Override
	public void doTag() throws JspException, IOException {
		if (string != null) {
			if ("split".equals(name) && param.containsKey("1")) {
				doByWay(string.split(param.get("1")));
			}
		}
	}
}
