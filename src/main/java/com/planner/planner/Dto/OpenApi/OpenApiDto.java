package com.planner.planner.Dto.OpenApi;

public class OpenApiDto {
	private int areaCode;
	private int contentTypeId; 
	private int numOfRows; 
	private double mapX; 
	private double mapY; 
	private int radius;
	private String keyword;
	private int contentId;
	private int pageNo;
	
	public static class Builder {
		private int areaCode;
		private int contentTypeId; 
		private int numOfRows; 
		private double mapX; 
		private double mapY; 
		private int radius;
		private String keyword; 
		private int contentId;
		private int pageNo;
		
		public Builder setAreaCode(int areaCode) {
			this.areaCode = areaCode;
			return this;
		}
		public Builder setContentTypeId(int contentTypeId) {
			this.contentTypeId = contentTypeId;
			return this;
		}
		public Builder setNumOfRows(int numOfRows) {
			this.numOfRows = numOfRows;
			return this;
		}
		public Builder setMapX(double mapX) {
			this.mapX = mapX;
			return this;
		}
		public Builder setMapY(double mapY) {
			this.mapY = mapY;
			return this;
		}
		public Builder setRadius(int radius) {
			this.radius = radius;
			return this;
		}
		public Builder setKeyword(String keyword) {
			this.keyword = keyword;
			return this;
		}
		public Builder setContentId(int contentId) {
			this.contentId = contentId;
			return this;
		}
		public Builder setPageNo(int pageNo) {
			this.pageNo = pageNo;
			return this;
		}
		
		public OpenApiDto build() {
			return new OpenApiDto(this);
		}
	}
	
	public OpenApiDto() {
		
	}
	
	public OpenApiDto(Builder builder) {
		this.areaCode = builder.areaCode;
		this.contentTypeId = builder.contentTypeId;
		this.numOfRows = builder.numOfRows;
		this.mapX = builder.mapX;
		this.mapY = builder.mapY;
		this.radius = builder.radius;
		this.keyword = builder.keyword;
		this.contentId = builder.contentId;
		this.pageNo = builder.pageNo;
	}

	public int getAreaCode() {
		return areaCode;
	}

	public int getContentTypeId() {
		return contentTypeId;
	}

	public int getNumOfRows() {
		return numOfRows;
	}

	public double getMapX() {
		return mapX;
	}

	public double getMapY() {
		return mapY;
	}

	public int getRadius() {
		return radius;
	}

	public String getKeyword() {
		return keyword;
	}

	public int getContentId() {
		return contentId;
	}

	public int getPageNo() {
		return pageNo;
	}	
}
