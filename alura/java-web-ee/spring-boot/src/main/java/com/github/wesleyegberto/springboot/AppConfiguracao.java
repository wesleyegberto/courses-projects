package com.github.wesleyegberto.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppConfiguracao {
	
	public static void main(String[] args) {
		SpringApplication.run(AppConfiguracao.class, args);
	}
	
	/* 
	Também podemos usar um arquivo application.properties no classpath
	@Bean // Será injetado no Spring-Data-JPA
	public DataSource dataSource(){
	    DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName("com.mysql.jdbc.Driver");
	    dataSource.setUrl("jdbc:mysql://localhost:3306/listavip");
	    dataSource.setUsername("root");
	    dataSource.setPassword("1234abc@");
	    return dataSource;
	}
	*/
}
