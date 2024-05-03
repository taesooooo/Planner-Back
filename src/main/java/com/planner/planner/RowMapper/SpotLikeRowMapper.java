package com.planner.planner.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.planner.planner.Dto.SpotLikeDto;

public class SpotLikeRowMapper implements RowMapper<SpotLikeDto> {

	@Override
	public SpotLikeDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		SpotLikeDto spotLike = SpotLikeDto.builder()
				.likeId(rs.getInt("like_id"))
				.accountId(rs.getInt("account_id"))
				.areaCode(rs.getInt("area_code"))
				.title(rs.getString("title"))
				.image(rs.getString("image"))
				.contentId(rs.getInt("content_id"))
				.likeDate(rs.getDate("like_date").toLocalDate())
				.build();
		return spotLike;
	}

}
