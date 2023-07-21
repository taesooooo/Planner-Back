package com.planner.planner.Dto.SENS;

public class Messages {
	private String to;
	private String subject;
	private String content;

	public static class Builder {
		private String to;
		private String subject;
		private String content;

		public Builder setTo(String to) {
			this.to = to;
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

		public Messages build() {
			return new Messages(this);
		}
	}

	public Messages(Builder builder) {
		this.to = builder.to;
		this.subject = builder.subject;
		this.content = builder.content;
	}

	public String getTo() {
		return to;
	}

	public String getSubject() {
		return subject;
	}

	public String getContent() {
		return content;
	}
}
