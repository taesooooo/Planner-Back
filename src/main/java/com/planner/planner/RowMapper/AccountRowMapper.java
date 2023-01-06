package com.planner.planner.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.planner.planner.Dto.AccountDto;

public class AccountRowMapper implements RowMapper<AccountDto> {

	@Override
	public AccountDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		AccountDto account = new AccountDto.Builder()
				.setAccountId(rs.getInt(1))
				.setEmail(rs.getString(2))
				.setPassword(rs.getString(3))
				.setUserName(rs.getString(4))
				.setNickName(rs.getString(5))
				.setPhone(rs.getString(6))
				.setImage(rs.getString(7))
				.setCreateDate(rs.getTimestamp(8).toLocalDateTime())
				.setUpdateDate(rs.getTimestamp(9).toLocalDateTime())
				.build();
		return account;

	}

}
