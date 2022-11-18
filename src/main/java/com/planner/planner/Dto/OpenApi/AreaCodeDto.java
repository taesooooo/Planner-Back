package com.planner.planner.Dto.OpenApi;

public class AreaCodeDto {
	private String rnum;
	private String code;
	private String name;
	
	public static class Builder {
		private String rnum;
		private String code;
		private String name;
		
		public Builder setRnum(String rnum) {
			this.rnum = rnum;
			return this;
		}
		public Builder setCode(String code) {
			this.code = code;
			return this;
		}
		public Builder setName(String name) {
			this.name = name;
			return this;
		}
		
		public AreaCodeDto build() {
			return new AreaCodeDto(this);
		}
	}

	public AreaCodeDto(Builder builder) {
		this.rnum = builder.rnum;
		this.code = builder.code;
		this.name = builder.name;
	}

	public String getRnum() {
		return rnum;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
}
