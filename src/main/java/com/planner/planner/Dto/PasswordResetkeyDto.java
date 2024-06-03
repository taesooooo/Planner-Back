package com.planner.planner.Dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PasswordResetkeyDto {
	private int id;
	private String resetKey;
	private int accountId;
	private LocalDateTime expireDate;
	private LocalDateTime createDate;
}
