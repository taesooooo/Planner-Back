package com.planner.planner.Config.Properites;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "planner")
@Getter
@Setter
public class CommonProperties {
	private String baseLocation;
	private Jwt jwt;
	private OpenApi openApi;
	private SENS sens;
	private Front front;
	
}
