package com.planner.planner.Dto;

import javax.validation.constraints.NotBlank;

public class FindPasswordDto {
	@NotBlank(message = "비밀번호 재설정이 필요한 이메일은 필수입니다.")
	private String email;

	public static class Builder {
		private String email;

		public Builder setEmail(String email) {
			this.email = email;
			return this;
		}

		public FindPasswordDto build() {
			return new FindPasswordDto(this);
		}
	}

	public FindPasswordDto() {
	}

	public FindPasswordDto(Builder builder) {
		this.email = builder.email;
	}

	public String getEmail() {
		return email;
	}

}
