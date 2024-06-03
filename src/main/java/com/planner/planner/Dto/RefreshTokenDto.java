package com.planner.planner.Dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RefreshTokenDto {
	private int id;
	private String email;
	private String token;
}
