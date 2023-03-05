package com.planner.planner.Dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PlanMemoDto {
	private int memoId;
	private String title;
	private String content;
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private LocalDateTime createDate;
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private LocalDateTime updateDate;
	
	public static class Builder {
		private int memoId;
		private String title;
		private String content;
		private LocalDateTime createDate;
		private LocalDateTime updateDate;
		
		public Builder setMemoId(int memoId) {
			this.memoId = memoId;
			return this;
		}
		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}
		public Builder setContent(String content) {
			this.content = content;
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
		public PlanMemoDto build() {
			return new PlanMemoDto(this);
		}
	}
	
	public PlanMemoDto() {
		
	}

	public PlanMemoDto(Builder builder) {
		this.memoId = builder.memoId;
		this.title = builder.title;
		this.content = builder.content;
		this.createDate = builder.createDate;
		this.updateDate = builder.updateDate;
	}

	public int getMemoId() {
		return memoId;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	@Override
	public String toString() {
		return "PlanMemoDto [memoId=" + memoId + ", title=" + title + ", content=" + content + ", createDate="
				+ createDate + ", updateDate=" + updateDate + "]";
	}
}
