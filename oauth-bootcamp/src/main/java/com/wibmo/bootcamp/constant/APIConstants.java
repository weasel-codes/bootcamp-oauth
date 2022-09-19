package com.wibmo.bootcamp.constant;

public class APIConstants {

	public static final String AUTH_HEADER_PARAMETER_AUTHERIZATION = "auth";

	public static final String SIGNUP = "/auth/signup";
	public static final String LOGIN_METHODS = "/auth/login/methods";
	public static final String LOGIN_USER_PASS = "/auth/login/pass";
	public static final String LOGIN_OTP_GENERATE= "/auth/login/otp/generate";
	public static final String LOGIN_OTP_VERIFY = "/auth/login/otp/verify";
	
	public static final String USER_DETAILS_FETCH = "/user/details";
	
	public static final String TEST_AUTH = "/auth/testserver";
	public static final String USER_AUTH = "/user/testserver";
}
