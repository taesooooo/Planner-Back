package com.planner.planner.Dto;

public class SpotLikeCountDto {
	private int contentId;
	private int count;
	
	public static class Builder {
		private int contentId;
		private int count;
		
		public Builder setContentId(int contentId) {
			this.contentId = contentId;
			return this;
		}
		public Builder setCount(int count) {
			this.count = count;
			return this;
		}
		
		public SpotLikeCountDto build() {
			return new SpotLikeCountDto(this);
		}
	}
	
	public SpotLikeCountDto(Builder builder) {
		this.contentId = builder.contentId;
		this.count = builder.count;
	}

	public int getContentId() {
		return contentId;
	}

	public int getCount() {
		return count;
	}
}
