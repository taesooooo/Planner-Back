package com.planner.planner.Dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.planner.planner.Common.ValidationGroups.PlannerCreateGroup;
import com.planner.planner.Common.ValidationGroups.PlannerUpdateGroup;
import com.planner.planner.Common.ValidationGroups.ReviewCreateGroup;
import com.planner.planner.Common.ValidationGroups.ReviewUpdateGroup;

public class ReviewDto {
	private int reviewId;
	private Integer plannerId;
	
	@NotBlank(message = "제목은 필수 항목입니다.", groups = {ReviewCreateGroup.class, ReviewUpdateGroup.class})
	private String title;
	
	private String writer;
	private int writerId;
	
	@NotBlank(message = "내용을 적어주세요.", groups = {ReviewCreateGroup.class, ReviewUpdateGroup.class})
	private String content;
	@PositiveOrZero(message = "지역 코드는 음수일 수 없습니다.", groups = {ReviewCreateGroup.class, ReviewUpdateGroup.class})
	private Integer areaCode;
	private String thumbnail;
	private int likeCount;
	
	@JsonFormat(pattern = "YYYY-MM-dd hh:mm:ss")
	private LocalDateTime createDate;
	@JsonFormat(pattern = "YYYY-MM-dd hh:mm:ss")
	private LocalDateTime updateDate;
	
	private List<String> fileNames;
	private List<CommentDto> comments;
	
	public static class Builder {
		private int reviewId;
		private Integer plannerId;
		private String title;
		private String writer;
		private int writerId;
		private String content;
		private Integer areaCode;
		private String thumbnail;
		private int likeCount;
		private LocalDateTime createDate;
		private LocalDateTime updateDate;
		private List<String> fileNames;
		private List<CommentDto> comments;
		
		public Builder setReviewId(int reviewId) {
			this.reviewId = reviewId;
			return this;
		}
		public Builder setPlannerId(Integer plannerId) {
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
		public Builder setAreaCode(Integer areaCode) {
			this.areaCode = areaCode;
			return this;
		}
		public Builder setThumbnail(String thumbnail) {
			this.thumbnail = thumbnail;
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
		public Builder setComments(List<CommentDto> comments) {
			this.comments = comments;
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
		this.areaCode = builder.areaCode;
		this.thumbnail = builder.thumbnail;
		this.likeCount = builder.likeCount;
		this.createDate = builder.createDate;
		this.updateDate = builder.updateDate;
		this.fileNames = builder.fileNames;
		this.comments = builder.comments;
	}

	public int getReviewId() {
		return reviewId;
	}

	public Integer getPlannerId() {
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

	public Integer getAreaCode() {
		return areaCode;
	}

	public String getThumbnail() {
		return thumbnail;
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

	public List<CommentDto> getComments() {
		return comments;
	}
}
