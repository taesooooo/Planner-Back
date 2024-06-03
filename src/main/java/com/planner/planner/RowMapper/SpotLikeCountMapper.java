package com.planner.planner.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.planner.planner.Dto.SpotLikeCountDto;

public class SpotLikeCountMapper implements RowMapper<SpotLikeCountDto> {

	@Override
	public SpotLikeCountDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		return SpotLikeCountDto.builder()
				.contentId(rs.getInt("content_id"))
				.count(rs.getInt("like_count"))
				.build();
	}
}
