package com.wibmo.bootcamp.configs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Configuration
public class CORSInterceptor extends HandlerInterceptorAdapter{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CORSInterceptor.class);
	
    public static final String CREDENTIALS_NAME = "Access-Control-Allow-Credentials";
    public static final String ORIGIN_NAME = "Access-Control-Allow-Origin";
    public static final String METHODS_NAME = "Access-Control-Allow-Methods";
    public static final String HEADERS_NAME = "Access-Control-Allow-Headers";
    public static final String MAX_AGE_NAME = "Access-Control-Max-Age";	
    
    @Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

//	  response.setHeader(CREDENTIALS_NAME, "true");
	  response.setHeader(ORIGIN_NAME, "http://localhost:8090");
	  response.setHeader(METHODS_NAME, "GET, OPTIONS, POST, PUT, DELETE");
	  response.setHeader(HEADERS_NAME, "Origin, X-Requested-With, Content-Type, Accept");
	  response.setHeader(MAX_AGE_NAME, "3600");
	  LOGGER.info("preHandle() : " + "ADDED HEADER...");
	  return super.preHandle(request, response, handler);
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		super.postHandle(request, response, handler, modelAndView);
//		response.addHeader("Access-Control-Allow-Headers", "http://localhost:8090");
		LOGGER.info("postHandle() : " + "ADDED HEADER...");
	}	
}