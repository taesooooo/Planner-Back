package com.planner.planner.Common.Notification;

import com.fasterxml.jackson.annotation.JsonValue;

public enum NotificationType {
	OTHER(1, "기타"),
	ACCOUNT(2, "계정"),
	NOTICE(3,"공지"),
	PLANNER_INVITE(4, "플래너 초대"),
	COMMENT(5, "댓글");
	
	private int code;
	private String type;
	
	private NotificationType(int code, String type) {
		this.code = code;
		this.type = type;
	}
	
	@JsonValue
	public int getCode() {
		return code;
	}

	public String getType() {
		return type;
	}
	
	public static NotificationType codeOf(int code) {
		for(NotificationType itme : NotificationType.values()) {
			if(itme.getCode() == code) {
				return itme;
			}
		}
		
		throw new IllegalArgumentException("잘못된 유형입니다.");
	}
	
	public static NotificationType typeOf(String type) {
		for(NotificationType itme : NotificationType.values()) {
			if(itme.getType().equals(type)) {
				return itme;
			}
		}
		
		throw new IllegalArgumentException("잘못된 유형입니다.");
	}
}
