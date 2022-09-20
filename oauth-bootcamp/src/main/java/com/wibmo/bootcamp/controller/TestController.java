package com.wibmo.bootcamp.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wibmo.bootcamp.constant.APIConstants;

@RestController
//@CrossOrigin(origins = "http://localhost:8090")
public class TestController {

	@RequestMapping(path = APIConstants.TEST_AUTH, method = RequestMethod.GET)
	String testAuth() {
		System.out.println("[TestController] : testMethod()");
		return "\"resp\" : \"Hello World\"";
	}

	@RequestMapping(path = APIConstants.USER_AUTH, method = RequestMethod.GET)
	String testUser() {
		System.out.println("[TestController] : testUser()");
		return "\"resp\" : \"Hello World\"";
	}
}
