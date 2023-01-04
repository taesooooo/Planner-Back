package com.planner.planner.Entity;

import java.time.LocalDateTime;
import java.util.List;

public class Plan {
	private int planId;
	private LocalDateTime planDate;
	private int plannerId;
	private List<PlanLocation> planLocations;
	
	public static class Builder {
		private int planId;
		private LocalDateTime planDate;
		private int plannerId;
		private List<PlanLocation> planLocations;
		
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
		
		public Builder setPlanLocations(List<PlanLocation> planLocations) {
			this.planLocations = planLocations;
			return this;
		}
		public Plan build() {
			return new Plan(this);
		}
	}

	public Plan(Builder builder) {
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

	public List<PlanLocation> getPlanLocations() {
		return planLocations;
	}
}
