package com.planner.planner.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.planner.planner.Entity.Spot;

public class LikeSpotsRowMapper implements RowMapper<Spot> {

	@Override
	public Spot mapRow(ResultSet rs, int rowNum) throws SQLException {
		Spot spot = new Spot.Builder()
				.setSpotId(rs.getInt(1))
				.setSpotName(rs.getString(2))
				.setSpotImage(rs.getString(3))
				.setContryName(rs.getString(4))
				.setCityName(rs.getString(5))
				.build();
		return spot;
	}

}
