package com.planner.planner.Dao;

import java.util.List;

import com.planner.planner.Common.Security.UserIdentifierDao;
import com.planner.planner.Dto.NotificationDto;

public interface NotificationDao extends UserIdentifierDao {
	public void createNotification(int accountId, NotificationDto notificationDto) throws Exception;
	public NotificationDto findById(int notificationId) throws Exception;
	public List<NotificationDto> findAllByAccountId(int accountId) throws Exception;
	public void updateRead(int notificationId, boolean state) throws Exception;
	public void deleteNotification(int notificationId) throws Exception;
}
