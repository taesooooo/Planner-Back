package com.planner.planner.Entity;

import java.time.LocalDateTime;

import com.planner.planner.Dto.ReviewDto;

public class Review {
	private int reviewId;
	private int plannerId;
	private String title;
	private int writer;
	private String content;
	private int likeCount;
	private LocalDateTime createTime;
	private LocalDateTime updateTime;
	
	public static class Builder {
		private int reviewId;
		private int plannerId;
		private String title;
		private int writer;
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
		public Builder setCreateTime(LocalDateTime createTime) {
			this.createTime = createTime;
			return this;
		}
		public Builder setUpdateTime(LocalDateTime updateTime) {
			this.updateTime = updateTime;
			return this;
		}
		public Review build() {
			return new Review(this);
		}
	}
	
	public Review(Builder builder) {
		this.reviewId = builder.reviewId;
		this.plannerId = builder.plannerId;
		this.title = builder.title;
		this.writer = builder.writer;
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

	public int getWriter() {
		return writer;
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
