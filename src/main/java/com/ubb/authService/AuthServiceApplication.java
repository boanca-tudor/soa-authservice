package com.ubb.authService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.ubb.userModule.user.repo"})
@EntityScan(basePackages = {"com.ubb.userModule.user.entity"})
@ComponentScan(basePackages = {"com.ubb.userModule", "com.ubb.authService"})
public class AuthServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}
}
