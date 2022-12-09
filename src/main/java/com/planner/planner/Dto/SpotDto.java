package com.planner.planner.Dto;

import com.planner.planner.Dto.OpenApi.AbstractCommonBasedDto;

public class SpotDto extends AbstractCommonBasedDto {
	
	public static class Builder extends AbstractCommonBasedDto.Builder<Builder> {
		public Builder setLikeCount(int likeCount) {
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
	}
	
}
