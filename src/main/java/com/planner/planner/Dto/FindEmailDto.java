package com.planner.planner.Dto;

import javax.validation.constraints.Pattern;

public class FindEmailDto {
	@Pattern(regexp = "^[가-힣]*", message = "한글만 입력할 수 있습니다.")
	private String username;
	@Pattern(regexp = "^010[0-9]{4}[0-9]{4}", message = "번호를 제대로 입력해주세요.")
	private String phone;
	
//	@NotBlank(message = "인증 코드는 필수 입니다.")
	@Pattern(regexp = "^[0-9]{6}", message = "인증 코드를 정확히 입력해주세요.")
	private String code;
	
	public static class Builder {
		private String username;
		private String phone;
		private String code;
		
		public Builder setUsername(String username) {
			this.username = username;
			return this;
		}
		public Builder setPhone(String phone) {
			this.phone = phone;
			return this;
		}
		public Builder setCode(String code) {
			this.code = code;
			return this;
		}
		public FindEmailDto build() {
			return new FindEmailDto(this);
		}
	}
	
	public FindEmailDto() {
		
	}

	public FindEmailDto(Builder builder) {
		this.username = builder.username;
		this.phone = builder.phone;
		this.code = builder.code;
	}

	public String getUsername() {
		return username;
	}

	public String getPhone() {
		return phone;
	}
	
	public String getCode() {
		return code;
	}
	
}
