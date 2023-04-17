package com.planner.planner.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.planner.planner.Dto.ReviewDto;

public class ReviewRowMapper implements RowMapper<ReviewDto> {

	@Override
	public ReviewDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new ReviewDto.Builder()
				.setReviewId(rs.getInt("review_id"))
				.setPlannerId(rs.getInt("planner_id"))
				.setTitle(rs.getString("title"))
				.setContent(rs.getString("content"))
				.setWriter(rs.getString("writer"))
				.setWriterId(rs.getInt("writer_id"))
				.setLikeCount(rs.getInt("like_count"))
				.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime())
				.setUpdateDate(rs.getTimestamp("update_date").toLocalDateTime())
//				.setTotalPage(rowNum)
				.build();
	}

}
