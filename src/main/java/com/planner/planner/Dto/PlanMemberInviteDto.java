package com.planner.planner.Dto;

import java.util.List;

import com.planner.planner.Common.Validation.NotListEmpty;

public class PlanMemberInviteDto {
	@NotListEmpty(message = "초대할 유저를 선택해주세요.")
	private List<String> members;
	
	public static class Builder {
		private List<String> members;

		public Builder setMembers(List<String> members) {
			this.members = members;
			return this;
		}
		
		public PlanMemberInviteDto build() {
			return new PlanMemberInviteDto(this);
		}
	}
	
	public PlanMemberInviteDto() {
	}

	public PlanMemberInviteDto(Builder builder) {
		this.members = builder.members;
	}

	public List<String> getMembers() {
		return members;
	}
	
	
}
