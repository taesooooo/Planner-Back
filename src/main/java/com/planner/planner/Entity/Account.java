package com.planner.planner.Entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.AccountDto.Builder;

public class Account {
	private int accountId;
	private String email;
	private String password;
	private String name;
	private String nickName;
	private LocalDateTime createDate;
	private LocalDateTime updateDate;

	public static class Builder {
		private int accountId;
		private String email;
		private String password;
		private String name;
		private String nickName;
		private LocalDateTime createDate;
		private LocalDateTime updateDate;
		
		public Builder setAccountId(int accountId) {
			this.accountId = accountId;
			return this;
		}
		
		public Builder setEmail(String email) {
			this.email = email;
			return this;
		}
		
		public Builder setPassword(String password) {
			this.password = password;
			return this;
		}
		
		public Builder setName(String name) {
			this.name = name;
			return this;
		}
		
		public Builder setNickName(String nickName) {
			this.nickName = nickName;
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
		
		public Account build() {
			return new Account(this);
		}
	}
	
	public Account() {
		
	}
	
	public Account(Builder builder) {
		this.accountId = builder.accountId;
		this.email = builder.email;
		this.password = builder.password;
		this.name = builder.name;
		this.nickName = builder.nickName;
		this.createDate = builder.createDate;
		this.updateDate = builder.updateDate;
	}
	
	public int getAccountId() {
		return accountId;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public String getNickName() {
		return nickName;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public AccountDto toDto() {
		return new AccountDto.Builder().setAccountId(accountId).setEmail(email).setPassword(password).setName(name).setNickName(nickName).build();
	}

	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", email=" + email + ", password=" + password + ", name=" + name
				+ ", nickName=" + nickName + ", createDate=" + createDate + ", updateDate=" + updateDate + "]";
	}	
}
