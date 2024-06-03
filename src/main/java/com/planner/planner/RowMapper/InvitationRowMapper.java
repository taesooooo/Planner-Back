package com.planner.planner.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.planner.planner.Dto.InvitationDto;

public class InvitationRowMapper implements RowMapper<InvitationDto>{

	@Override
	public InvitationDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		return InvitationDto.builder()
				.id(rs.getInt("id"))
				.accountId(rs.getInt("account_id"))
				.plannerId(rs.getInt("planner_id"))
				.inviteDate(rs.getTimestamp("invite_date").toLocalDateTime())
				.expireDate(rs.getTimestamp("expire_date").toLocalDateTime())
				.build();
	}

}
