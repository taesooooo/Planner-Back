package com.planner.planner.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.planner.planner.Entity.Planner;

public class LikePlannersRowMapper implements RowMapper<Planner> {

	@Override
	public Planner mapRow(ResultSet rs, int rowNum) throws SQLException {
		Planner planner = new Planner.Builder()
				.setPlannerId(rs.getInt(1))
				.setTitle(rs.getString(2))
				.setPlanDateStart(rs.getTimestamp(3).toLocalDateTime().toLocalDate())
				.setPlanDateEnd(rs.getTimestamp(4).toLocalDateTime().toLocalDate())
				.build();
		return planner;
	}

}
