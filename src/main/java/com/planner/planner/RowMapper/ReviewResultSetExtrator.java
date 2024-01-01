package com.planner.planner.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.planner.planner.Dto.CommentDto;
import com.planner.planner.Dto.ReviewDto;

public class ReviewResultSetExtrator implements ResultSetExtractor<ReviewDto> {

	@Override
	public ReviewDto extractData(ResultSet rs) throws SQLException, DataAccessException {
		ReviewDto review = null;
		List<CommentDto> comments = new ArrayList<CommentDto>();
		Map<Integer, CommentDto> temp = new HashMap<Integer, CommentDto>();
		List<CommentDto> commentList = new ArrayList<CommentDto>();
		int latestCommentId = 0;
		
		while(rs.next()) {
			int commentId = rs.getInt("RC_comment_id");
			if(latestCommentId != commentId) {
				latestCommentId = commentId;

				CommentDto comment = new CommentDto.Builder()
				.setCommentId(rs.getInt("RC_comment_id"))
				.setReviewId(rs.getInt("RC_review_id"))
				.setWriterId(rs.getInt("RC_writer_id"))
				.setWriter(rs.getString("AC_nickname"))
				.setContent(rs.getString("RC_content"))
				.setParentId(rs.getObject("RC_parent_id", Integer.class))
				.setCreateDate(rs.getTimestamp("RC_create_date").toLocalDateTime())
				.setUpdateDate(rs.getTimestamp("RC_update_date").toLocalDateTime())
				.build();
				
				comments.add(comment);
			}
			
			if(review == null) {
				review = new ReviewDto.Builder()
						.setReviewId(rs.getInt("review_id"))
						.setPlannerId(rs.getInt("planner_id"))
						.setTitle(rs.getString("title"))
						.setContent(rs.getString("content"))
						.setAreaCode(rs.getInt("area_code"))
						.setThumbnail(rs.getString("thumbnail"))
						.setWriter(rs.getString("writer"))
						.setWriterId(rs.getInt("writer_id"))
						.setLikeCount(rs.getInt("like_count"))
						.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime())
						.setUpdateDate(rs.getTimestamp("update_date").toLocalDateTime())
						.setComments(commentList)
						.build();
			}
		}
	
		
		comments.forEach(item -> {
			temp.put(item.getCommentId(), item);
			if(item.getParentId() != null) {
				temp.get(item.getParentId()).getReComments().add(item);
			}
			else {
				commentList.add(item);
			}
		});
		
		
		return review;
	}

}
