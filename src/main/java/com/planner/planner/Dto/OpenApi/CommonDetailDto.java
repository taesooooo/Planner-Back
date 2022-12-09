package com.planner.planner.Dto.OpenApi;

public class CommonDetailDto extends AbstractCommonDetailDto {
	public CommonDetailDto(Builder builder) {
		super(builder);
	}
	
	public static class Builder extends AbstractCommonDetailDto.Builder<Builder> {

		@Override
		public Builder self() {
			// TODO Auto-generated method stub
			return this;
		}

		@Override
		public CommonDetailDto build() {
			// TODO Auto-generated method stub
			return new CommonDetailDto(this);
		}
		
	}
}
