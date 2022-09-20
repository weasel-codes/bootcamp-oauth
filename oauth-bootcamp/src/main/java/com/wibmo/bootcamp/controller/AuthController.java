package com.wibmo.bootcamp.controller;

import java.sql.Timestamp;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wibmo.bootcamp.constant.APIConstants;
import com.wibmo.bootcamp.constant.Constants;
import com.wibmo.bootcamp.model.entity.UserDetails;
import com.wibmo.bootcamp.model.req.SignInReq;
import com.wibmo.bootcamp.model.req.SignUpReq;
import com.wibmo.bootcamp.model.resp.SignInResp;
import com.wibmo.bootcamp.model.resp.SignUpRes;
import com.wibmo.bootcamp.service.UserDetailsServiceImplementation;
import com.wibmo.bootcamp.utils.JWTUtils;
import com.wibmo.bootcamp.utils.OTPHandler;

@RestController
@CrossOrigin(origins = "http://localhost:8090")
public class AuthController {

	@Autowired
	private JWTUtils jwtutils;
	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
	private UserDetailsServiceImplementation userService;

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

	@RequestMapping(path = APIConstants.SIGNUP, method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<SignUpRes> signUp(@RequestBody SignUpReq req) {

		LOGGER.info("signUp() : " + req);
		SignUpRes res = new SignUpRes();

		if (validateSignupReq(req) == false) {
			LOGGER.error("Incorrect Request : " + req);
			return new ResponseEntity<SignUpRes>(res, HttpStatus.BAD_REQUEST);
		}

		UserDetails user = generateUserFromRequest(req);
		String token = jwtutils.generateAccessToken(user);
		user.setPassword(encoder.encode(user.getPassword()));
		user.setJwt_token(token);

		LOGGER.info("User to be persisted : " + user);
		userService.createuser(user);

		LOGGER.info("Request verified : " + req);
		res.setEmail(req.getEmail());
		res.setName(req.getName());
//		res.setPassword(req.getPassword());
		res.setPhone(req.getPhone());
		res.setUsername(req.getUsername());
		res.setToken(user.getJwt_token());

		return new ResponseEntity<SignUpRes>(res, HttpStatus.OK);
	}

	@RequestMapping(path = APIConstants.LOGIN_USER_PASS, method = RequestMethod.POST)
	public ResponseEntity<SignInResp> signIn(@RequestBody SignInReq req) {

		LOGGER.info("signIn() : " + req);

		if (validateSignInReq(req) == false) {
			LOGGER.error("Incorrect Request : " + req);
			return new ResponseEntity<SignInResp>(HttpStatus.BAD_REQUEST);
		}

		UserDetails user = userService.getUserByEmail(req.getEmail());

		if (!encoder.matches(req.getPassword(), user.getPassword())) {
			LOGGER.info(encoder.encode(req.getPassword()));
			LOGGER.info(user.getPassword());
			LOGGER.info("" + encoder.matches(req.getPassword(), user.getPassword()));
			return new ResponseEntity<SignInResp>(HttpStatus.UNAUTHORIZED);
		}

		try {
			jwtutils.isExpired(user.getJwt_token());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			LOGGER.info("Generating Token...");
			String token = jwtutils.generateAccessToken(user);
			user.setJwt_token(token);
			userService.updateUser(user);
		}
		
		SignInResp resp = new SignInResp();
		resp.setEmail(user.getEmail());
		resp.setName(user.getName());
		resp.setPhone(user.getPhone());
		resp.setToken(user.getJwt_token());

		return new ResponseEntity<SignInResp>(resp, HttpStatus.OK);
	}

	@RequestMapping(path = APIConstants.LOGIN_OTP_GENERATE, method = RequestMethod.GET)
	public ResponseEntity<String> generateOTP(@RequestParam String email) {

		if (email==null || email.isBlank() || email.isEmpty()) {
			LOGGER.error("Incorrect Request: EMAIL NULL : " + email);
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}

		UserDetails user = userService.getUserByEmail(email);
		String otp = OTPHandler.generateOTP(Constants.LOGIN_OTP_LENGTH);
		LOGGER.info("Generated otp : " + otp);
		user.setOtp(otp);
		user.setOtpTime(new Timestamp(new Date().getTime()));
		userService.updateUser(user);

		return new ResponseEntity<String>(otp, HttpStatus.OK);
	}
	
	@RequestMapping(path = APIConstants.LOGIN_OTP_VERIFY, method = RequestMethod.POST)
	public ResponseEntity<SignInResp> signInOTP(@RequestBody SignInReq req) {

		LOGGER.info("signInOTP() : " + req);

		if (validateSignInReq(req) == false) {
			LOGGER.error("Incorrect Request : " + req);
			return new ResponseEntity<SignInResp>(HttpStatus.BAD_REQUEST);
		}

		UserDetails user = userService.getUserByEmail(req.getEmail());
		
		if(!user.getOtp().equals(req.getOtp())) {
			LOGGER.error("Incorrect OTP : " + req);
			return new ResponseEntity<SignInResp>(HttpStatus.UNAUTHORIZED);
		}

		Timestamp expected = new Timestamp(user.getOtpTime().getTime() + 2*60*1000);
		LOGGER.info(expected + " >>>> " + new Timestamp(new Date().getTime()));
		if(expected.before(new Timestamp(new Date().getTime()))) {
			LOGGER.error("EXPIRED OTP : " + req);
			return new ResponseEntity<SignInResp>(HttpStatus.UNAUTHORIZED);
		}
		
		if(!user.getOtp().equals(req.getOtp())) {
			LOGGER.error("Incorrect OTP : " + req);
			return new ResponseEntity<SignInResp>(HttpStatus.UNAUTHORIZED);
		}
		
		try {
			jwtutils.isExpired(user.getJwt_token());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			LOGGER.info("Generating Token...");
			String token = jwtutils.generateAccessToken(user);
			user.setJwt_token(token);
			userService.updateUser(user);
		}
		
		SignInResp resp = new SignInResp();
		resp.setEmail(user.getEmail());
		resp.setName(user.getName());
		resp.setPhone(user.getPhone());
		resp.setToken(user.getJwt_token());

		return new ResponseEntity<SignInResp>(resp, HttpStatus.OK);
	}

	private boolean validateSignupReq(SignUpReq req) {
		if (req == null || req.getEmail() == null || req.getPhone() == 0 || req.getPassword() == null
				|| req.getUsername() == null)
			return false;
		return true;
	}

	private boolean validateSignInReq(SignInReq req) {
		if (req == null || req.getMethod().isBlank() || req.getMethod().isEmpty())
			return false;

		if (req.getMethod().equals(Constants.LOGIN_PASS)
				&& (req.getEmail() == null || req.getEmail().isBlank() || req.getEmail().isEmpty()
				|| req.getPassword() == null || req.getPassword().isBlank() || req.getPassword().isEmpty()))
			return false;
		
		else if(req.getMethod() == Constants.LOGIN_OTP && (req.getEmail() == null || req.getEmail().isBlank() || req.getEmail().isEmpty() || req.getOtp().length()<Constants.LOGIN_OTP_LENGTH))
			return false;
		
		return true;
	}

	private UserDetails generateUserFromRequest(SignUpReq req) {
		return new UserDetails(req.getUsername(), req.getPassword(), req.getName(), req.getPhone(), req.getEmail(),
				null);
	}
}
