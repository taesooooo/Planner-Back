package com.planner.planner.Dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.planner.planner.Common.Notification.NotificationType;

public class NotificationDto {
	private int id;
	private int accountId;
	private String content;
	private String link;
	private NotificationType notificationType;
	private boolean isRead;
	
	@JsonFormat(pattern = "YYYY-MM-dd hh:mm:ss")
	private LocalDateTime createDate;
	@JsonFormat(pattern = "YYYY-MM-dd hh:mm:ss")
	private LocalDateTime updateDate;
	
	public static class Builder {
		private int id;
		private int accountId;
		private String content;
		private String link;
		private NotificationType notificationType;
		private boolean isRead;
		private LocalDateTime createDate;
		private LocalDateTime updateDate;
		
		public Builder setId(int id) {
			this.id = id;
			return this;
		}
		public Builder setAccountId(int accountId) {
			this.accountId = accountId;
			return this;
		}
		public Builder setContent(String content) {
			this.content = content;
			return this;
		}
		public Builder setLink(String link) {
			this.link = link;
			return this;
		}
		public Builder setNotificationType(NotificationType notificationType) {
			this.notificationType = notificationType;
			return this;
		}
		public Builder setRead(boolean isRead) {
			this.isRead = isRead;
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
		
		public NotificationDto build() {
			return new NotificationDto(this);
		}
		
	}
	
	

	public NotificationDto() {
	}

	public NotificationDto(Builder builder) {
		this.id = builder.id;
		this.accountId = builder.accountId;
		this.content = builder.content;
		this.link = builder.link;
		this.notificationType = builder.notificationType;
		this.isRead = builder.isRead;
		this.createDate = builder.createDate;
		this.updateDate = builder.updateDate;
	}

	public int getId() {
		return id;
	}

	public int getAccountId() {
		return accountId;
	}

	public String getContent() {
		return content;
	}

	public String getLink() {
		return link;
	}

	public NotificationType getNotiType() {
		return notificationType;
	}

	public boolean isRead() {
		return isRead;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}
	
	
}
