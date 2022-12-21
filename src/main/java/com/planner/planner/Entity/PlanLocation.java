package com.planner.planner.Entity;

public class PlanLocation {
	private int locationId;
	private int locationContetntId;
	private String locationImage;
	private int locationTranspotation;
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
		public PlanLocation build(){
			return new PlanLocation(this);
		}
	}

	public PlanLocation(Builder builder) {
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
