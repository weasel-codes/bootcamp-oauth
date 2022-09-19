package com.wibmo.bootcamp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wibmo.bootcamp.model.entity.LoginMethods;

@Repository
public interface LoginMethodDao extends JpaRepository<LoginMethods, Integer> {
}
