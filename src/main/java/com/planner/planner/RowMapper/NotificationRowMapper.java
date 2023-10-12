package com.planner.planner.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.planner.planner.Common.Notification.NotificationType;
import com.planner.planner.Dto.NotificationDto;

public class NotificationRowMapper implements RowMapper<NotificationDto> {

	@Override
	public NotificationDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new NotificationDto.Builder()
				.setId(rs.getInt("id"))
				.setAccountId(rs.getInt("account_id"))
				.setContent(rs.getString("content"))
				.setLink(rs.getString("link"))
				.setRead(rs.getBoolean("state"))
				.setNotificationType(NotificationType.codeOf(rs.getInt("noti_type")))
				.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime())
				.setUpdateDate(rs.getTimestamp("update_date").toLocalDateTime())
				.build();
	}
	
}
