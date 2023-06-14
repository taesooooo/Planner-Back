package com.planner.planner.Dto.OpenApi;

public class CommonBasedDto extends AbstractCommonBasedDto{
	
	public static class Builder extends AbstractCommonBasedDto.Builder<Builder> {

		@Override
		public Builder self() {
			return this;
		}

		@Override
		public CommonBasedDto build() {
			return new CommonBasedDto(this);
		}
		
	}
	
	public CommonBasedDto(Builder builder) {
		super(builder);
	}
}
