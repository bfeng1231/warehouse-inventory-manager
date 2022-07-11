package com.skillstorm.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = "/*")
public class LoggingFilters implements Filter {
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// Do not call super.init
	}
	
	@Override
	public void destroy() {
		
	}
	
	// Filters must override the ability to filer a request
	// Request + Response are the HTTP res and resp
	// FilterCHain is so that we can continue to the next item in the chain
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		System.out.println("Commencing LoggingFilter logs");
		System.out.println(request.getLocalAddr());
		if (request.getLocale().equals("es_US")) {
			HttpServletResponse res = (HttpServletResponse) response;
			res.setStatus(400);
		}
		System.out.println(request.getLocale());
		System.out.println("Filter complete. Commencing chain");
		chain.doFilter(request, response); // Send to the next filter/servlet
	}
	
}
