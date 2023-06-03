package com.planner.planner.Dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.planner.planner.Dto.OpenApi.CommonBasedDto;

public class SpotDto {
	@JsonUnwrapped
	private CommonBasedDto basedSpot;
	private int likeCount;
	private boolean likeState;
	
	public static class Builder {
		private CommonBasedDto basedSpot;
		private int likeCount;
		private boolean likeState;
		
		public Builder setBasedSpot(CommonBasedDto basedSpot) {
			this.basedSpot = basedSpot;
			return this;
		}
		public Builder setLikeCount(int likeCount) {
			this.likeCount = likeCount;
			return this;
		}
		public Builder setLikeState(boolean likeState) {
			this.likeState = likeState;
			return this;
		}
		
		public SpotDto build() {
			return new SpotDto(this);
		}
	}

	public SpotDto(Builder builder) {
		this.basedSpot = builder.basedSpot;
		this.likeCount = builder.likeCount;
		this.likeState = builder.likeState;
	}

	public CommonBasedDto getBasedSpot() {
		return basedSpot;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public boolean getLikeState() {
		return likeState;
	}
}
