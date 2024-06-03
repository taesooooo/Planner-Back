package com.planner.planner.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.planner.planner.Dto.RefreshTokenDto;

public class ReflashTokenRowMapper implements RowMapper<RefreshTokenDto>{

	@Override
	public RefreshTokenDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		return RefreshTokenDto.builder()
				.id(rs.getInt("id"))
				.email(rs.getString("email"))
				.token(rs.getString("token"))
				.build();
	}

}
