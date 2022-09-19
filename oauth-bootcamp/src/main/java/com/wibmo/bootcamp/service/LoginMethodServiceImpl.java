package com.wibmo.bootcamp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.bootcamp.dao.LoginMethodDao;
import com.wibmo.bootcamp.model.entity.LoginMethods;

@Service
public class LoginMethodServiceImpl implements LoginMethodService {

	@Autowired LoginMethodDao dao;
	
	@Override
	public List<LoginMethods> fetchAllLoginMethods() {
		return this.dao.findAll();
	}

}
