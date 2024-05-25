package com.planner.planner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableWebSecurity
@EnableTransactionManagement(order = 0)
public class PlannerApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(PlannerApplication.class, args);
	}


}
