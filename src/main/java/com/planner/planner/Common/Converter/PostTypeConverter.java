package com.planner.planner.Common.Converter;

import org.springframework.core.convert.converter.Converter;

import com.planner.planner.Common.PostType;

public class PostTypeConverter implements Converter<String, PostType> {

	@Override
	public PostType convert(String source) {
		return PostType.of(source);
	}
	
}
