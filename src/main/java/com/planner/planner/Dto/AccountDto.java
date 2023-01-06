package com.planner.planner.Dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

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
	private String phone;
	private String image;
	private LocalDateTime createDate;
	private LocalDateTime updateDate;

	public static class Builder {
		private int accountId;
		private String email;
		private String password;
		private String userName;
		private String nickName;
		private String phone;
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

		public Builder setImage(String image) {
			this.image = image;
			return this;
		}

		public Builder setPhone(String phone) {
			this.phone = phone;
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
		this.phone = builder.phone;
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

	public String getPhone() {
		return phone;
	}

	public String getImage() {
		return image;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	@Override
	public String toString() {
		return "AccountDto [accountId=" + accountId + ", email=" + email + ", userName=" + userName + ", nickName="
				+ nickName + ", phone=" + phone + ", image=" + image + "]";
	}
}
