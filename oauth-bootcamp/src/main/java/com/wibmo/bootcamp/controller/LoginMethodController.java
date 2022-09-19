package com.wibmo.bootcamp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wibmo.bootcamp.constant.APIConstants;
import com.wibmo.bootcamp.model.entity.LoginMethods;
import com.wibmo.bootcamp.model.resp.LoginMethodRes;
import com.wibmo.bootcamp.service.LoginMethodServiceImpl;

@RestController
public class LoginMethodController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginMethodController.class);

	@Autowired private LoginMethodServiceImpl loginService;

	@RequestMapping(path = APIConstants.LOGIN_METHODS, method = RequestMethod.GET)
	public ResponseEntity<LoginMethodRes> loginOptions() {
		LOGGER.info("Fetching login options");

		List<LoginMethods> list = this.loginService.fetchAllLoginMethods();
		if (list == null || list.size() == 0)
			return new ResponseEntity<LoginMethodRes>(HttpStatus.OK);

		return new ResponseEntity<LoginMethodRes>(new LoginMethodRes(list), HttpStatus.OK);
	}
}
