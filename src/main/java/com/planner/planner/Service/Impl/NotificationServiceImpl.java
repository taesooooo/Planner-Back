package com.planner.planner.Service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planner.planner.Dto.NotificationDto;
import com.planner.planner.Exception.NotificationNotFoundException;
import com.planner.planner.Mapper.NotificationMapper;
import com.planner.planner.Service.NotificationService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
	private final NotificationMapper notificationMapper;

	@Override
	public void createNotification(int accountId, NotificationDto notificationDto) throws Exception{
		notificationMapper.insertNotification(accountId, notificationDto);
	}

	@Override
	public NotificationDto findById(int notificationId) throws Exception {
		NotificationDto notification = notificationMapper.findById(notificationId);
		if(notification == null) {
			throw new NotificationNotFoundException("해당하는 알림을 찾을 수 없습니다.");
		}
		
		return notification;
	}

	@Override
	public List<NotificationDto> findAllByAccountId(int accountId) throws Exception{
		List<NotificationDto> list = notificationMapper.findAllByAccountId(accountId);
		if(list == null) {
			list = new ArrayList<NotificationDto>();
		}
		
		return list;
	}

	@Override
	public void notificationRead(int notificationId) throws Exception{
		notificationMapper.readNotification(notificationId, true);
	}

	@Override
	public void deleteNotification(int notificationId) throws Exception{
		notificationMapper.deleteNotification(notificationId);
	}

}
