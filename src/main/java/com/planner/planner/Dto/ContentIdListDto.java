package com.planner.planner.Dto;

import java.util.List;

public class ContentIdListDto {
	
	private List<Integer> contentIds;

	public ContentIdListDto(List<Integer> contentIds) {
		this.contentIds = contentIds;
	}

	public List<Integer> getContentIds() {
		return contentIds;
	}

	@Override
	public String toString() {
		return "ContentIdListDto [contentIds=" + contentIds + "]";
	}
}
