package com.planner.planner.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.planner.planner.Dto.ReviewDto;

public class ReviewRowMapper implements RowMapper<ReviewDto> {

	@Override
	public ReviewDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		return ReviewDto.builder()
				.reviewId(rs.getInt("review_id"))
				.plannerId(rs.getInt("planner_id"))
				.title(rs.getString("title"))
				.content(rs.getString("content"))
				.areaCode(rs.getInt("areacode"))
				.thumbnail(rs.getString("thumbnail"))
				.writer(rs.getString("writer"))
				.writerId(rs.getInt("writer_id"))
				.likeCount(rs.getInt("like_count"))
				.createDate(rs.getTimestamp("create_date").toLocalDateTime())
				.updateDate(rs.getTimestamp("update_date").toLocalDateTime())
//				.setTotalPage(rowNum)
				.build();
	}

}
