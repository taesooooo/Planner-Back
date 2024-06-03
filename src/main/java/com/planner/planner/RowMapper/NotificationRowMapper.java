package com.planner.planner.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.planner.planner.Common.Notification.NotificationType;
import com.planner.planner.Dto.NotificationDto;

public class NotificationRowMapper implements RowMapper<NotificationDto> {

	@Override
	public NotificationDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		return NotificationDto.builder()
				.id(rs.getInt("id"))
				.accountId(rs.getInt("account_id"))
				.content(rs.getString("content"))
				.link(rs.getString("link"))
				.isRead(rs.getBoolean("state"))
				.notificationType(NotificationType.codeOf(rs.getInt("noti_type")))
				.createDate(rs.getTimestamp("create_date").toLocalDateTime())
				.updateDate(rs.getTimestamp("update_date").toLocalDateTime())
				.build();
	}
	
}
