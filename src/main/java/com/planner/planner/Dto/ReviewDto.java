package com.planner.planner.Dto;

public class ReviewDto {
	private int reviewId;
	private int plannerId;
	private String title;
	private int writer;
	private String content;
	private int likeCount;
	
	public static class Builder {
		private int reviewId;
		private int plannerId;
		private String title;
		private int writer;
		private String content;
		private int likeCount;
		
		public Builder setReviewId(int reviewId) {
			this.reviewId = reviewId;
			return this;
		}
		public Builder setPlannerId(int plannerId) {
			this.plannerId = plannerId;
			return this;
		}
		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}
		public Builder setWriter(int writer) {
			this.writer = writer;
			return this;
		}
		public Builder setContent(String content) {
			this.content = content;
			return this;
		}
		public Builder setLikeCount(int likeCount) {
			this.likeCount = likeCount;
			return this;
		}
		public ReviewDto build() {
			return new ReviewDto(this);
		}
	}
	
	public ReviewDto(Builder builder) {
		this.reviewId = builder.reviewId;
		this.plannerId = builder.plannerId;
		this.title = builder.title;
		this.writer = builder.writer;
		this.content = builder.content;
		this.likeCount = builder.likeCount;
	}

	public int getReviewId() {
		return reviewId;
	}

	public int getPlannerId() {
		return plannerId;
	}

	public String getTitle() {
		return title;
	}

	public int getWriter() {
		return writer;
	}

	public String getContent() {
		return content;
	}

	public int getLikeCount() {
		return likeCount;
	}
}
