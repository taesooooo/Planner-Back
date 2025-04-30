package com.planner.planner.Dto;

import com.planner.planner.Common.Validation.ValidCoordinate;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RouteDto {
	@NotBlank(message = "시작 좌표를 정확히 입력해주세요.")
	@ValidCoordinate
	private String start;
	@NotBlank(message = "종료 좌표를 정확히 입력해주세요.")
	@ValidCoordinate
	private String end;
}
