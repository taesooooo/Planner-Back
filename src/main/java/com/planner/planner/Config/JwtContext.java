package com.planner.planner.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.planner.planner.util.JwtUtil;

@Configuration
@PropertySource("classpath:config/config.properties")
public class JwtContext {
	
	@Value("${jwt.secret}")
	private String secretKey;
	
	@Bean
	public JwtUtil jwtUtil() {
		return new JwtUtil(secretKey);
	}

}
