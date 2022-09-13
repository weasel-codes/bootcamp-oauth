package com.wibmo.bootcamp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.wibmo.bootcamp.dao.UserDetailsDao;
import com.wibmo.bootcamp.model.entity.UserDetails;

public class UserDetailsServiceImplementation implements UserDetailsService {

	@Autowired
	UserDetailsDao userDao;

	@Override
	public UserDetails getUserById(int id) {
		Optional<UserDetails> myUser = userDao.findById(id);
		if (myUser.isPresent()) {
			return myUser.get();
		} else {
			throw new RuntimeException("User Not found");
		}
	}

	@Override
	public UserDetails getUserByUsername(String username) {
		UserDetails myUser = userDao.findByUsername(username);
		if (myUser != null) {
			return myUser;
		} else {
			throw new RuntimeException("User Not found");
		}
	}

	@Override
	public void createuser(UserDetails user) {
		userDao.save(user);

	}

	@Override
	public void updateUser(UserDetails user) {
		Optional<UserDetails> myUser = userDao.findById(user.getSid());
		if (myUser.isPresent()) {
			userDao.save(user);
		} else {
			throw new RuntimeException("User Not found");
		}

	}

	@Override
	public UserDetails getUserByEmail(String email) {
		UserDetails myUser = userDao.findByEmail(email);
		if (myUser != null) {
			return myUser;
		} else {
			throw new RuntimeException("User Not found");
		}
	}

	@Override
	public UserDetails getUserByPhone(long phone) {
		UserDetails myUser = userDao.findByPhone(phone);
		if (myUser != null) {
			return myUser;
		} else {
			throw new RuntimeException("User Not found");
		}
	}

}
