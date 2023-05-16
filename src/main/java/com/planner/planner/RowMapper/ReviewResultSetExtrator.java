package com.planner.planner.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.planner.planner.Dto.CommentDto;
import com.planner.planner.Dto.ReviewDto;

public class ReviewResultSetExtrator implements ResultSetExtractor<ReviewDto> {

	@Override
	public ReviewDto extractData(ResultSet rs) throws SQLException, DataAccessException {
		ReviewDto review = null;
		List<CommentDto> comments = new ArrayList<CommentDto>();
		int latestCommentId = 0;
		
		while(rs.next()) {
			int commentId = rs.getInt("comment_id");
			if(latestCommentId != commentId) {
				latestCommentId = commentId;

				CommentDto comment = new CommentDto.Builder()
				.setCommentId(rs.getInt("comment_id"))
				.setReviewId(rs.getInt("review_id"))
				.setWriterId(rs.getInt("writer_id"))
				.setWriter(rs.getString("writer"))
				.setContent(rs.getString("content"))
				.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime())
				.setUpdateDate(rs.getTimestamp("update_date").toLocalDateTime())
				.build();
				
				comments.add(comment);
			}
			
			if(review == null) {
				review = new ReviewDto.Builder()
						.setReviewId(rs.getInt("review_id"))
						.setPlannerId(rs.getInt("planner_id"))
						.setTitle(rs.getString("title"))
						.setContent(rs.getString("content"))
						.setWriter(rs.getString("writer"))
						.setWriterId(rs.getInt("writer_id"))
						.setLikeCount(rs.getInt("like_count"))
						.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime())
						.setUpdateDate(rs.getTimestamp("update_date").toLocalDateTime())
						.setComments(comments)
						.build();
			}
		}
		
		return review;
	}

}
