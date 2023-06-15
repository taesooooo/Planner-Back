package com.planner.planner.Dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class PlannerDto {
	private int plannerId;
	private int accountId;
	private String creator;
	private String title;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate planDateStart;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate planDateEnd;
	private List<String> planMembers;
	private int expense;
	private int memberCount;
	private int memberTypeId;
	private int likeCount;
	private boolean likeState;
	private List<PlanMemoDto> planMemos;
	private List<PlanDto> plans;
	@JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
	private LocalDateTime createDate;
	@JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
	private LocalDateTime updateDate;
	

	public static class Builder {
		private int plannerId;
		private int accountId;
		private String creator;
		private String title;
		private LocalDate planDateStart;
		private LocalDate planDateEnd;
		private List<String> planMembers;
		private int expense;
		private int memberCount;
		private int memberTypeId;
		private int likeCount;
		private boolean likeState;
		private List<PlanMemoDto> planMemos;
		private List<PlanDto> plans;
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

		public Builder setCreator(String creator) {
			this.creator = creator;
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

		public Builder setExpense(int expense) {
			this.expense = expense;
			return this;
		}

		public Builder setMemberCount(int memberCount) {
			this.memberCount = memberCount;
			return this;
		}

		public Builder setMemberTypeId(int memberTypeId) {
			this.memberTypeId = memberTypeId;
			return this;
		}

		public Builder setPlanMembers(List<String> planMembers) {
			this.planMembers = planMembers;
			return this;
		}

		public Builder setLikeCount(int likeCount) {
			this.likeCount = likeCount;
			return this;
		}
		
		public Builder setLikeState(boolean likeState) {
			this.likeState = likeState;
			return this;
		}

		public Builder setPlanMemos(List<PlanMemoDto> planMemos) {
			this.planMemos = planMemos;
			return this;
		}

		public Builder setPlans(List<PlanDto> plans) {
			this.plans = plans;
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
		this.creator = builder.creator;
		this.title = builder.title;
		this.planDateStart = builder.planDateStart;
		this.planDateEnd = builder.planDateEnd;
		this.planMembers = builder.planMembers;
		this.expense = builder.expense;
		this.memberCount = builder.memberCount;
		this.memberTypeId = builder.memberTypeId;
		this.likeCount = builder.likeCount;
		this.likeState = builder.likeState;
		this.planMemos = builder.planMemos;
		this.plans = builder.plans;
		this.createDate = builder.createDate;
		this.updateDate = builder.updateDate;
	}

	public int getPlannerId() {
		return plannerId;
	}

	public int getAccountId() {
		return accountId;
	}

	public String getCreator() {
		return creator;
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

	public List<String> getPlanMembers() {
		return planMembers;
	}

	public int getExpense() {
		return expense;
	}

	public int getMemberCount() {
		return memberCount;
	}

	public int getMemberTypeId() {
		return memberTypeId;
	}

	public int getLikeCount() {
		return likeCount;
	}
	
	public boolean isLikeState() {
		return likeState;
	}

	public List<PlanMemoDto> getPlanMemos() {
		return planMemos;
	}

	public List<PlanDto> getPlans() {
		return plans;
	}
	
	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}
	
}
