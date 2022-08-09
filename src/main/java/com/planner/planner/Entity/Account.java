package com.planner.planner.Entity;

import java.time.LocalDateTime;

import com.planner.planner.Dto.AccountDto;

public class Account {
	private int account_id;
	private String email;
	private String password;
	private String name;
	private String nickName;
	private LocalDateTime create_date;
	private LocalDateTime update_date;
	
	public Account(int id, String email, String password, String name, String nickName, LocalDateTime create_date, LocalDateTime update_date) {
		this.account_id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.nickName = nickName;
		this.create_date = create_date;
		this.update_date = update_date;
	}
	
	public Account(String email, String password, String name, String nickName, LocalDateTime update_date) {
		this.account_id = 0000;
		this.email = email;
		this.password = password;
		this.name = name;
		this.nickName = nickName;
		this.update_date = update_date;
	}
	
	public int getId() {
		return account_id;
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
	
	public LocalDateTime getCreate_date() {
		return create_date;
	}

	public void setCreate_date(LocalDateTime create_date) {
		this.create_date = create_date;
	}

	public LocalDateTime getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(LocalDateTime update_date) {
		this.update_date = update_date;
	}

	public AccountDto toDto() {
		return new AccountDto(email,password,name,nickName,update_date);
	}

	@Override
	public String toString() {
		return "Account [account_id=" + account_id + ", email=" + email + ", password=" + password + ", name=" + name
				+ ", nickName=" + nickName + ", create_date=" + create_date + ", update_date=" + update_date + "]";
	}
	
	
	
	
}
