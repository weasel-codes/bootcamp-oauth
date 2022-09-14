package com.wibmo.bootcamp.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "USER_DETAILS")
public class UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int sid;

	private String username;
	private String password;
	private String name;

	private long phone;
	private String email;	
	private String jwt_token;

	public UserDetails(int sid, String username, String password, String name, long phone, String email, String token) {
		super();
		this.sid = sid;
		this.username = username;
		this.password = password;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.jwt_token = token;
	}

	public UserDetails(String username, String password, String name, long phone, String email, String token) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.jwt_token = token;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserDetails() {
		super();
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public String getJwt_token() {
		return jwt_token;
	}

	public void setJwt_token(String jwt_token) {
		this.jwt_token = jwt_token;
	}

	@Override
	public String toString() {
		return "UserDetails [sid=" + sid + ", username=" + username + ", password=" + password + ", name=" + name
				+ ", phone=" + phone + ", email=" + email + ", jwt_token=" + jwt_token + "]";
	}
}
