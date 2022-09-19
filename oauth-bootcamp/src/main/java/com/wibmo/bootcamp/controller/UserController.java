package com.wibmo.bootcamp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wibmo.bootcamp.constant.APIConstants;
import com.wibmo.bootcamp.model.entity.UserDetails;
import com.wibmo.bootcamp.model.resp.UserDetailsRes;
import com.wibmo.bootcamp.service.UserDetailsServiceImplementation;

@RestController
public class UserController {

	@Autowired private UserDetailsServiceImplementation userService;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@RequestMapping(path = APIConstants.USER_DETAILS_FETCH, method = RequestMethod.GET)
	public ResponseEntity<UserDetailsRes> getUserDetails(@RequestParam String email) {
		
		LOGGER.info("Fetching user details...");
		
		if(email.isBlank() || email.isEmpty())
			new ResponseEntity<UserDetailsRes>(HttpStatus.BAD_REQUEST);
		
		try {
			UserDetails user = userService.getUserByEmail(email);
			return new ResponseEntity<UserDetailsRes>(this.generateResFromUser(user), HttpStatus.ACCEPTED);		
		} catch (Exception e) {
			return new ResponseEntity<UserDetailsRes>(HttpStatus.BAD_REQUEST);
		}
	}
	
	private UserDetailsRes generateResFromUser(UserDetails user) {
		UserDetailsRes res = new UserDetailsRes();
		res.setEmail(user.getEmail());
		res.setName(user.getName());
//		res.setPassword(user.getPassword());
		res.setToken(user.getJwt_token());
		res.setUsername(user.getUsername());
		res.setPhone(user.getPhone());
		return res;
	}

}
