package com.planner.planner.Dao;

import java.util.List;

import com.planner.planner.Dto.CommentDto;

public interface CommentDao {
	public int insertComment(int reviewId, CommentDto comment) throws Exception; 
	public List<CommentDto> selectAllComment(int reviewId) throws Exception;
	public CommentDto selectCommentByCommentId(int commentId) throws Exception;
	public void updateComment(int reviewId, CommentDto comment) throws Exception;
	public void deleteCommet(int reviewId, int commentId) throws Exception;
}
