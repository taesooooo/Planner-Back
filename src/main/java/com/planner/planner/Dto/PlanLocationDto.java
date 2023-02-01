package com.planner.planner.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PlanLocationDto {
	private int locationId;
	private int locationContetntId;
	private String locationImage;
	private int locationTransportation;
	private int planId;
	
	public static class Builder {
		private int locationId;
		private int locationContetntId;
		private String locationImage;
		private int locationTransportation;
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
		public Builder setLocationTransportation(int locationTransportation) {
			this.locationTransportation = locationTransportation;
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
	
	public PlanLocationDto() {
		
	}

	public PlanLocationDto(Builder builder) {
		this.locationId = builder.locationId;
		this.locationContetntId = builder.locationContetntId;
		this.locationImage = builder.locationImage;
		this.locationTransportation = builder.locationTransportation;
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

	public int getLocationTransportation() {
		return locationTransportation;
	}

	public int getPlanId() {
		return planId;
	}
}
