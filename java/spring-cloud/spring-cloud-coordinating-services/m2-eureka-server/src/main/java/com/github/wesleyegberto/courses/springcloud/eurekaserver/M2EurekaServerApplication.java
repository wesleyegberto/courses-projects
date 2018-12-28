package com.github.wesleyegberto.courses.springcloud.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class M2EurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(M2EurekaServerApplication.class, args);
	}

}

