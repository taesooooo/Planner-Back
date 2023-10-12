package com.planner.planner.Service;

import java.util.List;

import com.planner.planner.Dto.NotificationDto;

public interface NotificationService {
	public void createNotification(int accountId, NotificationDto notificationDto) throws Exception;
	public List<NotificationDto> findAllByAccountId(int accountId) throws Exception;
	public void notificationRead(int accountId, int notificationId) throws Exception;
	public void deleteAllNotification(int accountId) throws Exception;
}
