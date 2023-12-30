package com.planner.planner.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class LoginInfoDto {
	private AccountDto user;
	private String accessToken;
	@JsonIgnore
	private String reflashToken;
	
	public static class Builder {
		private AccountDto user;
		private String accessToken;
		private String reflashToken;
		
		public Builder setUser(AccountDto user) {
			this.user = user;
			return this;
		}
		public Builder setAccessToken(String accessToken) {
			this.accessToken = accessToken;
			return this;
		}
		public Builder setReflashToken(String reflashToken) {
			this.reflashToken = reflashToken;
			return this;
		}
		
		public LoginInfoDto build() {
			return new LoginInfoDto(this);
		}
	}
	
	public LoginInfoDto() {
		
	}
	
	public LoginInfoDto(Builder builder) {
		this.user = builder.user;
		this.accessToken = builder.accessToken;
		this.reflashToken = builder.reflashToken;
	}

	public AccountDto getUser() {
		return user;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getReflashToken() {
		return reflashToken;
	}
	
	
}
