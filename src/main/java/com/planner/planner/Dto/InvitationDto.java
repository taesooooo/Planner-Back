package com.planner.planner.Dto;

import java.time.LocalDateTime;

import com.planner.planner.Common.Security.UserIdentifier;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class InvitationDto implements UserIdentifier {
	private int id;
	private int accountId;
	private int plannerId;
	private LocalDateTime inviteDate;
	private LocalDateTime expireDate;
}
