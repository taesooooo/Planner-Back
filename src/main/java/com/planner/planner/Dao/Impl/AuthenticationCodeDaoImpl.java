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
	
	private final String INSERT_SQL = "INSERT INTO authentication_code(phone, code) VALUES (?, ?);";
	private final String FIND_SQL = "SELECT id, phone, code, create_date "
			+ "FROM authentication_code "
			+ "WHERE phone = ? AND create_date = (SELECT MAX(create_date) FROM authentication_code WHERE phone = ?);";
	private final String DELETE_SQL = "DELETE FROM authentication_code WHERE phone = ?;";

	public AuthenticationCodeDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public boolean insert(String phone, String code) {
		int result = this.jdbcTemplate.update(INSERT_SQL, phone, code);
		
		return result > 0 ? true : false;
	}

	@Override
	public AuthenticationCodeDto find(String phone) {
		try {
			AuthenticationCodeDto dto = this.jdbcTemplate.queryForObject(FIND_SQL, new AuthenticationCodeRowMapper(), phone, phone);
			return dto;			
		}
		catch(EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public void delete(String phone) {
		this.jdbcTemplate.update(DELETE_SQL, phone);
	}

}
