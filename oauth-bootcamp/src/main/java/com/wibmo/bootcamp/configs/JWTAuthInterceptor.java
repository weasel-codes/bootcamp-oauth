package com.wibmo.bootcamp.configs;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.wibmo.bootcamp.model.entity.UserDetails;
import com.wibmo.bootcamp.service.UserDetailsService;
import com.wibmo.bootcamp.utils.JWTUtils;

@Configuration
public class JWTAuthInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired private JWTUtils jwtUtil;
	@Autowired private UserDetailsService service;

	private static final Logger lOGGER = LoggerFactory.getLogger(JWTAuthInterceptor.class);
	private static final String AUTH_HEADER_PARAMETER_AUTHERIZATION = "auth";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		lOGGER.info("[Inside PRE Handle interceptor] : " + request.getRequestURL());
		String token = request.getHeader(AUTH_HEADER_PARAMETER_AUTHERIZATION);
		System.out.println(token);
		
		String[] sub = jwtUtil.getSubject(token).split("#");
		String phone = sub[0];
		String email = sub[1];
		String pass = sub[2];
		
		System.out.println(email + " : " + pass);
		
		boolean user = validateUser(email, pass);
		
		if(user==false) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			return false;
		} else {
			response.setStatus(HttpStatus.ACCEPTED.value());
			return true;			
		}
	}
	
	private boolean validateUser(String email, String pass) throws AuthenticationException {
		
		UserDetails user = this.service.getUserByEmail(email);
		if(user==null || !user.getPassword().equals(pass)) return false;
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		lOGGER.info("[Inside POST Handle Interceptor]" + request.getRequestURI());
	}

}