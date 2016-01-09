package com.fancige.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.fancige.servlet.XssHttpServletRequestWrapper;

@WebFilter("/*")
public class XSSFilter implements Filter
{

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException
	{

		chain.doFilter(new XssHttpServletRequestWrapper(
				(HttpServletRequest) request), response);
	}

	@Override
	public void destroy()
	{
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException
	{
	}
}
