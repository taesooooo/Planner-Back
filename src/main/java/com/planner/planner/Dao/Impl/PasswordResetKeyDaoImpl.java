package com.planner.planner.Dao.Impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.planner.planner.Dao.PasswordResetKeyDao;
import com.planner.planner.Dto.PasswordResetkeyDto;
import com.planner.planner.RowMapper.PasswordResetKeyRowMapper;

@Repository
public class PasswordResetKeyDaoImpl implements PasswordResetKeyDao {
	
	private JdbcTemplate jdbcTemplate;
	
	private final String CREATE_RESET_KEY_SQL = "INSERT INTO password_reset_key(reset_key, account_id, expire_date) VALUES (?, ?, DATE_ADD(NOW(), INTERVAL 1 DAY));";
	private final String FIND_BY_RESET_KEY_SQL = "SELECT id, reset_key, account_id, expire_date, create_date FROM password_reset_key WHERE reset_key = ?;";
	private final String DELETE_BY_RESEY_KEY_SQL = "DELETE FROM password_reset_key WHERE reset_key = ?;";

	public PasswordResetKeyDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void createPasswordResetKey(String key, int accountId) {
		this.jdbcTemplate.update(CREATE_RESET_KEY_SQL, key, accountId);
	}

	@Override
	public PasswordResetkeyDto findByResetKey(String key) {
		try {
			PasswordResetkeyDto dto = this.jdbcTemplate.queryForObject(FIND_BY_RESET_KEY_SQL, new PasswordResetKeyRowMapper(), key);			
			return dto;
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public void deleteByResetKey(String Key) {
		this.jdbcTemplate.update(DELETE_BY_RESEY_KEY_SQL);
	}

}
