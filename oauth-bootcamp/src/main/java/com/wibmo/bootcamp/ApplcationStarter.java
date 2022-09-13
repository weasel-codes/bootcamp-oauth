package com.wibmo.bootcamp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApplcationStarter {

	public static void main(String[] args) {
		System.out.println("################## START UP #################");
		SpringApplication.run(ApplcationStarter.class, args);
	}

}
