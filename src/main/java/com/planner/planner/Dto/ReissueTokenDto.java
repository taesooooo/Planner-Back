package com.planner.planner.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ReissueTokenDto {
	private String accessToken;
	@JsonIgnore
	private String refreshToken;
	
	public static class Builder {
		private String accessToken;
		private String refreshToken;
		
		public Builder setAccessToken(String accessToken) {
			this.accessToken = accessToken;
			return this;
		}
		public Builder setRefreshToken(String refreshToken) {
			this.refreshToken = refreshToken;
			return this;
		}
		
		public ReissueTokenDto build() {
			return new ReissueTokenDto(this);
		}
	}
	
	public ReissueTokenDto() {
		
	}

	public ReissueTokenDto(Builder builder) {
		this.accessToken = builder.accessToken;
		this.refreshToken = builder.refreshToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}
	
}
