package com.planner.planner.Dto;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.planner.planner.Common.ValidationGroups.PlanCreateGroup;
import com.planner.planner.Common.ValidationGroups.PlanUpdateGroup;

public class PlanDto {
	private int planId;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "날짜 항목은 필수 항목입니다.", groups = {PlanCreateGroup.class, PlanUpdateGroup.class})
	private LocalDate planDate;
	
	private int plannerId;
	private List<PlanLocationDto> planLocations;
	
	@Min(value = 1, message = "인덱스가 잘못되었습니다.", groups = PlanUpdateGroup.class)
	private int index;
	
	public static class Builder {
		private int planId;
		private LocalDate planDate;
		private int plannerId;
		private List<PlanLocationDto> planLocations;
		private int index;
		
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
		public Builder setIndex(int index) {
			this.index = index;
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
		this.index = builder.index;
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

	public int getIndex() {
		return index;
	}
	
}
