package com.fancige.tag;

import java.io.IOException;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class ResultTagSupport extends SimpleTagSupport {
	public String name;
	private String var;
	private String scope;

	public void setName(String name) {
		this.name = name;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public void doByWay(Object result) {
		if (var != null) {
			int scope = PageContext.PAGE_SCOPE;
			if ("session".equals(this.scope))
				scope = PageContext.SESSION_SCOPE;
			else if ("request".equals(this.scope))
				scope = PageContext.REQUEST_SCOPE;
			else if ("application".equals(this.scope))
				scope = PageContext.APPLICATION_SCOPE;

			getJspContext().setAttribute(var, result, scope);
		} else {
			try {
				getJspContext().getOut().print(result);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
