package com.planner.planner.Dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class ReviewDto {
	private int reviewId;
	private int plannerId;
	private String title;
	private String writer;
	private int writerId;
	private String content;
	private int likeCount;
	private LocalDateTime createDate;
	private LocalDateTime updateDate;
	
	private List<String> fileNames;
	
	public static class Builder {
		private int reviewId;
		private int plannerId;
		private String title;
		private String writer;
		private int writerId;
		private String content;
		private int likeCount;
		private LocalDateTime createDate;
		private LocalDateTime updateDate;
		private List<String> fileNames;
		
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
		public Builder setCreateDate(LocalDateTime createDate) {
			this.createDate = createDate;
			return this;
		}
		public Builder setUpdateDate(LocalDateTime updateDate) {
			this.updateDate = updateDate;
			return this;
		}
		public Builder setFileNames(List<String> fileNames) {
			this.fileNames = fileNames;
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
		this.createDate = builder.createDate;
		this.updateDate = builder.updateDate;
		this.fileNames = builder.fileNames;
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

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public List<String> getFileNames() {
		return fileNames;
	}


}
