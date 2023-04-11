package com.planner.planner.Dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PlanDto {
	private int planId;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate planDate;
	private int plannerId;
	private List<PlanLocationDto> planLocations;
	
	public static class Builder {
		private int planId;
		private LocalDate planDate;
		private int plannerId;
		private List<PlanLocationDto> planLocations;
		
		public Builder setPlanId(int planId) {
			this.planId = planId;
			return this;
		}
		public Builder setPlanDate(LocalDate planDate) {
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
	
	public PlanDto() {
		
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

	public LocalDate getPlanDate() {
		return planDate;
	}

	public int getPlannerId() {
		return plannerId;
	}

	public List<PlanLocationDto> getPlanLocations() {
		return planLocations;
	}
}
