package com.planner.planner.Dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CommentDto {
	private int commentId;
	
	@Min(value = 1, message = "잘못된 리뷰 정보입니다.")
	private int reviewId;
	
	@Min(value = 1, message = "잘못된 사용자 정보입니다.")
	private int writerId;
	
	private String writer;
	
	@NotBlank(message = "댓글 내용을 적어주세요.")
	private String content;
	
	@Min(value = 1, message = "잘못된 댓글 정보입니다.")
	private Integer parentId;
	
	@JsonFormat(pattern = "YYYY-MM-dd hh:mm:ss")
	private LocalDateTime createDate;
	@JsonFormat(pattern = "YYYY-MM-dd hh:mm:ss")
	private LocalDateTime updateDate;
	
	private List<CommentDto> reComments = new ArrayList<CommentDto>();
	
	public static class Builder {
		private int commentId;
		private int reviewId;
		private int writerId;
		private String writer;
		private String content;
		private Integer parentId;
		private LocalDateTime createDate;
		private LocalDateTime updateDate;
		private List<CommentDto> reComments = new ArrayList<CommentDto>();
		
		public Builder setCommentId(int commentId) {
			this.commentId = commentId;
			return this;
		}
		public Builder setReviewId(int reviewId) {
			this.reviewId = reviewId;
			return this;
		}
		public Builder setWriterId(int writerId) {
			this.writerId = writerId;
			return this;
		}
		public Builder setWriter(String writer) {
			this.writer = writer;
			return this;
		}
		public Builder setContent(String content) {
			this.content = content;
			return this;
		}
		public Builder setParentId(Integer parentId) {
			this.parentId = parentId;
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
		
		public Builder setReComments(List<CommentDto> reComments) {
			this.reComments = reComments;
			return this;
		}
		public CommentDto build() {
			return new CommentDto(this);
		}
	}
	
	public CommentDto() {
		
	}

	public CommentDto(Builder builder) {
		this.commentId = builder.commentId;
		this.reviewId = builder.reviewId;
		this.writerId = builder.writerId;
		this.writer = builder.writer;
		this.content = builder.content;
		this.parentId = builder.parentId;
		this.createDate = builder.createDate;
		this.updateDate = builder.updateDate;
		this.reComments = builder.reComments;
	}

	public int getCommentId() {
		return commentId;
	}

	public int getReviewId() {
		return reviewId;
	}

	public int getWriterId() {
		return writerId;
	}

	public String getWriter() {
		return writer;
	}

	public String getContent() {
		return content;
	}

	public Integer getParentId() {
		return parentId;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public List<CommentDto> getReComments() {
		return reComments;
	}
}
