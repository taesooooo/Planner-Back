package com.planner.planner.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.RowMapper;

import com.planner.planner.Dto.PlanDto;
import com.planner.planner.Dto.PlanLocationDto;

public class PlanRowMapper implements RowMapper<PlanDto>{

	@Override
	public PlanDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		return PlanDto.builder()
				.planId(rs.getInt("plan_id"))
				.planDate(rs.getDate("plan_date").toLocalDate())
				.index(rs.getInt("plan_index"))
				.plannerId(rs.getInt("planner_id"))
				.planLocations(new ArrayList<PlanLocationDto>())
				.build();
	}
}
