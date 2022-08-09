package com.planner.planner.Dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.planner.planner.Entity.Account;

public class AccountDto {
	
	private String email;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	private String name;
	private String nickName;
	private LocalDateTime update_date;
	
	public AccountDto() {
		
	}

	public AccountDto(String email, String password, String name, String nickName,LocalDateTime update_date) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.nickName = nickName;
		this.update_date = update_date;
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

	public LocalDateTime getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(LocalDateTime update_date) {
		this.update_date = update_date;
	}

	public Account toEntity() {
		return new Account(email, password, name, nickName, update_date);
	}

	@Override
	public String toString() {
		return "AccountDto [email=" + email + ", password=" + password + ", name=" + name + ", nickName=" + nickName
				+ ", update_date=" + update_date + "]";
	}
	
	
	
	
}
