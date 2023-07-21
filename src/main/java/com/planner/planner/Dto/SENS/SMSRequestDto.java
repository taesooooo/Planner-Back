package com.planner.planner.Dto.SENS;

import java.util.List;

public class SMSRequestDto {
	// SENS API docs
	// 필드는 docs에서 확인

	private String type;
	private String contentType;
	private String countryCode;
	private String from;
	private String subject;
	private String content;
	private List<Messages> messages;

	public static class Builder {
		private String type;
		private String contentType;
		private String countryCode;
		private String from;
		private String subject;
		private String content;
		private List<Messages> messages;

		public Builder setType(String type) {
			this.type = type;
			return this;
		}

		public Builder setContentType(String contentType) {
			this.contentType = contentType;
			return this;
		}

		public Builder setCountryCode(String countryCode) {
			this.countryCode = countryCode;
			return this;
		}

		public Builder setFrom(String from) {
			this.from = from;
			return this;
		}

		public Builder setSubject(String subject) {
			this.subject = subject;
			return this;
		}

		public Builder setContent(String content) {
			this.content = content;
			return this;
		}

		public Builder setMessages(List<Messages> messages) {
			this.messages = messages;
			return this;
		}
		
		public SMSRequestDto build() {
			return new SMSRequestDto(this);
		}
	}

	public SMSRequestDto(Builder builder) {
		this.type = builder.type;
		this.contentType = builder.contentType;
		this.countryCode = builder.countryCode;
		this.from = builder.from;
		this.subject = builder.subject;
		this.content = builder.content;
		this.messages = builder.messages;
	}

	public String getType() {
		return type;
	}

	public String getContentType() {
		return contentType;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public String getFrom() {
		return from;
	}

	public String getSubject() {
		return subject;
	}

	public String getContent() {
		return content;
	}

	public List<Messages> getMessages() {
		return messages;
	}

}
