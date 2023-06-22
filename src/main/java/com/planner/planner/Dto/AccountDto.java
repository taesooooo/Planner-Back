package com.planner.planner.Dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.planner.planner.Common.ValidationGroups.AccountUpdateGroup;
import com.planner.planner.Common.ValidationGroups.LoginGroup;
import com.planner.planner.Common.ValidationGroups.RegisterGroup;

@JsonInclude(value = Include.NON_NULL)
public class AccountDto {

	private int accountId;
	@NotBlank(message = "이메일은 필수 항목입니다.", groups = { RegisterGroup.class, LoginGroup.class })
	@Email(message = "이메일 형식이 아닙니다.", groups = { RegisterGroup.class, LoginGroup.class })
	private String email;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@NotBlank(message = "비밀번호는 필수 항목입니다.", groups = { RegisterGroup.class, LoginGroup.class })
	private String password;
	
	@JsonProperty("username")
	@NotBlank(message = "이름은 필수 항목입니다.", groups = RegisterGroup.class)
	private String userName;
	
	@JsonProperty("nickname")
	@NotBlank(message = "닉네임은 필수 항목입니다.", groups = { RegisterGroup.class, AccountUpdateGroup.class })
	private String nickName;
	
	@Size(min = 11, max = 11, message = "휴대폰 번호를 다시 작성해주세요.", groups = { RegisterGroup.class, AccountUpdateGroup.class })
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
