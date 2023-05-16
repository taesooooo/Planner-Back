package com.planner.planner.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.planner.planner.Dto.CommentDto;

public class CommentRowMapper implements RowMapper<CommentDto> {

	@Override
	public CommentDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new CommentDto.Builder()
				.setCommentId(rs.getInt("comment_id"))
				.setReviewId(rs.getInt("review_id"))
				.setWriterId(rs.getInt("writer_id"))
				.setWriter(rs.getString("writer"))
				.setContent(rs.getString("content"))
				.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime())
				.setUpdateDate(rs.getTimestamp("update_date").toLocalDateTime())
				.build();
	}

}
