package com.planner.planner.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.planner.planner.Dto.AccountDto;

public class AccountRowMapper implements RowMapper<AccountDto> {

	@Override
	public AccountDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		AccountDto account = AccountDto.builder()
				.accountId(rs.getInt(1))
				.email(rs.getString(2))
				.password(rs.getString(3))
				.username(rs.getString(4))
				.nickname(rs.getString(5))
				.phone(rs.getString(6))
				.image(rs.getString(7))
				.createDate(rs.getTimestamp(8).toLocalDateTime())
				.updateDate(rs.getTimestamp(9).toLocalDateTime())
				.build();
		return account;

	}

}
