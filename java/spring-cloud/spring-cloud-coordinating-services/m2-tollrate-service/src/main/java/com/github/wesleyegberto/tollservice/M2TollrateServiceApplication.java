package com.github.wesleyegberto.tollservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class M2TollrateServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(M2TollrateServiceApplication.class, args);
	}

}

