package com.planner.planner.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.planner.planner.Dto.SpotLikeDto;

public class SpotLikeRowMapper implements RowMapper<SpotLikeDto> {

	@Override
	public SpotLikeDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		SpotLikeDto spotLike = new SpotLikeDto.Builder()
				.setLikeId(rs.getInt("like_id"))
				.setAccountId(rs.getInt("account_id"))
				.setContentId(rs.getInt("content_id"))
				.setLikeDate(rs.getDate("like_date").toLocalDate())
				.build();
		return spotLike;
	}

}
