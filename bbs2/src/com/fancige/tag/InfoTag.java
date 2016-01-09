package com.fancige.tag;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspException;

import com.fancige.manager.UserManager;
import com.fancige.manager.UserManager.Column;
import com.fancige.util.StringHandler;

public class InfoTag extends ResultTagSupport {
	private Map<String, String> param;

	public void setParam(String param) {
		this.param = StringHandler.toMap(param);
	}

	@Override
	public void doTag() throws JspException, IOException {
		if ("username".equals(name)) {
			String unique = param.get("loginID");
			if (unique == null)
				unique = param.get("userid");
			doByWay(UserManager.getUnique(Column.username, unique));
		}
	}
}
