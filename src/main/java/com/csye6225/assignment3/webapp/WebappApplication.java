package com.csye6225.assignment3.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.csye6225.assignment3.pojo")
@EnableJpaRepositories(basePackages = "com.csye6225.assignment3.repositories")
@ComponentScan(basePackages = {"com.csye6225.assignment3.security","com.csye6225.assignment3.controllers","com.csye6225.assignment3.util","com.csye6225.assignment3.services","com.csye6225.assignment3.dependencies"})
public class WebappApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebappApplication.class, args);
	}

}
