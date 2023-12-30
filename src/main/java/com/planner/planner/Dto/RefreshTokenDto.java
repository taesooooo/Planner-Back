package com.planner.planner.Dto;

public class RefreshTokenDto {
	private int id;
	private String email;
	private String token;
	
	public static class Builder {
		private int id;
		private String email;
		private String token;
		
		public Builder setId(int id) {
			this.id = id;
			return this;
		}
		public Builder setEmail(String email) {
			this.email = email;
			return this;
		}
		public Builder setToken(String token) {
			this.token = token;
			return this;
		}
		
		public RefreshTokenDto build() {
			return new RefreshTokenDto(this);
		}
	}
	
	public RefreshTokenDto() {
		
	}

	public RefreshTokenDto(Builder builder) {
		this.id = builder.id;
		this.email = builder.email;
		this.token = builder.token;
	}

	public int getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getToken() {
		return token;
	}
	
	
}
