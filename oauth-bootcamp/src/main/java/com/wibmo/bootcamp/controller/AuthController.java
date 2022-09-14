package com.wibmo.bootcamp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wibmo.bootcamp.model.entity.UserDetails;
import com.wibmo.bootcamp.model.req.SignUpReq;
import com.wibmo.bootcamp.model.resp.SignUpRes;
import com.wibmo.bootcamp.service.UserDetailsServiceImplementation;
import com.wibmo.bootcamp.utils.JWTUtils;

@RestController
public class AuthController {

	@Autowired UserDetailsServiceImplementation userService;
	@Autowired JWTUtils jwtutils;

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

	@RequestMapping(path = "/auth/signup", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<SignUpRes> signUp(@RequestBody SignUpReq req) {

		LOGGER.info("signUp() : " + req);
		SignUpRes res = new SignUpRes();

		if (validateSignupReq(req) == false) {
			LOGGER.error("Incorrect Request : " + req);
			return new ResponseEntity<SignUpRes>(res, HttpStatus.BAD_REQUEST);
		}

		UserDetails user = generateUserFromRequest(req);
		userService.createuser(user);

		LOGGER.info("Request verified : " + req);
		res.setEmail(req.getEmail());
		res.setName(req.getName());
		res.setPassword(req.getPassword());
		res.setPhone(req.getPhone());
		res.setUsername(req.getUsername());
		res.setToken(jwtutils.generateAccessToken(user));

		return new ResponseEntity<SignUpRes>(res, HttpStatus.OK);
	}

	private boolean validateSignupReq(SignUpReq req) {
		if (req.getEmail() == null || req.getPhone() == 0 || req.getPassword() == null || req.getUsername() == null)
			return false;
		return true;
	}

	private UserDetails generateUserFromRequest(SignUpReq req) {
		return new UserDetails(req.getUsername(), req.getPassword(), req.getName(), req.getPhone(), req.getEmail());
	}
}
