package com.planner.planner.Dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PlanMemberDto {
	private int planMemberId;
	private int plannerId;
	private int accountId;
}
