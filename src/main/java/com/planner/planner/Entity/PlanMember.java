package com.planner.planner.Entity;

public class PlanMember {
	private int planMemberId;
	private int plannerId;
	private int accountId;
	
	public static class Builder {
		private int planMemberId;
		private int plannerId;
		private int accountId;
		
		public Builder setPlanMemberId(int planMemberId) {
			this.planMemberId = planMemberId;
			return this;
		}
		public Builder setPlannerId(int plannerId) {
			this.plannerId = plannerId;
			return this;
		}
		public Builder setAccountId(int accountId) {
			this.accountId = accountId;
			return this;
		}
		public PlanMember build() {
			return new PlanMember(this);
		}
	}

	public PlanMember(Builder builder) {
		this.planMemberId = builder.planMemberId;
		this.plannerId = builder.plannerId;
		this.accountId = builder.accountId;
	}

	public int getPlanMemberId() {
		return planMemberId;
	}

	public int getPlannerId() {
		return plannerId;
	}

	public int getAccountId() {
		return accountId;
	}
}
