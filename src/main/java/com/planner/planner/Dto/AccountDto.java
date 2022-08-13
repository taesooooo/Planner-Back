package com.planner.planner.Dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.planner.planner.Entity.Account;

public class AccountDto {
	
	private int accountId;
	private String email;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	private String name;
	private String nickName;
	
	public static class Builder {
		private int accountId;
		private String email;
		private String password;
		private String name;
		private String nickName;
		
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
		
		public AccountDto build() {
			return new AccountDto(this);
		}
	}
	
	public AccountDto() {
		
	}
	
	public AccountDto(Builder builder) {
		this.accountId = builder.accountId;
		this.email = builder.email;
		this.password = builder.password;
		this.name = builder.name;
		this.nickName = builder.nickName;
	}
	
	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Account toEntity() {
		return new Account.Builder().setAccountId(accountId).setEmail(email).setPassword(password).setName(name).setNickName(nickName).build();
	}

	@Override
	public String toString() {
		return "AccountDto [accountId=" + accountId + ", email=" + email + ", password=" + password + ", name=" + name
				+ ", nickName=" + nickName + "]";
	}
}
