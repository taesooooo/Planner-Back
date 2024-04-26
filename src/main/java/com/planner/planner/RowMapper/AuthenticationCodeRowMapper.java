package com.planner.planner.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.planner.planner.Dto.AuthenticationCodeDto;

public class AuthenticationCodeRowMapper implements RowMapper<AuthenticationCodeDto>{

	@Override
	public AuthenticationCodeDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		return AuthenticationCodeDto.builder()
				.id(rs.getInt("id"))
				.phone(rs.getString("phone"))
				.email(rs.getString("email"))
				.code(rs.getString("code"))
				.confirm(rs.getBoolean("code_confirm"))
				.expireDate(rs.getTimestamp("expire_date").toLocalDateTime())
				.createDate(rs.getTimestamp("create_date").toLocalDateTime())
				.build();
	}
	
}
