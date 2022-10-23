package com.planner.planner.Entity;

import com.planner.planner.Dto.SpotLikeCountDto;

public class SpotLikeCount {
	private int conetntId;
	private int likeCount;
	
	public SpotLikeCount(Builder builder) {
		this.conetntId = builder.conetntId;
		this.likeCount = builder.likeCount;
	}

	public int getConetntId() {
		return conetntId;
	}

	public int getLikeCount() {
		return likeCount;
	}
	
	public SpotLikeCountDto toDto() {
		return new SpotLikeCountDto.Builder().setConetntId(conetntId).setLikeCount(likeCount).build();
	}

	public static class Builder {
		private int conetntId;
		private int likeCount;
		
		public Builder setConetntId(int conetntId) {
			this.conetntId = conetntId;
			return this;
		}
		public Builder setLikeCount(int likeCount) {
			this.likeCount = likeCount;
			return this;
		}
		public SpotLikeCount build() {
			return new SpotLikeCount(this);
		}
	}
}
