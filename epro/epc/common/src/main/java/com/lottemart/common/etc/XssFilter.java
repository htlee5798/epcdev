package com.lottemart.common.etc;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XssFilter implements Filter{
	
	private static final Logger logger = LoggerFactory.getLogger(XssFilter.class);
	
	public static final String[] exceptionUrl = {"/banner/banner/", "/category/basicCategory/"
		,"/newContents/", "/promotion/eventMgt/","/exhibition/"};
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
			
		HttpServletRequest request = (HttpServletRequest)servletRequest;
		String servletPath = request.getServletPath();

		boolean fiterFlag = true;
		try {
			for(String action : exceptionUrl){
				 if(servletPath.indexOf(action) > -1){
					 fiterFlag = false;
				 }
			 }

			if(fiterFlag){
				chain.doFilter( new RequestWrapper(request), response );
			}else{
				chain.doFilter(request, response);
			}
		}catch (Exception e) {
			logger.error("error --> " + e.getMessage(),e);
			throw e;
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
