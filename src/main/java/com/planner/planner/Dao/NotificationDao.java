package com.planner.planner.Dao;

import java.util.List;

import com.planner.planner.Dto.NotificationDto;

public interface NotificationDao {
	public void createNotification(int accountId, NotificationDto notificationDto) throws Exception;
	public List<NotificationDto> findAllByAccountId(int accountId) throws Exception;
	public void updateRead(int accountId, int notificationId) throws Exception;
	public void deleteNotification(int accountId, int notificationId) throws Exception;
}
