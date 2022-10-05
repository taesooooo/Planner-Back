package com.planner.planner.Dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.planner.planner.Entity.Planner;

@JsonInclude(value = Include.NON_NULL)
public class PlannerDto {
	private int plannerId;
	@JsonProperty(access = Access.WRITE_ONLY)
	private int accountId;
	private String title;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate planDateStart;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate planDateEnd;
	private int memberCount = 1;
	private String member;
	private String plan;
	private int likeCount;
	private LocalDateTime createDate;
	private LocalDateTime updateDate;

	public static class Builder {
		private int plannerId;
		private int accountId;
		private String title;
		private LocalDate planDateStart;
		private LocalDate planDateEnd;
		private int memberCount = 1;
		private String member;
		private String plan;
		private int likeCount;
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

		public Builder setLikeCount(int recommendCount) {
			this.likeCount = recommendCount;
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
		this.memberCount = builder.memberCount;
		this.member = builder.member;
		this.plan = builder.plan;
		this.likeCount = builder.likeCount;
		this.createDate = builder.createDate;
		this.updateDate = builder.updateDate;
	}

	public int getPlannerId() {
		return plannerId;
	}

	public void setPlannerId(int plannerId) {
		this.plannerId = plannerId;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getPlanDateStart() {
		return planDateStart;
	}

	public void setPlanDateStart(LocalDate planDateStart) {
		this.planDateStart = planDateStart;
	}

	public LocalDate getPlanDateEnd() {
		return planDateEnd;
	}

	public void setPlanDateEnd(LocalDate planDateEnd) {
		this.planDateEnd = planDateEnd;
	}

	public int getMemberCount() {
		return memberCount;
	}

	public void setMemberCount(int memberCount) {
		this.memberCount = memberCount;
	}

	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int recommendCount) {
		this.likeCount = recommendCount;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}

	public Planner toEntity() {
		return new Planner.Builder()
				.setPlannerId(plannerId)
				.setAccountId(accountId)
				.setTitle(title)
				.setPlanDateStart(planDateStart)
				.setPlanDateEnd(planDateEnd)
				.setMemberCount(memberCount)
				.setMember(member)
				.setPlan(plan)
				.setRecommendCount(likeCount)
				.setCreateDate(createDate)
				.setUpdateDate(updateDate)
				.build();
	}

	@Override
	public String toString() {
		return "PlannerDto [plannerId=" + plannerId + ", accountId=" + accountId + ", title=" + title
				+ ", planDateStart=" + planDateStart + ", planDateEnd=" + planDateEnd + ", memberCount=" + memberCount
				+ ", member=" + member + ", plan=" + plan + ", recommendCount=" + likeCount + ", createDate="
				+ createDate + ", updateDate=" + updateDate + "]";
	}
}
