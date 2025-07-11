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

				CommentDto comment = CommentDto.builder()
				.commentId(rs.getInt("RC_comment_id"))
				.reviewId(rs.getInt("RC_review_id"))
				.writerId(rs.getInt("RC_writer_id"))
				.writer(rs.getString("AC_nickname"))
				.content(rs.getString("RC_content"))
				.parentId(rs.getObject("RC_parent_id", Integer.class))
				.createDate(rs.getTimestamp("RC_create_date").toLocalDateTime())
				.updateDate(rs.getTimestamp("RC_update_date").toLocalDateTime())
				.build();
				
				comments.add(comment);
			}
			
			if(review == null) {
				review = ReviewDto.builder()
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
						.comments(commentList)
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
