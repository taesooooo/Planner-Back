package com.planner.planner.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.planner.planner.Dto.PlannerDto;

public class Planner {
	private int plannerId;
	private int accountId;
	private String title;
	private LocalDate planDateStart;
	private LocalDate planDateEnd;
	private int memberCount;
	private String member;
	private String plan;
	private int recommendCount;
	private LocalDateTime createDate;
	private LocalDateTime updateDate;
	
	public static class Builder {
		private int plannerId = 0;
		private int accountId = 0;
		private String title;
		private LocalDate planDateStart;
		private LocalDate planDateEnd;
		private int memberCount = 1;
		private String member;
		private String plan;
		private int recommendCount = 0; 
		private LocalDateTime createDate;
		private LocalDateTime updateDate;
		
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

		public Builder setPlanDateStart(LocalDate planDateStart) {
			this.planDateStart = planDateStart;
			return this;
		}

		public Builder setPlanDateEnd(LocalDate planDateEnd) {
			this.planDateEnd = planDateEnd;
			return this;
		}

		public Builder setMemberCount(int memberCount) {
			this.memberCount = memberCount;
			return this;
		}

		public Builder setMember(String member) {
			this.member = member;
			return this;
		}

		public Builder setPlan(String plan) {
			this.plan = plan;
			return this;
		}

		public Builder setRecommendCount(int recommendCount) {
			this.recommendCount = recommendCount;
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

		public Planner build() {
			return new Planner(this);
		}
	}
	
	public Planner(Builder builder) {
		this.plannerId = builder.plannerId;
		this.accountId = builder.accountId;
		this.title = builder.title;
		this.planDateStart = builder.planDateStart;
		this.planDateEnd = builder.planDateEnd;
		this.memberCount = builder.memberCount;
		this.member = builder.member;
		this.plan = builder.plan;
		this.recommendCount = builder.recommendCount;
		this.createDate = builder.createDate;
		this.updateDate = builder.updateDate;
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

	public LocalDate getPlanDateStart() {
		return planDateStart;
	}

	public LocalDate getPlanDateEnd() {
		return planDateEnd;
	}

	public int getMemberCount() {
		return memberCount;
	}

	public String getMember() {
		return member;
	}

	public String getPlan() {
		return plan;
	}

	public int getRecommendCount() {
		return recommendCount;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}
	
	public PlannerDto toDto() {
		return new PlannerDto.Builder()
				.setPlannerId(plannerId)
				.setAccountId(accountId)
				.setTitle(title)
				.setPlanDateStart(planDateStart)
				.setPlanDateEnd(planDateEnd)
				.setMemberCount(memberCount)
				.setMember(member)
				.setPlan(plan)
				.setLikeCount(recommendCount)
				.build();
	}

	@Override
	public String toString() {
		return "Planner [plannerId=" + plannerId + ", accountId=" + accountId + ", title=" + title + ", planDateStart="
				+ planDateStart + ", planDateEnd=" + planDateEnd + ", memberCount=" + memberCount + ", member=" + member
				+ ", plan=" + plan + ", recommendCount=" + recommendCount + ", createDate=" + createDate
				+ ", updateDate=" + updateDate + "]";
	}
}
