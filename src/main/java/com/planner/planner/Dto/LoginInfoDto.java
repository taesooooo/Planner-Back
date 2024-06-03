package com.planner.planner.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginInfoDto {
	private AccountDto user;
	private String accessToken;
	@JsonIgnore
	private String refreshToken;
}
