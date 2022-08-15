package com.planner.planner.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.LikeDto;
import com.planner.planner.Entity.Account;
import com.planner.planner.Entity.Like;
import com.planner.planner.RowMapper.AccountRowMapper;
import com.planner.planner.util.ResponseMessage;

@Repository
public class AccountDaoImpl implements AccountDao {

	private static final Logger logger = LoggerFactory.getLogger(AccountDaoImpl.class);

	private ObjectMapper jsonMapper = new ObjectMapper();

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private final String createSQL = "INSERT INTO ACCOUNT(email,password,name,nickname,create_date,update_date) VALUES(?,?,?,?, now(), now());";
	private final String readSQL = "SELECT account_id, email, password, name, nickname, create_date, update_date FROM account WHERE email = ?";
	private final String updateSQL = "UPDATE ACCOUNT SET name = ?, nickname = ?, update_date = now() WHERE email = ?;";
	private final String deleteSQL = "DELETE FROM ACCOUNT WHERE email = ?;";
	private final String passwordUpdateSQL = "UPDATE account SET password = ?, update_date = now() WHERE email = ?";
	private final String likeReadSQL = "SELECT planner_id, title, plan_date_start, plan_date_end FROM planner WHERE planner_id "
			+ "IN (SELECT planner_id FROM plannerlike WHERE account_id = ?);";

	@Override
	public boolean create(Account account) {
		int result = jdbcTemplate.update(createSQL, account.getEmail(), account.getPassword(), account.getName(),
				account.getNickName());
		return result > 0 ? true : false;
	}

	@Override
	public AccountDto read(Account account) {
		Account user = jdbcTemplate.queryForObject(readSQL, new AccountRowMapper(), account.getEmail());
		if (user != null) {
			return user.toDto();
		} else {

			return null;
		}
	}

	@Override
	public boolean update(Account account) {
		int result = jdbcTemplate.update(updateSQL, account.getName(), account.getNickName(), account.getEmail());
		return result > 0 ? true : false;
	}

	@Override
	public boolean delete(Account account) {
		int result = jdbcTemplate.update(deleteSQL, account.getEmail());
		return result > 0 ? true : false;
	}

	@Override
	public boolean passwordUpdate(Account account) {
		int result = jdbcTemplate.update(passwordUpdateSQL, account.getPassword());
		return result > 0 ? true : false;
	}

	@Override
	public List<LikeDto> getLikes(int accountId) {
		return null;
	}

}
