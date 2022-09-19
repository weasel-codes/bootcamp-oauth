package com.wibmo.bootcamp.model.resp;

import java.util.List;

import com.wibmo.bootcamp.model.entity.LoginMethods;

public class LoginMethodRes {

	private List<LoginMethods> methods;

	public LoginMethodRes(List<LoginMethods> methods) {
		super();
		this.methods = methods;
	}

	public List<LoginMethods> getMethods() {
		return methods;
	}

	public void setMethods(List<LoginMethods> methods) {
		this.methods = methods;
	}
}
