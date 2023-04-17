package com.planner.planner.Common;

import java.util.ArrayList;
import java.util.List;

public class Page<T> {
	private List<T> list = new ArrayList<T>();
	private PageInfo pageInfo;
	private int totalCount = 0;
	
	public static class Builder<T> {
		private List<T> list = new ArrayList<>();
		private PageInfo pageInfo;
		private int totalCount = 0;
		
		public Builder<T> setList(List<T> list) {
			this.list = list;
			return this;
		}
		public Builder<T> setPageInfo(PageInfo pageInfo) {
			this.pageInfo = pageInfo;
			return this;
		}
		public Builder<T> setTotalCount(int totalCount) {
			this.totalCount = totalCount;
			return this;
		}
		
		public Page<T> build() {
			return new Page<T>(this);
		}
	}

	public Page(Builder<T> builder) {
		this.list = builder.list;
		this.pageInfo = builder.pageInfo;
		this.totalCount = builder.totalCount;
	}

	public List<T> getList() {
		return list;
	}

	public int getPageIndex() {
		return pageInfo.getPageNum();
	}

	public int getPageLastIndex() {
		return (totalCount / 10) + 1 ;
	}

	public int getTotalCount() {
		return totalCount;
	}
}
