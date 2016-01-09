package com.fancige.filter;

import java.io.IOException;
import java.util.Enumeration;

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

/**
 * Servlet Filter implementation class Global
 */
@WebFilter("/*")
public class GlobalFilter implements Filter {
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		String url = req.getRequestURL().toString();

		// cookies
		if (session.getAttribute("loginID") == null && req.getCookies() != null) {
			Cookie[] cs = req.getCookies();
			for (Cookie c : cs) {
				if ("LOGIN_ID".equals(c.getName())) {
					String id = UserManager.getUnique(Column.userid, c.getValue());
					if (UserManager.isUserid(id)) {
						session.setAttribute("loginID", c.getValue());
					}
					break;
				}
			}
		}

		// https to http
		if (!url.contains("/sec/") && url.startsWith("https") && (url.endsWith(".jsp") || url.endsWith(".html"))) {
			url = url.replaceFirst("https", "http");
			if ("get".equalsIgnoreCase(req.getMethod())) {
				// append parameters following the url
				String params = "";
				Enumeration<String> names = req.getParameterNames();
				while (names.hasMoreElements()) {
					String name = names.nextElement();
					params = name + "=" + req.getParameter(name) + "&";
				}
				if (!params.isEmpty()) {
					params = params.substring(0, params.length() - 1);
					url = url + "?" + params;
				}
			}
			resp.sendRedirect(url);
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		MyLogger.getLogger(Name.debug).info("ok");
	}

	@Override
	public void destroy() {
		MyLogger.getLogger(Name.debug).info("ok");
	}
}
