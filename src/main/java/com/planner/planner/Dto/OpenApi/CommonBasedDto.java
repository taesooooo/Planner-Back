package com.planner.planner.Dto.OpenApi;

public class CommonBasedDto extends AbstractCommonBasedDto {
	
	public CommonBasedDto(Builder builder) {
		super(builder);
	}

	public static class Builder extends AbstractCommonBasedDto.Builder<Builder> {

		@Override
		public Builder self() {
			// TODO Auto-generated method stub
			return this;
		}

		@Override
		public CommonBasedDto build() {
			// TODO Auto-generated method stub
			return new CommonBasedDto(this);
		}
		
	}
}
