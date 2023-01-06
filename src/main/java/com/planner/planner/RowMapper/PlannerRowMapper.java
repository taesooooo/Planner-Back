package com.planner.planner.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.planner.planner.Dto.PlannerDto;

public class PlannerRowMapper implements RowMapper<PlannerDto> {

	@Override
	public PlannerDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new PlannerDto.Builder()
				.setPlannerId(rs.getInt("planner_id"))
				.setAccountId(rs.getInt("account_id"))
				.setTitle(rs.getString("title"))
				.setPlanDateStart(rs.getTimestamp("plan_date_start").toLocalDateTime())
				.setPlanDateEnd(rs.getTimestamp("plan_date_end").toLocalDateTime())
				.setLikeCount(rs.getInt("like_count"))
				.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime())
				.setUpdateDate(rs.getTimestamp("update_date").toLocalDateTime())
				.build();
	}


}

