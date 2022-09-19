package com.wibmo.bootcamp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wibmo.bootcamp.model.entity.UserDetails;

@Repository
public interface UserDetailsDao extends JpaRepository<UserDetails, Integer> {
	UserDetails findByUsername(String username);

	UserDetails findByEmail(String email);

	UserDetails findByPhone(long phone);
}
