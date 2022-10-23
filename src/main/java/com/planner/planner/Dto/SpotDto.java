package com.planner.planner.Dto;

import com.planner.planner.Object.SpotItem;

public class SpotDto {
	private String item;
	private int likeCount;

	public static class Builder {
		private String item;
		private int likeCount;
		
		public Builder setItem(String item) {
			this.item = item;
			return this;
		}
		
		public Builder setlikeCount(int count) {
			this.likeCount = count;
			return this;
		}
		
		public SpotDto build() {
			return new SpotDto(this);
		}
	}

	public SpotDto() {

	}

	public SpotDto(Builder builder) {
		this.item = builder.item;
		this.likeCount = builder.likeCount;
	}

	public String getItem() {
		return item;
	}

	public int getLikeCount() {
		return likeCount;
	}
}
