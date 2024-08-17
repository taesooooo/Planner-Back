package com.planner.planner.Dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AuthenticationCodeDto {
	private int id;
	
	@Pattern(regexp = "^010[0-9]{4}[0-9]{4}", message = "번호를 제대로 입력해주세요.")
	private String phone;
	
	@Email(message = "정확한 이메일을 입력해주세요.")
	private String email;

	@NotBlank(message = "인증 코드는 필수 입니다.")
	@Pattern(regexp = "^[0-9]{6}", message = "인증 코드를 정확히 입력해주세요.")
	private String code;
	private boolean codeConfirm;
	private LocalDateTime expireDate;
	private LocalDateTime createDate;
}
