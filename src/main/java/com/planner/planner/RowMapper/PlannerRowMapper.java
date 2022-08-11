package com.planner.planner.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.planner.planner.Entity.Planner;

public class PlannerRowMapper implements RowMapper<Planner> {

	@Override
	public Planner mapRow(ResultSet rs, int rowNum) throws SQLException {
		Planner planner = new Planner.Builder().setPlannerId(rs.getInt(0))
				.setAccountId(rs.getInt(1))
				.setPlanDateStart(rs.getTimestamp(2).toLocalDateTime().toLocalDate())
				.setPlanDateEnd(rs.getTimestamp((3)).toLocalDateTime().toLocalDate())
				.setMemberCount(rs.getInt(4))
				.setMember(rs.getString(5))
				.setPlan(rs.getString(6))
				.setRecommendCount(rs.getInt(7))
				.setCreateDate(rs.getTimestamp(8).toLocalDateTime())
				.setUpdateDate(rs.getTimestamp(8).toLocalDateTime())
				.build();
		return planner;
	}
	

}

