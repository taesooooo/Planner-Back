package com.planner.planner.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.planner.planner.Dto.InvitationDto;

public class InvitationRowMapper implements RowMapper<InvitationDto>{

	@Override
	public InvitationDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new InvitationDto.Builder()
				.setId(rs.getInt("id"))
				.setAccountId(rs.getInt("account_id"))
				.setPlannerId(rs.getInt("planner_id"))
				.setInviteDate(rs.getTimestamp("invite_date").toLocalDateTime())
				.setExpireDate(rs.getTimestamp("expire_date").toLocalDateTime())
				.build();
	}

}
