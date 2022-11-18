package com.planner.planner.Dto.OpenApi;

public class BasedDto extends AbstractBasedDto {
	
	public BasedDto(Builder builder) {
		super(builder);
	}

	public static class Builder extends AbstractBasedDto.Builder<Builder> {

		@Override
		public Builder self() {
			// TODO Auto-generated method stub
			return this;
		}

		@Override
		public BasedDto build() {
			// TODO Auto-generated method stub
			return new BasedDto(this);
		}
		
	}
}
