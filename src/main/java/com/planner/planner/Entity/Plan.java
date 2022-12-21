package com.planner.planner.Entity;

import java.time.LocalDateTime;

public class Plan {
	private int planId;
	private LocalDateTime planDate;
	private int plannerId;
	
	public static class Builder {
		private int planId;
		private LocalDateTime planDate;
		private int plannerId;
		
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
		public Plan build() {
			return new Plan(this);
		}
	}

	public Plan(Builder builder) {
		this.planId = builder.planId;
		this.planDate = builder.planDate;
		this.plannerId = builder.plannerId;
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
}
