package com.planner.planner.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import com.planner.planner.Entity.Account;

public class AccountRowMapper implements RowMapper<Account> {

	@Override
	public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
		Account account = new Account.Builder()
				.setAccountId(rs.getInt(1))
				.setEmail(rs.getString(2))
				.setPassword(rs.getString(3))
				.setName(rs.getString(4))
				.setNickName(rs.getString(5))
				.setCreateDate(rs.getTimestamp(6).toLocalDateTime())
				.setUpdateDate(rs.getTimestamp(7).toLocalDateTime())
				.build();
		return account;

	}

}
