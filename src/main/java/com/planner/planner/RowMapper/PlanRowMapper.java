package com.planner.planner.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.planner.planner.Entity.Plan;

public class PlanRowMapper implements RowMapper<Plan>{

	@Override
	public Plan mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Plan.Builder()
				.setPlanId(rs.getInt("plan_id"))
				.setPlanDate(rs.getTimestamp("plan_date").toLocalDateTime())
				.setPlannerId(rs.getInt("planner_id"))
				.build();
	}
}
