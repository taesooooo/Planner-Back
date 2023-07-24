package com.planner.planner.Dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class AuthenticationCodeDto {
	private int id;
	
	@Pattern(regexp = "^010[0-9]{4}[0-9]{4}", message = "번호를 제대로 입력해주세요.")
	private String phone;
	
	@Email(message = "정확한 이메일을 입력해주세요.")
	private String email;

	@NotBlank(message = "인증 코드는 필수 입니다.")
	@Pattern(regexp = "^[0-9]{6}", message = "인증 코드를 정확히 입력해주세요.")
	private String code;
	private boolean confirm;
	private LocalDateTime expireDate;
	private LocalDateTime createDate;

	public static class Builder {
		private int id;
		private String phone;
		private String email;
		private String code;
		private boolean confirm;
		private LocalDateTime expireDate;
		private LocalDateTime createDate;

		public Builder setId(int id) {
			this.id = id;
			return this;
		}

		public Builder setPhone(String phone) {
			this.phone = phone;
			return this;
		}

		public Builder setEmail(String email) {
			this.email = email;
			return this;
		}

		public Builder setCode(String code) {
			this.code = code;
			return this;
		}

		public Builder setConfirm(boolean confirm) {
			this.confirm = confirm;
			return this;
		}

		public Builder setExpireDate(LocalDateTime expireDate) {
			this.expireDate = expireDate;
			return this;
		}

		public Builder setCreateDate(LocalDateTime createDate) {
			this.createDate = createDate;
			return this;
		}

		public AuthenticationCodeDto build() {
			return new AuthenticationCodeDto(this);
		}
	}

	public AuthenticationCodeDto() {

	}

	public AuthenticationCodeDto(Builder builder) {
		this.id = builder.id;
		this.phone = builder.phone;
		this.email = builder.email;
		this.code = builder.code;
		this.confirm = builder.confirm;
		this.expireDate = builder.expireDate;
		this.createDate = builder.createDate;
	}

	public int getId() {
		return id;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	public String getCode() {
		return code;
	}

	public boolean isConfirm() {
		return confirm;
	}

	public LocalDateTime getExpireDate() {
		return expireDate;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}
}
