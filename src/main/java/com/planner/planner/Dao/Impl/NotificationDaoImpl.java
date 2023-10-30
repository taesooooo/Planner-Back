package com.planner.planner.Dao.Impl;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.planner.planner.Common.Notification.NotificationType;
import com.planner.planner.Dao.NotificationDao;
import com.planner.planner.Dto.NotificationDto;
import com.planner.planner.RowMapper.NotificationRowMapper;

@Repository
public class NotificationDaoImpl implements NotificationDao {
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	private final String INSERT_NOTIFICATION_SQL = "INSERT INTO notification(account_id, content, link, noti_type) VALUES (:accountId, :content, :link, :notificationType);";
	private final String FIND_BY_ID_SQL = "SELECT id, account_id, content, link, state, noti_type, create_date, update_date FROM notification WHERE id = :id;";
	private final String FIND_ALL_BY_ACCOUNT_ID_SQL = "SELECT id, account_id, content, link, state, noti_type, create_date, update_date FROM notification WHERE account_id = :accountId;";
	private final String UPDATE_STATE_SQL = "UPDATE notification SET state = :isRead, update_date = NOW() WHERE id = :id;";
	private final String DELETE_BY_ID_SQL = "DELETE FROM notification WHERE id = :id;";
	
	public NotificationDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public void createNotification(int accountId, NotificationDto notificationDto) throws Exception {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("accountId", accountId)
				.addValue("content", notificationDto.getContent())
				.addValue("link", notificationDto.getLink() == null ? "" : notificationDto.getLink())
				.addValue("notificationType", notificationDto.getNotificationType() == null ? NotificationType.OTHER.getCode() : notificationDto.getNotificationType().getCode());
		
		int result = namedParameterJdbcTemplate.update(INSERT_NOTIFICATION_SQL, parameterSource, keyHolder, new String[] {"id"});

	}

	@Override
	public NotificationDto findById(int notificationId) throws Exception {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("id", notificationId);
		
		NotificationDto notification = namedParameterJdbcTemplate.queryForObject(FIND_BY_ID_SQL, parameterSource, new NotificationRowMapper());
		
		return notification;
	}

	@Override
	public List<NotificationDto> findAllByAccountId(int accountId) throws Exception {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("accountId", accountId);
		
		List<NotificationDto> list = namedParameterJdbcTemplate.query(FIND_ALL_BY_ACCOUNT_ID_SQL, parameterSource, new NotificationRowMapper());
		
		return list;
	}

	@Override
	public void updateRead(int accountId, int notificationId) throws Exception {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("accountId", accountId)
				.addValue("id", notificationId)
				.addValue("isRead", true);
		
		int result = namedParameterJdbcTemplate.update(UPDATE_STATE_SQL, parameterSource);
	}

	@Override
	public void deleteNotification(int notificationId) throws Exception {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("id", notificationId);
		
		int result = namedParameterJdbcTemplate.update(DELETE_BY_ID_SQL, parameterSource);
	}
}
