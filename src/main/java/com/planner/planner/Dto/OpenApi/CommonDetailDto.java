package com.planner.planner.Dto.OpenApi;

public class CommonDetailDto {
	private String homepage; // 홈페이지주소
	private String telname; // 전화번호명
	private String zipcode; // 우편번호
	private String overview; // 개요
	
	public static class Builder {
		private String homepage; // 홈페이지주소
		private String telname; // 전화번호명
		private String zipcode; // 우편번호
		private String overview; // 개요
	
		public Builder setHomepage(String homepage) {
			this.homepage = homepage;
			return this;
		}

		public Builder setTelname(String telname) {
			this.telname = telname;
			return this;
		}

		public Builder setZipcode(String zipcode) {
			this.zipcode = zipcode;
			return this;
		}

		public Builder setOverview(String overview) {
			this.overview = overview;
			return this;
		}
		
		public CommonDetailDto build() {
			return new CommonDetailDto(this);
		}
	}

	public CommonDetailDto(Builder builder) {
		this.homepage = builder.homepage;
		this.telname = builder.telname;
		this.zipcode = builder.zipcode;
		this.overview = builder.overview;
	}

	public String getHomepage() {
		return homepage;
	}

	public String getTelname() {
		return telname;
	}

	public String getZipcode() {
		return zipcode;
	}

	public String getOverview() {
		return overview;
	}

}
