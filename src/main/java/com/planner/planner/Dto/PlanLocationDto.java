package com.planner.planner.Dto;

public class PlanLocationDto {
	private int locationId;
	private int locationContentId;
	private String locationName;
	private String locationImage;
	private String addr;
	private float mapx;
	private float mapy;
	private int locationTransportation;
	private int planId;
	
	public static class Builder {
		private int locationId;
		private int locationContentId;
		private String locationName;
		private String locationImage;
		private String addr;
		private float mapx;
		private float mapy;
		private int locationTransportation;
		private int planId;
		
		public Builder setLocationId(int locationId) {
			this.locationId = locationId;
			return this;
		}
		public Builder setLocationContentId(int locationContetntId) {
			this.locationContentId = locationContetntId;
			return this;
		}
		
		public Builder setLocationName(String locationName) {
			this.locationName = locationName;
			return this;
		}
		
		public Builder setLocationImage(String locationImage) {
			this.locationImage = locationImage;
			return this;
		}
		public Builder setAddr(String addr) {
			this.addr = addr;
			return this;
		}
		public Builder setMapx(float mapx) {
			this.mapx = mapx;
			return this;
		}
		public Builder setMapy(float mapy) {
			this.mapy = mapy;
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
		this.locationContentId = builder.locationContentId;
		this.locationName = builder.locationName;
		this.locationImage = builder.locationImage;
		this.addr = builder.addr;
		this.mapx = builder.mapx;
		this.mapy = builder.mapy;
		this.locationTransportation = builder.locationTransportation;
		this.planId = builder.planId;
	}

	public int getLocationId() {
		return locationId;
	}

	public int getLocationContentId() {
		return locationContentId;
	}

	public String getLocationName() {
		return locationName;
	}

	public String getLocationImage() {
		return locationImage;
	}

	public String getAddr() {
		return addr;
	}

	public float getMapx() {
		return mapx;
	}

	public float getMapy() {
		return mapy;
	}

	public int getLocationTransportation() {
		return locationTransportation;
	}

	public int getPlanId() {
		return planId;
	}
}
