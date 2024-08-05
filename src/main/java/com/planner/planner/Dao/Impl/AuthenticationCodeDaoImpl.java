package com.planner.planner.Dao.Impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.planner.planner.Dao.AuthenticationCodeDao;
import com.planner.planner.Dto.AuthenticationCodeDto;
import com.planner.planner.RowMapper.AuthenticationCodeRowMapper;

@Repository
public class AuthenticationCodeDaoImpl implements AuthenticationCodeDao {
	
	private JdbcTemplate jdbcTemplate;
	
	private final String INSERT_BY_PHONE_SQL = "INSERT INTO authentication_code(phone, code) VALUES (?, ?);";
	private final String INSERT_BY_EMAIL_SQL = "INSERT INTO authentication_code(email, code) VALUES (?, ?);";
	private final String FIND_BY_PHONE_SQL = "SELECT id, phone, email, code, code_confirm, expire_date, create_date "
			+ "FROM authentication_code "
			+ "WHERE phone = ? AND create_date = (SELECT MAX(create_date) FROM authentication_code WHERE phone = ?);";
	private final String FIND_BY_EMAIL_SQL = "SELECT id, phone, email, code, code_confirm, expire_date, create_date "
			+ "FROM authentication_code "
			+ "WHERE email = ? AND create_date = (SELECT MAX(create_date) FROM authentication_code WHERE email = ?);";
	private final String UPDATE_CODE_CONFIRM_BY_PHONE_SQL = "UPDATE authentication_code SET code_confirm = TRUE WHERE phone = ?;";
	private final String UPDATE_CODE_CONFIRM_BY_EMAIL_SQL = "UPDATE authentication_code SET code_confirm = TRUE WHERE email = ?;";
	private final String DELETE_SQL = "DELETE FROM authentication_code WHERE phone = ?;";

	public AuthenticationCodeDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public boolean createByPhone(String phone, String code) {
		int result = this.jdbcTemplate.update(INSERT_BY_PHONE_SQL, phone, code);
		
		return result > 0 ? true : false;
	}
	
	@Override
	public boolean createByEmail(String email, String code) {
		int result = this.jdbcTemplate.update(INSERT_BY_EMAIL_SQL, email, code);
		
		return result > 0 ? true : false;
	}

	@Override
	public AuthenticationCodeDto findByPhone(String phone) {
		try {
			AuthenticationCodeDto dto = this.jdbcTemplate.queryForObject(FIND_BY_PHONE_SQL, new AuthenticationCodeRowMapper(), phone, phone);
			return dto;			
		}
		catch(EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public AuthenticationCodeDto findByEmail(String email) {
		try {
			AuthenticationCodeDto dto = this.jdbcTemplate.queryForObject(FIND_BY_EMAIL_SQL, new AuthenticationCodeRowMapper(), email, email);
			return dto;			
		}
		catch(EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public boolean updateCodeConfirmByPhone(String phone) {
		int result = this.jdbcTemplate.update(UPDATE_CODE_CONFIRM_BY_PHONE_SQL, phone);
		
		return result > 0 ? true : false;
	}

	@Override
	public boolean updateCodeConfirmByEmail(String email) {
		int result = this.jdbcTemplate.update(UPDATE_CODE_CONFIRM_BY_EMAIL_SQL, email);
		
		return result > 0 ? true : false;
	}

	@Override
	public void deleteByPhone(String phone) {
		this.jdbcTemplate.update(DELETE_SQL, phone);
	}

}
