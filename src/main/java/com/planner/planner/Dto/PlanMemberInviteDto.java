package com.planner.planner.Dto;

import java.util.List;

import com.planner.planner.Common.Validation.NotListEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PlanMemberInviteDto {
	@NotListEmpty(message = "초대할 유저를 선택해주세요.")
	private List<String> members;
	
}
