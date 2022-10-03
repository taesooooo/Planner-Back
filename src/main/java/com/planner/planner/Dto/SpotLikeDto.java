package com.planner.planner.Dto;

import java.time.LocalDate;

public class SpotLikeDto {
	private int likeId;
	private int accountId;
	private int contentId;
	private LocalDate likeDate;
	
	public static class Builder {
		private int likeId;
		private int accountId;
		private int contentId;
		private LocalDate likeDate;
		
		public Builder setLikeId(int likeId) {
			this.likeId = likeId;
			return this;
		}
		public Builder setAccountId(int accountId) {
			this.accountId = accountId;
			return this;
		}
		public Builder setContentId(int contentId) {
			this.contentId = contentId;
			return this;
		}
		public Builder setLikeDate(LocalDate likeDate) {
			this.likeDate = likeDate;
			return this;
		}
		public SpotLikeDto build() {
			return new SpotLikeDto(this);
		}
	}
	
	public SpotLikeDto() {
		
	}

	public SpotLikeDto(Builder builder) {
		this.likeId = builder.likeId;
		this.accountId = builder.accountId;
		this.contentId = builder.contentId;
		this.likeDate = builder.likeDate;
	}
	
	public int getLikeId() {
		return likeId;
	}

	public int getAccountId() {
		return accountId;
	}

	public int getContentId() {
		return contentId;
	}

	public LocalDate getLikeDate() {
		return likeDate;
	}

	@Override
	public String toString() {
		return "SpotLikeDto [likeId=" + likeId + ", accountId=" + accountId + ", contentId=" + contentId + ", likeDate="
				+ likeDate + "]";
	}

}
