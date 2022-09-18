package com.planner.planner.Entity;

import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.AccountDto.Builder;

public class Account {
	private int accountId;
	private String email;
	private String password;
	private String userName;
	private String nickName;
	private String image;
	private LocalDateTime createDate;
	private LocalDateTime updateDate;

	public static class Builder {
		private int accountId;
		private String email;
		private String password;
		private String userName;
		private String nickName;
		private String image;
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
		
		public Builder setUserName(String userName) {
			this.userName = userName;
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
		
		public Builder setImage(String image) {
			this.image = image;
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
		this.userName = builder.userName;
		this.nickName = builder.nickName;
		this.image = builder.image;
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

	public String getUserName() {
		return userName;
	}

	public String getNickName() {
		return nickName;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public AccountDto toDto() {
		return new AccountDto.Builder().setAccountId(accountId).setEmail(email).setPassword(password).setUserName(userName).setNickName(nickName).setImage(image).build();
	}
	
	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", email=" + email + ", password=" + password + ", userName="
				+ userName + ", nickName=" + nickName + ", image=" + image + ", createDate=" + createDate
				+ ", updateDate=" + updateDate + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(accountId, createDate, email, image, nickName, password, updateDate, userName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		return accountId == other.accountId && Objects.equals(createDate, other.createDate)
				&& Objects.equals(email, other.email) && Objects.equals(image, other.image)
				&& Objects.equals(nickName, other.nickName) && Objects.equals(password, other.password)
				&& Objects.equals(updateDate, other.updateDate) && Objects.equals(userName, other.userName);
	}
	
}
