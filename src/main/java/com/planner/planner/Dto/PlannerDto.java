package com.planner.planner.Dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.planner.planner.Entity.Plan;
import com.planner.planner.Entity.Planner;

public class PlannerDto {
	private int plannerId;
	private int accountId;
	private String title;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime planDateStart;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime planDateEnd;
	private PlanDto plan;
	private PlanMemberDto planMember;
	private int likeCount;
	private LocalDateTime createDate;
	private LocalDateTime updateDate;
	
	private List<Plan> plans;

	public static class Builder {
		private int plannerId;
		private int accountId;
		private String title;
		private LocalDateTime planDateStart;
		private LocalDateTime planDateEnd;
		private PlanDto plan;
		private PlanMemberDto planMember;
		private int likeCount;
		private LocalDateTime createDate;
		private LocalDateTime updateDate;
		
		private List<Plan> plans;

		public Builder setPlannerId(int plannerId) {
			this.plannerId = plannerId;
			return this;
		}

		public Builder setAccountId(int accountId) {
			this.accountId = accountId;
			return this;
		}

		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}

		public Builder setPlanDateStart(LocalDateTime planDateStart) {
			this.planDateStart = planDateStart;
			return this;
		}

		public Builder setPlanDateEnd(LocalDateTime planDateEnd) {
			this.planDateEnd = planDateEnd;
			return this;
		}

		public Builder setPlan(PlanDto plan) {
			this.plan = plan;
			return this;
		}

		public Builder setPlanMember(PlanMemberDto planMember) {
			this.planMember = planMember;
			return this;
		}

		public Builder setLikeCount(int likeCount) {
			this.likeCount = likeCount;
			return this;
		}

		public Builder setCreateDate(LocalDateTime createDate) {
			this.createDate = createDate;
			return this;
		}

		public Builder setUpdateDate(LocalDateTime updateDate) {
			this.updateDate = updateDate;
			return this;
		}

		public Builder setPlans(List<Plan> plans) {
			this.plans = plans;
			return this;
		}

		public PlannerDto build() {
			return new PlannerDto(this);
		}
	}

	public PlannerDto() {

	}

	public PlannerDto(Builder builder) {
		this.plannerId = builder.plannerId;
		this.accountId = builder.accountId;
		this.title = builder.title;
		this.planDateStart = builder.planDateStart;
		this.planDateEnd = builder.planDateEnd;
		this.plan = builder.plan;
		this.planMember = builder.planMember;
		this.plan = builder.plan;
		this.likeCount = builder.likeCount;
		this.createDate = builder.createDate;
		this.updateDate = builder.updateDate;
		this.plans = builder.plans;
	}

	public int getPlannerId() {
		return plannerId;
	}

	public int getAccountId() {
		return accountId;
	}

	public String getTitle() {
		return title;
	}

	public LocalDateTime getPlanDateStart() {
		return planDateStart;
	}

	public LocalDateTime getPlanDateEnd() {
		return planDateEnd;
	}

	public PlanDto getPlan() {
		return plan;
	}

	public PlanMemberDto getPlanMember() {
		return planMember;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}
	
	public List<Plan> getPlans() {
		return plans;
	}

	public static PlannerDto from(Planner planner) {
		return new PlannerDto.Builder()
				.setPlannerId(planner.getPlannerId())
				.setAccountId(planner.getAccountId())
				.setTitle(planner.getTitle())
				.setPlanDateStart(planner.getPlanDateStart())
				.setPlanDateEnd(planner.getPlanDateEnd())
				.setCreateDate(planner.getCreateDate())
				.setUpdateDate(planner.getUpdateDate())
				.setLikeCount(planner.getLikeCount())
				.setPlans(planner.getPlans())
				.build();
	}
	
	public Planner toEntity() {
		return new Planner.Builder()
				.setPlannerId(plannerId)
				.setAccountId(accountId)
				.setTitle(title)
				.setPlanDateStart(planDateStart)
				.setPlanDateEnd(planDateEnd)
				.setCreateDate(createDate)
				.setUpdateDate(updateDate)
				.setLikeCount(likeCount)
				.setPlans(plans)
				.build();
	}
}
