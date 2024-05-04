package com.planner.planner.Dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class InvitationDto {
	private int id;
	private int accountId;
	private int plannerId;
	private LocalDateTime inviteDate;
	private LocalDateTime expireDate;
}
