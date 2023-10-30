package com.planner.planner.Service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.planner.planner.Dao.NotificationDao;
import com.planner.planner.Dto.NotificationDto;
import com.planner.planner.Exception.NotificationNotFoundException;
import com.planner.planner.Service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {
	private NotificationDao notificationDao;
	
	public NotificationServiceImpl(NotificationDao notificationDao) {
		this.notificationDao = notificationDao;
	}

	@Override
	public void createNotification(int accountId, NotificationDto notificationDto) throws Exception{
		notificationDao.createNotification(accountId, notificationDto);
	}

	@Override
	public NotificationDto findById(int notificationId) throws Exception {
		NotificationDto notification = notificationDao.findById(notificationId);
		if(notification == null) {
			throw new NotificationNotFoundException("해당하는 알림을 찾을 수 없습니다.");
		}
		
		return notification;
	}

	@Override
	public List<NotificationDto> findAllByAccountId(int accountId) throws Exception{
		List<NotificationDto> list = notificationDao.findAllByAccountId(accountId);
		if(list == null) {
			list = new ArrayList<NotificationDto>();
		}
		
		return list;
	}

	@Override
	public void notificationRead(int notificationId) throws Exception{
		notificationDao.updateRead(notificationId, true);
	}

	@Override
	public void deleteNotification(int notificationId) throws Exception{
		notificationDao.deleteNotification(notificationId);
	}

}
