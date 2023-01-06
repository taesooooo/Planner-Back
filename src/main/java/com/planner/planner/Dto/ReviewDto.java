package com.planner.planner.Dto;

import java.time.LocalDateTime;

public class ReviewDto {
	private int reviewId;
	private int plannerId;
	private String title;
	private String writer;
	private int writerId;
	private String content;
	private int likeCount;
	private LocalDateTime createTime;
	private LocalDateTime updateTime;
	
	public static class Builder {
		private int reviewId;
		private int plannerId;
		private String title;
		private String writer;
		private int writerId;
		private String content;
		private int likeCount;
		private LocalDateTime createTime;
		private LocalDateTime updateTime;
		
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
		public Builder setWriter(String writer) {
			this.writer = writer;
			return this;
		}
		public Builder setWriterId(int writerId) {
			this.writerId = writerId;
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
		public Builder setCreateTime(LocalDateTime createTime) {
			this.createTime = createTime;
			return this;
		}
		public Builder setUpdateTime(LocalDateTime updateTime) {
			this.updateTime = updateTime;
			return this;
		}
		public ReviewDto build() {
			return new ReviewDto(this);
		}
	}
	
	public ReviewDto() {
		
	}
	
	public ReviewDto(Builder builder) {
		this.reviewId = builder.reviewId;
		this.plannerId = builder.plannerId;
		this.title = builder.title;
		this.writer = builder.writer;
		this.writerId = builder.writerId;
		this.content = builder.content;
		this.likeCount = builder.likeCount;
		this.createTime = builder.createTime;
		this.updateTime = builder.updateTime;
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

	public String getWriter() {
		return writer;
	}
	
	public int getWriterId() {
		return writerId;
	}

	public String getContent() {
		return content;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}
}
