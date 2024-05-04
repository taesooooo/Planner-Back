package com.planner.planner.Dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SpotListDto<T> {
	private List<T> items;
	private int totalCount;
}
