package com.planner.planner.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.planner.planner.Dto.PasswordResetkeyDto;

public class PasswordResetKeyRowMapper implements RowMapper<PasswordResetkeyDto>{

	@Override
	public PasswordResetkeyDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		return PasswordResetkeyDto.builder()
				.id(rs.getInt("id"))
				.resetKey(rs.getString("reset_key"))
				.accountId(rs.getInt("account_id"))
				.expireDate(rs.getTimestamp("expire_date").toLocalDateTime())
				.createDate(rs.getTimestamp("create_date").toLocalDateTime())
				.build();
	}

}
