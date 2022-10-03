package com.planner.planner.Entity;

import java.time.LocalDate;

import com.planner.planner.Dto.SpotLikeDto;

public class SpotLike {
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
		public SpotLike build() {
			return new SpotLike(this);
		}
	}
	
	public SpotLike() {
		
	}

	public SpotLike(Builder builder) {
		this.likeId = builder.likeId;
		this.accountId = builder.accountId;
		this.contentId = builder.contentId;
		this.likeDate = builder.likeDate;
	}

	public int getLikeId() {
		return likeId;
	}

	public void setLikeId(int likeId) {
		this.likeId = likeId;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public int getContentId() {
		return contentId;
	}

	public void setContentId(int contentId) {
		this.contentId = contentId;
	}

	public LocalDate getLikeDate() {
		return likeDate;
	}

	public void setLikeDate(LocalDate likeDate) {
		this.likeDate = likeDate;
	}
	
	public SpotLikeDto toDto() {
		return new SpotLikeDto.Builder().setLikeId(likeId).setAccountId(accountId).setContentId(contentId).setLikeDate(likeDate).build();
	}

	@Override
	public String toString() {
		return "SpotLike [likeId=" + likeId + ", accountId=" + accountId + ", contentId=" + contentId + ", likeDate="
				+ likeDate + "]";
	}
}
