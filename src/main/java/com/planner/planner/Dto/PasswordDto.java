package com.planner.planner.Dto;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class PasswordDto {
	@NotBlank(message = "필수 항목입니다.")
	@Pattern(regexp = "^(?=.*[\\w])(?=.*[~!@#$%^&*()+|=])[\\w~!@#$%^&*()+|=]{8,16}$", message = "비밀번호는 8~16글자 및 특수문자가 들어가야합니다.")
	private String newPassword;
	@NotBlank(message = "필수 항목입니다.")
	@Pattern(regexp = "^(?=.*[\\w])(?=.*[~!@#$%^&*()+|=])[\\w~!@#$%^&*()+|=]{8,16}$", message = "비밀번호는 8~16글자 및 특수문자가 들어가야합니다.")
	private String confirmPassword;
	@NotBlank(message = "재설정 키는 필수 항목입니다." )
	private String key;
	
	@AssertTrue(message = "비밀번호가 동일하지 않습니다.")
	private boolean isEqualsPassword() {
		return newPassword.equals(confirmPassword);
	}

	public static class Builder {
		private String newPassword;
		private String confirmPassword;
		private String key;

		public Builder setNewPassword(String newPassword) {
			this.newPassword = newPassword;
			return this;
		}

		public Builder setConfirmPassword(String confirmPassword) {
			this.confirmPassword = confirmPassword;
			return this;
		}

		public Builder setKey(String key) {
			this.key = key;
			return this;
		}

		public PasswordDto build() {
			return new PasswordDto(this);
		}
	}
	
	public PasswordDto() {
	}

	public PasswordDto(Builder builder) {
		this.newPassword = builder.newPassword;
		this.confirmPassword = builder.confirmPassword;
		this.key = builder.key;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public String getKey() {
		return key;
	}
}
