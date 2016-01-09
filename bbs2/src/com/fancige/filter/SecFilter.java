package com.fancige.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fancige.manager.UserManager;
import com.fancige.manager.UserManager.Column;
import com.fancige.util.MyLogger;
import com.fancige.util.MyLogger.Name;

@WebFilter("/sec/*")
public class SecFilter implements Filter {
	public void init(FilterConfig fConfig) throws ServletException {
		MyLogger.getLogger(Name.debug).info("ok");
	}

	public void destroy() {
		MyLogger.getLogger(Name.debug).info("ok");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession();

		// cookies
		if (session.getAttribute("userid") == null && req.getCookies() != null) {
			Cookie[] cs = req.getCookies();
			for (Cookie c : cs) {
				if ("LOGIN_KEY".equals(c.getName())) {
					String loginID = (String) session.getAttribute("loginID");
					if (UserManager.autoLogin(loginID, c.getValue())) {
						session.setAttribute("userid", UserManager.getUnique(Column.userid, loginID));
					}
					break;
				}
			}
		}

		if (session.getAttribute("userid") == null && req.getRequestURL().toString().contains("/user/")) {
			resp.sendRedirect(req.getContextPath() + "/redirect.html");
		} else {
			chain.doFilter(request, response);
		}
	}
}
