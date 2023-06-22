package com.planner.planner.Common.Converter;

import org.springframework.core.convert.converter.Converter;

import com.planner.planner.Common.SortCriteria;

public class SortCriteriaConverter implements Converter<String, SortCriteria>{

	@Override
	public SortCriteria convert(String source) {
		return SortCriteria.of(source);
	}
	
}
