package com.planner.planner.Dto;

import java.time.LocalDateTime;

public class InvitationDto {
	private int id;
	private int accountId;
	private int plannerId;
	private LocalDateTime inviteDate;
	private LocalDateTime expireDate;
	
	public static class Builder {
		private int id;
		private int accountId;
		private int plannerId;
		private LocalDateTime inviteDate;
		private LocalDateTime expireDate;
		
		public Builder setId(int id) {
			this.id = id;
			return this;
		}
		public Builder setAccountId(int accountId) {
			this.accountId = accountId;
			return this;
		}		
		public Builder setPlannerId(int plannerId) {
			this.plannerId = plannerId;
			return this;					}
		public Builder setInviteDate(LocalDateTime inviteDate) {
			this.inviteDate = inviteDate;
			return this;
		}
		public Builder setExpireDate(LocalDateTime expireDate) {
			this.expireDate = expireDate;
			return this;
		}
		
		public InvitationDto build() {
			return new InvitationDto(this);
		}
	}
	
	public InvitationDto() {
		
	}

	public InvitationDto(Builder builder) {
		this.id = builder.id;
		this.accountId = builder.accountId;
		this.plannerId = builder.plannerId;
		this.inviteDate = builder.inviteDate;
		this.expireDate = builder.expireDate;
	}

	public int getId() {
		return id;
	}

	public int getAccountId() {
		return accountId;
	}

	public int getPlannerId() {
		return plannerId;
	}

	public LocalDateTime getInviteDate() {
		return inviteDate;
	}

	public LocalDateTime getExpireDate() {
		return expireDate;
	}
	
	
}
