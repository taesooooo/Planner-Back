package com.planner.planner.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.planner.planner.Entity.Planner;

public class PlannerRowMapper implements RowMapper<Planner> {

	@Override
	public Planner mapRow(ResultSet rs, int rowNum) throws SQLException {
		Planner planner = new Planner.Builder().setPlannerId(rs.getInt(1))
				.setAccountId(rs.getInt(2))
				.setTitle(rs.getString(3))
				.setPlanDateStart(rs.getTimestamp(4).toLocalDateTime().toLocalDate())
				.setPlanDateEnd(rs.getTimestamp((5)).toLocalDateTime().toLocalDate())
				.setMemberCount(rs.getInt(6))
				.setMember(rs.getString(7))
				.setPlan(rs.getString(8))
				.setRecommendCount(rs.getInt(9))
				.setCreateDate(rs.getTimestamp(10).toLocalDateTime())
				.setUpdateDate(rs.getTimestamp(11).toLocalDateTime())
				.build();
		return planner;
	}


}

