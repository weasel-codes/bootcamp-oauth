package com.wibmo.bootcamp.configs;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.wibmo.bootcamp.constant.APIConstants;
import com.wibmo.bootcamp.model.entity.UserDetails;
import com.wibmo.bootcamp.service.UserDetailsService;
import com.wibmo.bootcamp.utils.JWTUtils;

import io.jsonwebtoken.ExpiredJwtException;

@Configuration
public class JWTAuthInterceptor extends HandlerInterceptorAdapter {

	@Autowired private BCryptPasswordEncoder encoder;
	@Autowired	private JWTUtils jwtUtil;
	@Autowired	private UserDetailsService service;

	private static final Logger lOGGER = LoggerFactory.getLogger(JWTAuthInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		lOGGER.info("[Inside PRE Handle interceptor] : " + request.getRequestURL());
		String token = request.getHeader(APIConstants.AUTH_HEADER_PARAMETER_AUTHERIZATION);
		System.out.println(token);

		try {
			String[] sub = jwtUtil.getSubject(token).split("#");
			String email = sub[0];
			String pass = sub[1];

			lOGGER.info("Email and Pass from api token : " + email + " @ " + pass);
			boolean user = validateUser(email, pass);

			if (user == false) {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				return false;
			}

		} catch (ExpiredJwtException e) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			return false;
		}

		response.setStatus(HttpStatus.ACCEPTED.value());
		return true;
	}

	private boolean validateUser(String email, String pass) throws AuthenticationException {

		UserDetails user = this.service.getUserByEmail(email);
		lOGGER.info("Password from table in encoded form : " + user);
		lOGGER.info("Password from JWT token : " + pass + "and password from getpass: " + user.getPassword()
				+ " &&&&&&&& " + pass.equals(user.getPassword()));
		if (user == null || encoder.matches(pass, user.getPassword()))
			return false;
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		lOGGER.info("[Inside POST Handle Interceptor]" + request.getRequestURI());
	}

}