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
public class CORSFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}
	
	@Override
	public void destroy() {

	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		//System.out.println("CORS Filter");
		HttpServletResponse res = (HttpServletResponse) response;
		res.addHeader("Access-Control-Allow-Origin", "*"); // Allows any domain to make a request
		res.addHeader("Access-Control-Allow-Methods", "*"); // Allows all HTTP methods
		res.addHeader("Access-Control-Allow-Headers", "*"); // Allows all types of headers
		//res.addHeader("Access-Control-Request-Headers", "");
		chain.doFilter(request, response);
	}

}
