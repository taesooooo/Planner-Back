package com.planner.planner.Dto;

public class FindEmailDto {
	private String username;
	private String phone;
	private String email;
	
	public static class Builder {
		private String username;
		private String phone;
		private String email;
		
		public Builder setUsername(String username) {
			this.username = username;
			return this;
		}
		public Builder setPhone(String phone) {
			this.phone = phone;
			return this;
		}
		public Builder setEmail(String email) {
			this.email = email;
			return this;
		}
		
		public FindEmailDto build() {
			return new FindEmailDto(this);
		}
	}

	public FindEmailDto(Builder builder) {
		this.username = builder.username;
		this.phone = builder.phone;
		this.email = builder.email;
	}

	public String getUsername() {
		return username;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}
}
