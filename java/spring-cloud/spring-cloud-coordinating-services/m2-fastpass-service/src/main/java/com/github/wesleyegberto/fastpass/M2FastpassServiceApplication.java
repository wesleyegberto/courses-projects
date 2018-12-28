package com.github.wesleyegberto.fastpass;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class M2FastpassServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(M2FastpassServiceApplication.class, args);
	}

}

