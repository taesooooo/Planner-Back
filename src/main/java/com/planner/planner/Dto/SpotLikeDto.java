package com.planner.planner.Dto;

import java.time.LocalDate;

public class SpotLikeDto {
	private int likeId;
	private int accountId;
	private int contentId;
	private Integer areaCode;
	private String title;
	private String image;
	private LocalDate likeDate;

	public static class Builder {
		private int likeId;
		private int accountId;
		private int contentId;
		private Integer areaCode;
		private String title;
		private String image;
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
		public Builder setAreaCode(Integer areaCode) {
			this.areaCode = areaCode;
			return this;
		}
		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}
		public Builder setImage(String image) {
			this.image = image;
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
		this.areaCode = builder.areaCode;
		this.title = builder.title;
		this.image = builder.image;
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

	public Integer getAreaCode() {
		return areaCode;
	}

	public String getTitle() {
		return title;
	}

	public String getImage() {
		return image;
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
