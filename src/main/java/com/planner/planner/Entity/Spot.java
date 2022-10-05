package com.planner.planner.Entity;

public class Spot {
	private int spotId;
	private String spotName;
	private String spotImage;
	private String contryName;
	private String cityName;
	private String detail;
	private int likeCount;

	public static class Builder {
		private int spotId;
		private String spotName;
		private String spotImage;
		private String contryName;
		private String cityName;
		private String detail;
		private int likeCount;

		public Builder setSpotId(int spotId) {
			this.spotId = spotId;
			return this;
		}
		public Builder setSpotName(String spotName) {
			this.spotName = spotName;
			return this;
		}
		public Builder setSpotImage(String spotImage) {
			this.spotImage = spotImage;
			return this;
		}
		public Builder setContryName(String contryName) {
			this.contryName = contryName;
			return this;
		}
		public Builder setCityName(String cityName) {
			this.cityName = cityName;
			return this;
		}
		public Builder setDetail(String detail) {
			this.detail = detail;
			return this;
		}
		public Builder setLikeCount(int likeCount) {
			this.likeCount = likeCount;
			return this;
		}
		public Spot build() {
			return new Spot(this);
		}
	}

	public Spot() {

	}

	public Spot(Builder builder) {
		this.spotId = builder.spotId;
		this.spotName = builder.spotName;
		this.spotImage = builder.spotImage;
		this.contryName = builder.contryName;
		this.cityName = builder.cityName;
		this.detail = builder.detail;
		this.likeCount = builder.likeCount;
	}

	public int getSpotId() {
		return spotId;
	}

	public void setSpotId(int spotId) {
		this.spotId = spotId;
	}

	public String getSpotName() {
		return spotName;
	}

	public void setSpotName(String spotName) {
		this.spotName = spotName;
	}

	public String getSpotImage() {
		return spotImage;
	}

	public void setSpotImage(String spotImage) {
		this.spotImage = spotImage;
	}

	public String getContryName() {
		return contryName;
	}

	public void setContryName(String contryName) {
		this.contryName = contryName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	@Override
	public String toString() {
		return "Spot [spotId=" + spotId + ", spotName=" + spotName + ", spotImage=" + spotImage + ", contryName="
				+ contryName + ", cityName=" + cityName + ", detail=" + detail + ", likeCount=" + likeCount + "]";
	}
}
