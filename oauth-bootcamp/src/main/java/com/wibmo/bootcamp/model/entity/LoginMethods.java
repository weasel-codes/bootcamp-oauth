package com.wibmo.bootcamp.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "LOGIN_TYPES")
public class LoginMethods {

	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	private int sid;

	private String name;

	private String description;

	public LoginMethods() {
		super();
	}

	public LoginMethods(int sid, String name, String description) {
		super();
		this.sid = sid;
		this.name = name;
		this.description = description;
	}

	public int getSid() {
		return sid;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
}
