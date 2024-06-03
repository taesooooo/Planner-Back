package com.planner.planner.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.planner.planner.Config.Properites.CommonProperties;
import com.planner.planner.Util.FileStore;

@Configuration
public class RootConfiguration {
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public FileStore fileStore(CommonProperties properties) {
		return new FileStore(properties.getBaseLocation());
	}
}
