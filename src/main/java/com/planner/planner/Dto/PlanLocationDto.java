package com.planner.planner.Dto;

import javax.validation.constraints.Min;

import com.planner.planner.Common.ValidationGroups.PlanLocationCreateGroup;
import com.planner.planner.Common.ValidationGroups.PlanLocationUpdateGroup;

public class PlanLocationDto {
	private int locationId;
	
	@Min(value = 1, message = "콘텐츠 아이디가 잘못되었습니다.", groups = {PlanLocationCreateGroup.class, PlanLocationUpdateGroup.class})
	private int locationContentId;
	
	private String locationName;
	private String locationImage;
	private String locationAddr;
	private double locationMapx;
	private double locationMapy;
	
	@Min(value = 1, message = "이동수단이 잘못되었습니다.", groups = {PlanLocationCreateGroup.class, PlanLocationUpdateGroup.class})
	private int locationTransportation;
	
	@Min(value = 1, message = "인덱스가 잘못되었습니다.", groups = PlanLocationUpdateGroup.class)
	private int index;
	private int planId;
	
	public static class Builder {
		private int locationId;
		private int locationContentId;
		private String locationName;
		private String locationImage;
		private String locationAddr;
		private double locationMapx;
		private double locationMapy;
		private int locationTransportation;
		private int index;
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
		public Builder setLocationAddr(String addr) {
			this.locationAddr = addr;
			return this;
		}
		public Builder setLocationMapx(double mapx) {
			this.locationMapx = mapx;
			return this;
		}
		public Builder setLocationMapy(double mapy) {
			this.locationMapy = mapy;
			return this;
		}
		public Builder setLocationTransportation(int locationTransportation) {
			this.locationTransportation = locationTransportation;
			return this;
		}
		public Builder setIndex(int index) {
			this.index = index;
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
		this.locationAddr = builder.locationAddr;
		this.locationMapx = builder.locationMapx;
		this.locationMapy = builder.locationMapy;
		this.locationTransportation = builder.locationTransportation;
		this.index = builder.index;
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

	public String getLocationAddr() {
		return locationAddr;
	}

	public double getLocationMapx() {
		return locationMapx;
	}

	public double getLocationMapy() {
		return locationMapy;
	}

	public int getLocationTransportation() {
		return locationTransportation;
	}

	public int getIndex() {
		return index;
	}
	
	public int getPlanId() {
		return planId;
	}

}
