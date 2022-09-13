package com.wibmo.bootcamp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@RequestMapping(path = "/testserver", method = RequestMethod.GET)
	String testMethod() {
		System.out.println("[TestController] : testMethod()");
		return "\"resp\" : \"Hello World\"";
	}
}
