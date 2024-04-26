package com.planner.planner.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.planner.planner.Common.Converter.PostTypeConverter;
import com.planner.planner.Common.Converter.SortCriteriaConverter;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new PostTypeConverter());
		registry.addConverter(new SortCriteriaConverter());
	}

}
