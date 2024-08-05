package com.planner.planner.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.planner.planner.Dto.PlanLocationDto;

public class PlanLocationRowMapper implements RowMapper<PlanLocationDto> {

	@Override
	public PlanLocationDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		return PlanLocationDto.builder()
				.locationId(rs.getInt("location_id"))
				.locationContentId(rs.getInt("location_content_id"))
				.locationImage(rs.getString("location_image"))
				.locationAddr(rs.getString("location_addr"))
				.locationMapx(rs.getDouble("location_mapx"))
				.locationMapy(rs.getDouble("location_mapy"))
				.locationTransportation(rs.getInt("location_transportation"))
				.locationIndex(rs.getInt("location_index"))
				.planId(rs.getInt("plan_id"))
				.build();
	}
}
