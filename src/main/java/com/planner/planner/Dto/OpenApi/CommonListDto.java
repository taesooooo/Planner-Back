package com.planner.planner.Dto.OpenApi;

import java.util.List;

public class CommonListDto<T> {
	private List<T> items; // 아이템 리스트;
	private int numOfRows; // 페이지 결과 개수
	private int pageNo; // 페이지 번호
	private int totalCount; // 총 개수
	
	public CommonListDto(List<T> items, int numOfRows, int pageNo, int totalCount) {
		this.items = items;
		this.numOfRows = numOfRows;
		this.pageNo = pageNo;
		this.totalCount = totalCount;
	}
	public List<T> getItems() {
		return items;
	}
	public int getNumOfRows() {
		return numOfRows;
	}
	public int getPageNo() {
		return pageNo;
	}
	public int getTotalCount() {
		return totalCount;
	}
}
