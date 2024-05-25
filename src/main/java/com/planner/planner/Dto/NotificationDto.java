package com.planner.planner.Dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.planner.planner.Common.Notification.NotificationType;
import com.planner.planner.Common.Security.UserIdentifier;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class NotificationDto implements UserIdentifier {
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
}
