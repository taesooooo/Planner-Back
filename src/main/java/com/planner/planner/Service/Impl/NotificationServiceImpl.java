package com.planner.planner.Service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.planner.planner.Dao.NotificationDao;
import com.planner.planner.Dto.NotificationDto;
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
	public List<NotificationDto> findAllByAccountId(int accountId) throws Exception{
		List<NotificationDto> list = notificationDao.findAllByAccountId(accountId);
		if(list == null) {
			list = new ArrayList<NotificationDto>();
		}
		
		return list;
	}

	@Override
	public void notificationRead(int accountId, int notificationId) throws Exception{
		notificationDao.updateRead(accountId, notificationId);
	}

	@Override
	public void deleteAllNotification(int accountId) throws Exception{
		notificationDao.deleteNotification(accountId, accountId);
	}

}
