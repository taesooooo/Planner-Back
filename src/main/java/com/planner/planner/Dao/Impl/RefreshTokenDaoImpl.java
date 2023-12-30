package com.planner.planner.Dao.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.planner.planner.Dao.RefreshTokenDao;
import com.planner.planner.Dto.RefreshTokenDto;
import com.planner.planner.RowMapper.ReflashTokenRowMapper;

@Repository
public class RefreshTokenDaoImpl implements RefreshTokenDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(RefreshTokenDaoImpl.class);

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	private final String INSERT_REFRESH_TOKEN_SQL = "INSERT INTO refresh_token(email, token) VALUES (:email, :token);";
	private final String FIND_BY_EMAIL_SQL = "SELECT id, email, token FROM refresh_token WHERE email = :email;";
	private final String FIND_BY_TOKEN_SQL = "SELECT id, email, token FROM refresh_token WHERE token = :token;";
	private final String UPDATE_BY_EMAIL_SQL = "UPDATE refresh_token SET token = :token WHERE email = :email;";
	private final String DELETE_BY_EMAIL_SQL = "DELETE FROM refresh_token WHERE email = :email;";
	
	public RefreshTokenDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public boolean create(String email, String token) throws Exception {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("email", email)
				.addValue("token", token);
		
		int result = namedParameterJdbcTemplate.update(INSERT_REFRESH_TOKEN_SQL, parameterSource);
		return result > 0 ? true : false;
	}

	@Override
	public RefreshTokenDto findByEmail(String email) throws Exception {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("email", email);
		
		try {
			return namedParameterJdbcTemplate.queryForObject(FIND_BY_EMAIL_SQL, parameterSource, new ReflashTokenRowMapper());
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public RefreshTokenDto findByToken(String token) throws Exception {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("token", token);
		
		try {
			return namedParameterJdbcTemplate.queryForObject(FIND_BY_TOKEN_SQL, parameterSource, new ReflashTokenRowMapper());
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public boolean update(String email, String token) throws Exception {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("email", email)
				.addValue("token", token);
		
		int result = namedParameterJdbcTemplate.update(UPDATE_BY_EMAIL_SQL, parameterSource);
		
		return result > 0 ? true : false;
	}

	@Override
	public boolean delete(String email) throws Exception {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("email", email);
		
		int result = namedParameterJdbcTemplate.update(DELETE_BY_EMAIL_SQL, parameterSource);
		
		return result > 0 ? true : false;
	}

}
