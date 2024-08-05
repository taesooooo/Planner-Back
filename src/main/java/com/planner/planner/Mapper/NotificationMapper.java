package com.planner.planner.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.planner.planner.Common.Security.UserIdentifierDao;
import com.planner.planner.Dto.NotificationDto;

@Mapper
public interface NotificationMapper extends UserIdentifierDao {
	public void insertNotification(int accountId, NotificationDto notificationDto) throws Exception;

	public NotificationDto findById(int notificationId) throws Exception;

	public List<NotificationDto> findAllByAccountId(int accountId) throws Exception;

	public int readNotification(int notificationId, boolean state) throws Exception;

	public int deleteNotification(int notificationId) throws Exception;
}
