package com.planner.planner.Entity;

import java.time.LocalDateTime;
import java.util.List;

public class Planner {
	private int plannerId;
	private int accountId;
	private String title;
	private LocalDateTime planDateStart;
	private LocalDateTime planDateEnd;
	private int likeCount = 0;
	private LocalDateTime createDate;
	private LocalDateTime updateDate;
	
	private List<Plan> plans;

	public static class Builder {
		private int plannerId = 0;
		private int accountId = 0;
		private String title;
		private LocalDateTime planDateStart;
		private LocalDateTime planDateEnd;
		private int likeCount = 0;
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
}
