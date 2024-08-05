package com.planner.planner.Mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.ActiveProfiles;

import com.planner.planner.Common.Notification.NotificationType;
import com.planner.planner.Dto.NotificationDto;

@ActiveProfiles("test")
@MybatisTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class NotificationMapperTest {
	@Autowired
	private NotificationMapper mapper;
	
	@DisplayName("알림 생성")
	@Test
	public void insertNotification() throws Exception {
		NotificationDto testNoti = NotificationDto.builder()
				.content("알림 생성 테스트")
				.link("")
				.isRead(false)
				.notificationType(NotificationType.OTHER)
				.build();
		
		mapper.insertNotification(1, testNoti);
		
		assertThat(testNoti.getId()).isEqualTo(2);
	}
	
	@DisplayName("알림아이디로 알림 가져오기")
	@Test
	public void findById() throws Exception {
		NotificationDto testNoti = NotificationDto.builder()
				.id(1)
				.accountId(1)
				.content("알림 테스트")
				.link("")
				.isRead(false)
				.notificationType(NotificationType.OTHER)
				.build();
		
		NotificationDto findNoti = mapper.findById(1);
		
		
		assertThat(findNoti).usingRecursiveComparison()
				.ignoringFields("createDate", "updateDate")
				.isEqualTo(testNoti);
	}
	
	@DisplayName("알림아이디로 모든 알림 가져오기")
	@Test
	public void findAll() throws Exception {
		List<NotificationDto> testNotiList = Arrays.asList(
				NotificationDto.builder()
				.id(1)
				.accountId(1)
				.content("알림 테스트")
				.link("")
				.isRead(false)
				.notificationType(NotificationType.OTHER)
				.build()
				);
		
		List<NotificationDto> findNotiList = mapper.findAllByAccountId(1);
		
		assertThat(findNotiList).usingRecursiveComparison()
				.ignoringFields("createDate", "updateDate")
				.isEqualTo(testNotiList);
	}
	
	@DisplayName("알림 읽기")
	@Test
	public void readNotification() throws Exception {
		int result = mapper.readNotification(1, true);
		
		assertThat(result).isEqualTo(1);
	}
	
	@DisplayName("알림 삭제")
	@Test
	public void deleteNotification() throws Exception {
		int result = mapper.deleteNotification(1);
		
		assertThat(result).isEqualTo(1);
	}
}
