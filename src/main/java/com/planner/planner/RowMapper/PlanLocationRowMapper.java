package com.planner.planner.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.planner.planner.Dto.PlanLocationDto;

public class PlanLocationRowMapper implements RowMapper<PlanLocationDto> {

	@Override
	public PlanLocationDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new PlanLocationDto.Builder()
				.setLocationId(rs.getInt("location_id"))
				.setLocationContentId(rs.getInt("location_content_id"))
				.setLocationImage(rs.getString("location_image"))
				.setAddr(rs.getString("location_addr"))
				.setMapx(rs.getFloat("location_mapx"))
				.setMapy(rs.getFloat("location_mapy"))
				.setLocationTransportation(rs.getInt("location_transportation"))
				.setPlanId(rs.getInt("plan_id"))
				.build();
	}
}
