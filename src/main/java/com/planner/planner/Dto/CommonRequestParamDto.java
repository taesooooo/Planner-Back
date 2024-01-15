package com.planner.planner.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.planner.planner.Common.PostType;
import com.planner.planner.Common.SortCriteria;

public class CommonRequestParamDto {
	
	private Integer itemCount = 10;
	@JsonProperty("criteria")
	private SortCriteria sortCriteria = SortCriteria.LATEST;
	private String keyword = "";
	private Integer areaCode;
	private Integer pageNum;
	private PostType postType;
	
	public static class Builder {
		private Integer itemCount = 10;
		private SortCriteria sortCriteria;
		private String keyword;
		private Integer areaCode;
		private Integer pageNum;
		private PostType postType;

		public Builder setItemCount(Integer itemCount) {
			this.itemCount = itemCount;
			return this;
		}

		public Builder setSortCriteria(SortCriteria sortCriteria) {
			this.sortCriteria = sortCriteria;
			return this;
		}

		public Builder setKeyword(String keyword) {
			this.keyword = keyword;
			return this;
		}

		public Builder setAreaCode(Integer areaCode) {
			this.areaCode = areaCode;
			return this;
		}

		public Builder setPageNum(Integer pageNum) {
			this.pageNum = pageNum;
			return this;
		}
		
		public Builder setPostType(PostType postType) {
			this.postType = postType;
			return this;
		}

		public CommonRequestParamDto build() {
			return new CommonRequestParamDto(this);
		}
	}
	
	public CommonRequestParamDto() {
		
	}

	public CommonRequestParamDto(Builder builder) {
		this.itemCount = builder.itemCount;
		this.sortCriteria = builder.sortCriteria;
		this.keyword = builder.keyword;
		this.areaCode = builder.areaCode;
		this.pageNum = builder.pageNum;
		this.postType = builder.postType;
	}

	public Integer getItemCount() {
		return itemCount;
	}

	public SortCriteria getSortCriteria() {
		return sortCriteria;
	}

	public String getKeyword() {
		return keyword;
	}

	public Integer getAreaCode() {
		return areaCode;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public PostType getPostType() {
		return postType;
	}
	
}
