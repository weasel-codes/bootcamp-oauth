package com.wibmo.bootcamp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wibmo.bootcamp.constant.APIEndPoint;
import com.wibmo.bootcamp.model.entity.UserDetails;
import com.wibmo.bootcamp.model.req.SignInReq;
import com.wibmo.bootcamp.model.req.SignUpReq;
import com.wibmo.bootcamp.model.resp.SignUpRes;
import com.wibmo.bootcamp.service.UserDetailsServiceImplementation;
import com.wibmo.bootcamp.utils.JWTUtils;

@RestController
public class AuthController {

	@Autowired BCryptPasswordEncoder encoder;
	@Autowired UserDetailsServiceImplementation userService;
	@Autowired JWTUtils jwtutils;

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

	@RequestMapping(path = APIEndPoint.SIGNUP, method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<SignUpRes> signUp(@RequestBody SignUpReq req) {

		LOGGER.info("signUp() : " + req);
		SignUpRes res = new SignUpRes();

		if (validateSignupReq(req) == false) {
			LOGGER.error("Incorrect Request : " + req);
			return new ResponseEntity<SignUpRes>(res, HttpStatus.BAD_REQUEST);
		}

		UserDetails user = generateUserFromRequest(req);
		String token = jwtutils.generateAccessToken(user);
		user.setJwt_token(token);

		LOGGER.info("User to be persisted : " + user);
		userService.createuser(user);

		LOGGER.info("Request verified : " + req);
		res.setEmail(req.getEmail());
		res.setName(req.getName());
		res.setPassword(req.getPassword());
		res.setPhone(req.getPhone());
		res.setUsername(req.getUsername());
		res.setToken(user.getJwt_token());

		return new ResponseEntity<SignUpRes>(res, HttpStatus.OK);
	}
	
	@RequestMapping(path = APIEndPoint.LOGIN, method = RequestMethod.POST)
	public ResponseEntity<String> signIn(@RequestBody SignInReq req) {

		if(validateSignInReq(req) == false) {
			LOGGER.error("Incorrect Request : " + req);
			return new ResponseEntity<String>("", HttpStatus.BAD_REQUEST);
		}
		
		UserDetails user = userService.getUserByEmail(req.getEmail());

		if(!encoder.encode(req.getPassword()).equals(user.getPassword()))
			return new ResponseEntity<String>("Incorrect Passoword", HttpStatus.UNAUTHORIZED);		

		try {
			jwtutils.isExpired(user.getJwt_token());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			LOGGER.info("Generating Token...");
			String token = jwtutils.generateAccessToken(user);
			user.setJwt_token(token);
			userService.updateUser(user);
		}
	
		return new ResponseEntity<String>(user.getJwt_token(), HttpStatus.OK);
	}	

	private boolean validateSignupReq(SignUpReq req) {
		if (req==null || req.getEmail() == null || req.getPhone() == 0 || req.getPassword() == null || req.getUsername() == null)
			return false;
		return true;
	}

	private boolean validateSignInReq(SignInReq req) {
		if (req==null || req.getEmail() == null || req.getPassword() == null)
			return false;
		return true;
	}

	private UserDetails generateUserFromRequest(SignUpReq req) {
		return new UserDetails(req.getUsername(), encoder.encode(req.getPassword()), req.getName(), req.getPhone(), req.getEmail(), null);
	}
}
