package com.planner.planner.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;

import com.planner.planner.Entity.Account;

public class AccountRowMapper implements RowMapper<Account> {

	@Override
	public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
		Account account = new Account(rs.getInt(1),rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),LocalDateTime.parse(rs.getDate(6).toString()),LocalDateTime.parse(rs.getDate(7).toString()));
		return account;
	}
	
}
