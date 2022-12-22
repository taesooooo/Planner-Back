package com.planner.planner.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.planner.planner.Entity.Planner;

public class PlannerRowMapper implements RowMapper<Planner> {

	@Override
	public Planner mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Planner.Builder()
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

