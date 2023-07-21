package com.planner.planner.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.planner.planner.Dto.AuthenticationCodeDto;

public class AuthenticationCodeRowMapper implements RowMapper<AuthenticationCodeDto>{

	@Override
	public AuthenticationCodeDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new AuthenticationCodeDto.Builder()
				.setId(rs.getInt("id"))
				.setPhone(rs.getString("phone"))
				.setCode(rs.getString("code"))
				.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime())
				.build();
	}
	
}
