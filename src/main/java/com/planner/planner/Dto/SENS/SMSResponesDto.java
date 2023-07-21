package com.planner.planner.Dto.SENS;

import java.time.LocalDateTime;

public class SMSResponesDto {
	// SENS API docs
	//	requestId	Mandatory	String	요청 아이디	
	//	requestTime	Mandatory	DateTime	요청 시간	yyyy-MM-dd'T'HH:mm:ss.SSS
	//	statusCode	Mandatory	String	요청 상태 코드	- 202: 성공
	//	- 그 외: 실패
	//	- HTTP Status 규격을 따름
	//	statusName	Mandatory	String	요청 상태명	- success: 성공
	//	- fail: 실패
		
	private String requestId;
	private LocalDateTime requestTime;
	private String statusCode;
	private String statusName;
	
	public static class Builder {
		private String requestId;
		private LocalDateTime requestTime;
		private String statusCode;
		private String statusName;
		
		public Builder setRequestId(String requestId) {
			this.requestId = requestId;
			return this;
		}
		
		public Builder setRequestTime(LocalDateTime requestTime) {
			this.requestTime = requestTime;
			return this;
		}
		
		public Builder setStatusCode(String statusCode) {
			this.statusCode = statusCode;
			return this;
		}
		
		public Builder setStatusName(String statusName) {
			this.statusName = statusName;
			return this;
		}
		
		public SMSResponesDto build() {
			return new SMSResponesDto(this);
		}
		
	}
	
	
	public SMSResponesDto() {
	}

	public SMSResponesDto(Builder builder) {
		this.requestId = builder.requestId;
		this.requestTime = builder.requestTime;
		this.statusCode = builder.statusCode;
		this.statusName = builder.statusName;
	}

	public String getRequestId() {
		return requestId;
	}

	public LocalDateTime getRequestTime() {
		return requestTime;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public String getStatusName() {
		return statusName;
	}
}
