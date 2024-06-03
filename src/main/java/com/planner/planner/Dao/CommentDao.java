package com.planner.planner.Dao;

import java.util.List;

import com.planner.planner.Common.Security.UserIdentifierDao;
import com.planner.planner.Dto.CommentDto;

public interface CommentDao extends UserIdentifierDao {
	public int insertComment(int accountId, int reviewId, CommentDto comment) throws Exception; 
	public List<CommentDto> selectAllComment(int reviewId) throws Exception;
	public CommentDto findById(int commentId) throws Exception;
	public void updateComment(int reviewId, CommentDto comment) throws Exception;
	public void deleteCommet(int reviewId, int commentId) throws Exception;
}
