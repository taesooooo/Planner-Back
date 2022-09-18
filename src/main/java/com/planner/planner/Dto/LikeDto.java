package com.planner.planner.Dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class LikeDto {
	List<PlannerDto> likePlanners;
	List<SpotLikeDto> likeSpots;
	
	public static class Builder {
		List<PlannerDto> likePlanners;
		List<SpotLikeDto> likeSpots;

		public Builder setLikePlanners(List<PlannerDto> likePlanners) {
			this.likePlanners = likePlanners;
			return this;
		}
		
		public Builder setLikeSpots(List<SpotLikeDto> likeSpots) {
			this.likeSpots = likeSpots;
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
		this.likeSpots = builder.likeSpots;
	}

	public List<PlannerDto> getLikePlanners() {
		return likePlanners;
	}

	public void setLikePlanners(List<PlannerDto> likePlanners) {
		this.likePlanners = likePlanners;
	}

	public List<SpotLikeDto> getLikeSpots() {
		return likeSpots;
	}

	public void setLikeSpots(List<SpotLikeDto> likeSpots) {
		this.likeSpots = likeSpots;
	}

	@Override
	public String toString() {
		return "LikeDto [likePlanners=" + likePlanners + ", likeSpots=" + likeSpots + "]";
	}
}
