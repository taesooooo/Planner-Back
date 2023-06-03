package com.planner.planner.Dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.planner.planner.Dto.OpenApi.CommonDetailDto;

public class SpotDetailDto {
	@JsonUnwrapped
	private CommonDetailDto detail;
	private int likeCount;
	private boolean likeState;
	
	public static class Builder {
		private CommonDetailDto detail;
		private int likeCount;
		private boolean likeState;
		
		public Builder setDetail(CommonDetailDto detail) {
			this.detail = detail;
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
		
		public SpotDetailDto build() {
			return new SpotDetailDto(this);
		}
	}

	public SpotDetailDto(Builder builder) {
		this.detail = builder.detail;
		this.likeCount = builder.likeCount;
		this.likeState = builder.likeState;
	}

	public CommonDetailDto getDetail() {
		return detail;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public boolean getLikeState() {
		return likeState;
	}
}
