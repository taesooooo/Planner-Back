package com.planner.planner.Dto;

import com.planner.planner.Entity.SpotLikeCount;
import com.planner.planner.Entity.SpotLikeCount.Builder;

public class SpotLikeCountDto {
	private int conetntId;
	private int likeCount;
	
	public SpotLikeCountDto(Builder builder) {
		this.conetntId = builder.conetntId;
		this.likeCount = builder.likeCount;
	}

	public int getConetntId() {
		return conetntId;
	}

	public int getLikeCount() {
		return likeCount;
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
		public SpotLikeCountDto build() {
			return new SpotLikeCountDto(this);
		}
	}
}
