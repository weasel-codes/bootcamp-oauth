package com.wibmo.bootcamp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wibmo.bootcamp.model.entity.UserDetails;

public interface UserDetailsDao extends JpaRepository<UserDetails, Integer> {
	UserDetails findByUsername(String username);

	UserDetails findByEmail(String email);

	UserDetails findByPhone(long phone);
}
