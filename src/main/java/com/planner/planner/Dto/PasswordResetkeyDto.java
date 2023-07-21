package com.planner.planner.Dto;

import java.time.LocalDateTime;

public class PasswordResetkeyDto {
	private int id;
	private String resetKey;
	private int accountId;
	private LocalDateTime expireDate;
	private LocalDateTime createDate;
	
	public static class Builder {
		private int id;
		private String resetKey;
		private int accountId;
		private LocalDateTime expireDate;
		private LocalDateTime createDate;
		
		public Builder setId(int id) {
			this.id = id;
			return this;
		}
		public Builder setResetKey(String resetKey) {
			this.resetKey = resetKey;
			return this;
		}
		public Builder setAccountId(int accountId) {
			this.accountId = accountId;
			return this;
		}
		public Builder setExpireDate(LocalDateTime expireDate) {
			this.expireDate = expireDate;
			return this;
		}
		public Builder setCreateDate(LocalDateTime createDate) {
			this.createDate = createDate;
			return this;
		}
		
		public PasswordResetkeyDto build() {
			return new PasswordResetkeyDto(this);
		}
	}
	
	public PasswordResetkeyDto(Builder builder) {
		this.id = builder.id;
		this.resetKey = builder.resetKey;
		this.accountId = builder.accountId;
		this.expireDate = builder.expireDate;
		this.createDate = builder.createDate;
	}


	public int getId() {
		return id;
	}


	public String getResetKey() {
		return resetKey;
	}


	public int getAccountId() {
		return accountId;
	}


	public LocalDateTime getExpireDate() {
		return expireDate;
	}


	public LocalDateTime getCreateDate() {
		return createDate;
	}
}
