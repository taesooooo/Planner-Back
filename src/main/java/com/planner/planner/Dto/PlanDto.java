package com.planner.planner.Dto;

import java.time.LocalDateTime;
import java.util.List;

import com.planner.planner.Entity.Plan;
import com.planner.planner.Entity.PlanLocation;

public class PlanDto {
	private int planId;
	private LocalDateTime planDate;
	private int plannerId;
	private List<PlanLocationDto> planLocations;
	
	public static class Builder {
		private int planId;
		private LocalDateTime planDate;
		private int plannerId;
		private List<PlanLocationDto> planLocations;
		
		public Builder setPlanId(int planId) {
			this.planId = planId;
			return this;
		}
		public Builder setPlanDate(LocalDateTime planDate) {
			this.planDate = planDate;
			return this;
		}
		public Builder setPlannerId(int plannerId) {
			this.plannerId = plannerId;
			return this;
		}
		
		public Builder setPlanLocations(List<PlanLocationDto> planLocations) {
			this.planLocations = planLocations;
			return this;
		}
		public PlanDto build() {
			return new PlanDto(this);
		}
	}

	public PlanDto(Builder builder) {
		this.planId = builder.planId;
		this.planDate = builder.planDate;
		this.plannerId = builder.plannerId;
		this.planLocations = builder.planLocations;
	}

	public int getPlanId() {
		return planId;
	}

	public LocalDateTime getPlanDate() {
		return planDate;
	}

	public int getPlannerId() {
		return plannerId;
	}

	public List<PlanLocationDto> getPlanLocation() {
		return planLocations;
	}
	
//	public static PlanDto from(Plan plan) {
//		return new PlanDto.Builder()
//				.setPlanId(plan.getPlanId())
//				.setPlanDate(plan.getPlanDate())
//				.setPlanLocations(plan.getPlanLocations())
//				.setPlannerId(plan.getPlannerId())
//				.build();
//	}
}
