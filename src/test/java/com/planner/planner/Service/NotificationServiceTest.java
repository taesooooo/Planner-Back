package com.planner.planner.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.planner.planner.Dao.NotificationDao;
import com.planner.planner.Dto.NotificationDto;
import com.planner.planner.Service.Impl.NotificationServiceImpl;

public class NotificationServiceTest {
	@Mock
	private NotificationDao notificationDao;
	
	@InjectMocks
	private NotificationServiceImpl notificationServiceImpl;
	
	@Before
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void 알림_모두_조회_없는경우() throws Exception {
		when(notificationDao.findAllByAccountId(anyInt())).thenReturn(null);
		
		List<NotificationDto> list = notificationServiceImpl.findAllByAccountId(1);
		
		assertThat(list).isEmpty();
	}
}
