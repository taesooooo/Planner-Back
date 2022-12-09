package com.planner.planner.Dto;

import com.planner.planner.Dto.OpenApi.AbstractCommonDetailDto;

public class SpotDetailDto extends AbstractCommonDetailDto {
	private int likeCount;

	public SpotDetailDto(Builder builder) {
		super(builder);
		this.likeCount = builder.likeCount;
	}
	
	public static class Builder extends AbstractCommonDetailDto.Builder<Builder> {
		private int likeCount;
		
		public Builder setLikeCount(int likeCount) {
			this.likeCount = likeCount;
			return self();
		}
		
		@Override
		public Builder self() {
			// TODO Auto-generated method stub
			return this;
		}

		@Override
		public SpotDetailDto build() {
			// TODO Auto-generated method stub
			return new SpotDetailDto(this);
		}
		
	}

	public int getLikeCount() {
		return likeCount;
	}
	
	
}
