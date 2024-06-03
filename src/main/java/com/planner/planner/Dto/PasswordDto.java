package com.planner.planner.Dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
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
}
