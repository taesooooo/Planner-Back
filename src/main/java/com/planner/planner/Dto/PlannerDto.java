package com.planner.planner.Dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PlannerDto {
	private int plannerId;
	private int accountId;
	private String title;
	@JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
	private LocalDateTime planDateStart;
	@JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
	private LocalDateTime planDateEnd;
	private String creatorEmail;
	private List<String> planMemberEmails;
	private int likeCount;
	@JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
	private LocalDateTime createDate;
	@JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
	private LocalDateTime updateDate;
	
	private List<PlanDto> plans;

	public static class Builder {
		private int plannerId;
		private int accountId;
		private String title;
		private LocalDateTime planDateStart;
		private LocalDateTime planDateEnd;
		private String creatorEmail;
		private List<String> planMemberEmails;
		private int likeCount;
		private LocalDateTime createDate;
		private LocalDateTime updateDate;
		
		private List<PlanDto> plans;

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

		public Builder setCreatorEmail(String creatorEmail) {
			this.creatorEmail = creatorEmail;
			return this;
		}

		public Builder setPlanMemberEmails(List<String> planMemberEmails) {
			this.planMemberEmails = planMemberEmails;
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

		public Builder setPlans(List<PlanDto> plans) {
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
		this.creatorEmail = builder.creatorEmail;
		this.planMemberEmails = builder.planMemberEmails;
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

	public String getCreatorEmail() {
		return creatorEmail;
	}

	public List<String> getPlanMemberEmails() {
		return planMemberEmails;
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
	
	public List<PlanDto> getPlans() {
		return plans;
	}

	public static PlannerDto from(PlannerDto planner) {
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
	
	public PlannerDto toEntity() {
		return new PlannerDto.Builder()
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
