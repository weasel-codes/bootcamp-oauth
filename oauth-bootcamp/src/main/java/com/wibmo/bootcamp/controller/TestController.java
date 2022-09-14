package com.wibmo.bootcamp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@RequestMapping(path = "/auth/testserver", method = RequestMethod.GET)
	String testAuth() {
		System.out.println("[TestController] : testMethod()");
		return "\"resp\" : \"Hello World\"";
	}

	@RequestMapping(path = "/user/testserver", method = RequestMethod.GET)
	String testUser() {
		System.out.println("[TestController] : testUser()");
		return "\"resp\" : \"Hello World\"";
	}

}
