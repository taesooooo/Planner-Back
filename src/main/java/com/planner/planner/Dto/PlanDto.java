package com.planner.planner.Dto;

import java.time.LocalDateTime;

import com.planner.planner.Entity.PlanLocation;

public class PlanDto {
	private int planId;
	private LocalDateTime planDate;
	private int plannerId;
	private PlanLocation planLocation;
	
	public static class Builder {
		private int planId;
		private LocalDateTime planDate;
		private int plannerId;
		private PlanLocation planLocation;
		
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
		
		public Builder setPlanLocation(PlanLocation planLocation) {
			this.planLocation = planLocation;
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
		this.planLocation = builder.planLocation;
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

	public PlanLocation getPlanLocation() {
		return planLocation;
	}
}
