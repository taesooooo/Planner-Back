package com.planner.planner.Dto;

import java.util.List;

public class ReviewListDto {
	private List<ReviewDto> reviewList;

	public static class Builder {
		private List<ReviewDto> reviewList;
		
		public Builder setReviewList(List<ReviewDto> reviewList) {
			this.reviewList = reviewList;
			return this;
		}

		public ReviewListDto build() {
			return new ReviewListDto(this);
		}
	}
	
	public ReviewListDto(Builder builder) {
		this.reviewList = builder.reviewList;
	}

	public List<ReviewDto> getReviewList() {
		return reviewList;
	}
}
