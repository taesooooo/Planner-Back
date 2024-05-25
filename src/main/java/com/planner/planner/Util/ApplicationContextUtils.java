package com.planner.planner.Util;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ApplicationContextUtils {

	private final ApplicationContext context;
	
	public Object getBean(String beanName) {
		return context.getBean(beanName);
	}
	
	public <T> Object getBean(T clazz) {
		return context.getBean(clazz.getClass());
	}
}
