package com.planner.planner.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.planner.planner.Dto.PasswordResetkeyDto;

public class PasswordResetKeyRowMapper implements RowMapper<PasswordResetkeyDto>{

	@Override
	public PasswordResetkeyDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new PasswordResetkeyDto.Builder()
				.setId(rs.getInt("id"))
				.setResetKey(rs.getString("reset_key"))
				.setAccountId(rs.getInt("account_id"))
				.setExpireDate(rs.getTimestamp("expire_date").toLocalDateTime())
				.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime())
				.build();
	}

}
