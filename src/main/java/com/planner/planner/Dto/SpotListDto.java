package com.planner.planner.Dto;

import java.util.List;

public class SpotListDto<T> {
	private List<T> items;
	private int totalCount;
	
	public SpotListDto(List<T> items, int totalCount) {
		this.items = items;
		this.totalCount = totalCount;
	}
	public List<T> getItems() {
		return items;
	}
	public int getTotalCount() {
		return totalCount;
	}
}
