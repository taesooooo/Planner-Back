package com.planner.planner.Dto;

import java.util.List;

public class PlannerListDto {
	private List<PlannerDto> plannerList;
	
	public static class Builder {
		private List<PlannerDto> plannerList;
		
		public Builder setPlannerList(List<PlannerDto> plannerList) {
			this.plannerList = plannerList;
			return this;
		}
		
		public PlannerListDto build() {
			return new PlannerListDto(this);
		}
	}

	public PlannerListDto(Builder builder) {
		this.plannerList = builder.plannerList;
	}

	public List<PlannerDto> getPlannerList() {
		return plannerList;
	}
}
