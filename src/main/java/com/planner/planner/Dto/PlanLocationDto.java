package com.planner.planner.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PlanLocationDto {
	private int locationId;
	private int locationContetntId;
	private String locationImage;
	private int locationTranspotation;
	@JsonIgnore
	private int planId;
	
	public static class Builder {
		private int locationId;
		private int locationContetntId;
		private String locationImage;
		private int locationTranspotation;
		private int planId;
		
		public Builder setLocationId(int locationId) {
			this.locationId = locationId;
			return this;
		}
		public Builder setLocationContetntId(int locationContetntId) {
			this.locationContetntId = locationContetntId;
			return this;
		}
		public Builder setLocationImage(String locationImage) {
			this.locationImage = locationImage;
			return this;
		}
		public Builder setLocationTranspotation(int locationTranspotation) {
			this.locationTranspotation = locationTranspotation;
			return this;
		}
		public Builder setPlanId(int planId) {
			this.planId = planId;
			return this;
		}
		public PlanLocationDto build(){
			return new PlanLocationDto(this);
		}
	}

	public PlanLocationDto(Builder builder) {
		this.locationId = builder.locationId;
		this.locationContetntId = builder.locationContetntId;
		this.locationImage = builder.locationImage;
		this.locationTranspotation = builder.locationTranspotation;
		this.planId = builder.planId;
	}

	public int getLocationId() {
		return locationId;
	}

	public int getLocationContetntId() {
		return locationContetntId;
	}

	public String getLocationImage() {
		return locationImage;
	}

	public int getLocationTranspotation() {
		return locationTranspotation;
	}

	public int getPlanId() {
		return planId;
	}
}
