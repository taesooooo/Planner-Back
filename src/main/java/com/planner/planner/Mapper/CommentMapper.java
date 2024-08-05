package com.planner.planner.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.planner.planner.Common.Security.UserIdentifierDao;
import com.planner.planner.Dto.CommentDto;

@Mapper
public interface CommentMapper extends UserIdentifierDao {
	public int insertComment(int accountId, int reviewId, CommentDto comment) throws Exception;

	public List<CommentDto> findAll(int reviewId) throws Exception;

	public CommentDto findById(int commentId) throws Exception;

	public int updateComment(int reviewId, CommentDto comment) throws Exception;

	public int deleteComment(int reviewId, int commentId) throws Exception;
}
