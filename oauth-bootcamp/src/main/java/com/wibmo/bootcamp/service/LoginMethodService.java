package com.wibmo.bootcamp.service;

import java.util.List;

import com.wibmo.bootcamp.model.entity.LoginMethods;

public interface LoginMethodService {
	public List<LoginMethods> fetchAllLoginMethods();
}
