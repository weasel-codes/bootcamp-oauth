package com.wibmo.bootcamp.model.req;

public class SignInReq {

	private String method;
	private String otp;
	private String email;
	private String password;

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public SignInReq(String method, String otp, String email, String password) {
		super();
		this.method = method;
		this.otp = otp;
		this.email = email;
		this.password = password;
	}

	@Override
	public String toString() {
		return "SignInReq [method=" + method + ", otp=" + otp + ", email=" + email + ", password=" + password + "]";
	}


}
