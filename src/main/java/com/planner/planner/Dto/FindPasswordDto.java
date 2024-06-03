package com.planner.planner.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FindPasswordDto {
	@NotBlank(message = "비밀번호 재설정이 필요한 이메일은 필수입니다.")
	private String email;

}
