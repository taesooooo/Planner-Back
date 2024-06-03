package com.planner.planner.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.planner.planner.Dto.CommentDto;

public class CommentRowMapper implements RowMapper<CommentDto> {

	@Override
	public CommentDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		return CommentDto.builder()
				.commentId(rs.getInt("comment_id"))
				.reviewId(rs.getInt("review_id"))
				.writerId(rs.getInt("writer_id"))
				.writer(rs.getString("writer"))
				.content(rs.getString("content"))
				.createDate(rs.getTimestamp("create_date").toLocalDateTime())
				.updateDate(rs.getTimestamp("update_date").toLocalDateTime())
				.build();
	}

}
