package com.planner.planner.Dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AuthenticationCodeDto {
	private int id;
	
	@NotBlank(message = "휴대폰 번호는 필수 입니다.")
	@Pattern(regexp = "^010[0-9]{4}[0-9]{4}", message = "제대로 입력해주세요.")
	private String phone;

	@NotBlank(message = "인증 코드는 필수 입니다.")
	@Pattern(regexp = "^[0-9]{6}", message = "인증 코드를 정확히 입력해주세요.")
	private String code;
	
	private LocalDateTime createDate;

	public static class Builder {
		private int id;
		private String phone;
		private String code;
		private LocalDateTime createDate;

		public Builder setId(int id) {
			this.id = id;
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
		this.code = builder.code;
		this.createDate = builder.createDate;
	}

	public int getId() {
		return id;
	}

	public String getPhone() {
		return phone;
	}

	public String getCode() {
		return code;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}
}
