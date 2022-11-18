package com.planner.planner.Dto;

import com.planner.planner.Dto.OpenApi.AbstractBasedDto;

public class SpotDto extends AbstractBasedDto {
	private int likeCount;
	
	public static class Builder extends AbstractBasedDto.Builder<Builder> {
		private int likeCount;
		
		public Builder setLikeCount(int likeCount) {
			this.likeCount = likeCount;
			return self();
		}
		
		@Override
		public Builder self() {
			return this;
		}
		
		@Override
		public SpotDto build() {
			return new SpotDto(this);
		}
	}
	
	public SpotDto(Builder builder) {
		super(builder);
		this.likeCount = builder.likeCount;
	}

	public int getLikeCount() {
		return likeCount;
	}
	
}
