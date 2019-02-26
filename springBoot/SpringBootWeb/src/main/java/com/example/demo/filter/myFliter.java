package com.example.demo.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
@WebFilter(filterName="myFliter",urlPatterns="/*")
public class myFliter implements Filter {
	
	String encoding = null;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		encoding = filterConfig.getInitParameter("encoding");
		Filter.super.init(filterConfig);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if(request instanceof HttpServletRequest) {
			HttpServletRequest re = (HttpServletRequest) request;
			Enumeration<String> parameters = re.getParameterNames();
			while(parameters.hasMoreElements()) {
				String param = parameters.nextElement();
				String value = re.getParameter(param);
				System.out.println("param:" + param + ",value:" + value);
			}
		}
		chain.doFilter(request, response);
	}

}
