package com.planner.planner.Dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class LikeDto {
	List<PlannerDto> likePlanners;
	
	public static class Builder {
		List<PlannerDto> likePlanners;

		public Builder setLikePlanners(List<PlannerDto> likePlanners) {
			this.likePlanners = likePlanners;
			return this;
		}
		
		public LikeDto build() {
			return new LikeDto(this);
		}
	}
	
	public LikeDto() {
	}

	public LikeDto(Builder builder) {
		this.likePlanners = builder.likePlanners;
	}

	public List<PlannerDto> getLikePlanners() {
		return likePlanners;
	}

	public void setLikePlanners(List<PlannerDto> likePlanners) {
		this.likePlanners = likePlanners;
	}

	@Override
	public String toString() {
		return "LikeDto [likePlanners=" + likePlanners + "]";
	}
	
}
