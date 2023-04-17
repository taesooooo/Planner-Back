package com.planner.planner.Common;

public class PageInfo {
	private int pageNum;
	private int pageItemCount;
	
	public static class Builder {
		private int pageNum;
		private int pageItemCount;
		
		public Builder setPageNum(int pageNum) {
			this.pageNum = pageNum;
			return this;
		}
		public Builder setPageItemCount(int pageItemCount) {
			this.pageItemCount = pageItemCount;
			return this;
		}
		
		public PageInfo build() {
			return new PageInfo(this);
		}
	}

	public PageInfo(Builder builder) {
		this.pageNum = builder.pageNum;
		this.pageItemCount = builder.pageItemCount;
	}

	public int getPageNum() {
		return pageNum;
	}

	public int getPageItemCount() {
		return pageItemCount;
	}
	
	public int getPageOffSet() {
		return  (pageNum * pageItemCount) - pageItemCount;
	}
}
