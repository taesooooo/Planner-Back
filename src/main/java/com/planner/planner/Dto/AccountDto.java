package com.planner.planner.Dto;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.planner.planner.Entity.Account;

@JsonInclude(value = Include.NON_NULL)
public class AccountDto {
	
	private int accountId;
	private String email;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	@JsonProperty("username")
	private String userName;
	@JsonProperty("nickname")
	private String nickName;
	
	private String image;
	
	public static class Builder {
		private int accountId;
		private String email;
		private String password;
		private String userName;
		private String nickName;
		private String image;
		
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
		
		public Builder setUserName(String userName) {
			this.userName = userName;
			return this;
		}
		
		public Builder setNickName(String nickName) {
			this.nickName = nickName;
			return this;
		}

		public Builder setImage(String image) {
			this.image = image;
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
		this.userName = builder.userName;
		this.nickName = builder.nickName;
		this.image = builder.image;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Account toEntity() {
		return new Account.Builder().setAccountId(accountId).setEmail(email).setPassword(password).setUserName(userName).setNickName(nickName).setImage(image).build();
	}

	@Override
	public String toString() {
		return "AccountDto [accountId=" + accountId + ", email=" + email + ", userName="
				+ userName + ", nickName=" + nickName + ", image=" + image + "]";
	}
}
