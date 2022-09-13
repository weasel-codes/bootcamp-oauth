package com.wibmo.bootcamp.service;

import com.wibmo.bootcamp.model.entity.UserDetails;

public interface UserDetailsService {
	public UserDetails getUserById(int id);

	public UserDetails getUserByUsername(String username);

	public UserDetails getUserByEmail(String email);

	public UserDetails getUserByPhone(long phone);

	public void createuser(UserDetails user);

	public void updateUser(UserDetails user);
}
