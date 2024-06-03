package com.planner.planner.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.planner.planner.Dto.PlannerDto;

public class PlannerRowMapper implements RowMapper<PlannerDto> {
	@Override
	public PlannerDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		String thumbnail = rs.getString("thumbnail");
		
		return PlannerDto.builder()
				.plannerId(rs.getInt("planner_id"))
				.accountId(rs.getInt("account_id"))
				.areaCode(rs.getInt("area_code"))
				.creator(rs.getString("creator"))
				.title(rs.getString("title"))
				.planDateStart(rs.getDate("plan_date_start").toLocalDate())
				.planDateEnd(rs.getDate("plan_date_end").toLocalDate())
				.expense(rs.getInt("expense"))
				.memberCount(rs.getInt("member_count"))
				.memberTypeId(rs.getInt("member_type_id"))
				.likeCount(rs.getInt("like_count"))
				.likeState(rs.getInt("like_id") != 0 ? true : false)
				.thumbnail(thumbnail == null ? "" : thumbnail)
				.createDate(rs.getTimestamp("create_date").toLocalDateTime())
				.updateDate(rs.getTimestamp("update_date").toLocalDateTime())
				.build();
	}
}

