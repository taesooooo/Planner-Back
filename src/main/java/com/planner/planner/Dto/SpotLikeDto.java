package com.planner.planner.Dto;

import java.time.LocalDate;

import com.planner.planner.Common.Security.UserIdentifier;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SpotLikeDto {
	private int likeId;
	private int accountId;
	private int contentId;
	@PositiveOrZero(message = "지역 코드는 음수일 수 없습니다.")
	private Integer areaCode;
	private String title;
	private String image;
	private LocalDate likeDate;
}
