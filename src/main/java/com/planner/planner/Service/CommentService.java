package com.planner.planner.Service;

import com.planner.planner.Dto.CommentDto;

public interface CommentService {

	public int newComment(int reviewId, CommentDto comment) throws Exception;
	public CommentDto findByCommentId(int commentId) throws Exception;
	public void updateComment(int reviewId, CommentDto comment) throws Exception;
	public void deleteComment(int reviewId, int commentId) throws Exception;
}
