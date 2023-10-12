package com.planner.planner.Dto;

public class AcceptInvateDto {
	private int plannerId;
	
	public static class Builder {
		private int plannerId;

		public Builder setPlannerId(int plannerId) {
			this.plannerId = plannerId;
			return this;
		}
		
		public AcceptInvateDto build() {
			return new AcceptInvateDto(this);
		}
	}
	
	public AcceptInvateDto() {
		
	}
	
	public AcceptInvateDto(Builder builder) {
		this.plannerId = builder.plannerId;
	}

	public int getPlannerId() {
		return plannerId;
	}
	
}
