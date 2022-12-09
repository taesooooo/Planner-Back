package com.planner.planner.Dto.OpenApi;

public abstract class AbstractCommonDetailDto {
	private String homepage; // 홈페이지주소
	private String telname; // 전화번호명
	private String zipcode; // 우편번호
	private String overview; // 개요
	
	public abstract static class Builder<T extends Builder<T>> {
		private String homepage; // 홈페이지주소
		private String telname; // 전화번호명
		private String zipcode; // 우편번호
		private String overview; // 개요
	
		public T setHomepage(String homepage) {
			this.homepage = homepage;
			return self();
		}

		public T setTelname(String telname) {
			this.telname = telname;
			return self();
		}

		public T setZipcode(String zipcode) {
			this.zipcode = zipcode;
			return self();
		}

		public T setOverview(String overview) {
			this.overview = overview;
			return self();
		}
		
		public abstract T self();
		
		public abstract AbstractCommonDetailDto build();
	}

	public AbstractCommonDetailDto(Builder<?> builder) {
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
