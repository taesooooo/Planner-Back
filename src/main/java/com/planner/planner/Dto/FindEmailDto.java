package com.planner.planner.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FindEmailDto {
	@Pattern(regexp = "^[가-힣]*", message = "한글만 입력할 수 있습니다.")
	@NotBlank(message = "이름을 제대로 입력해주세요.")
	private String userName;
	@Pattern(regexp = "^010[0-9]{4}[0-9]{4}", message = "번호를 제대로 입력해주세요.")
	private String phone;
	
//	@NotBlank(message = "인증 코드는 필수 입니다.")
	@Pattern(regexp = "^[0-9]{6}", message = "인증 코드를 정확히 입력해주세요.")
	private String code;
}
