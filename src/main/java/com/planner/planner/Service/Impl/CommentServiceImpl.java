package com.planner.planner.Service.Impl;

import org.springframework.stereotype.Service;

import com.planner.planner.Dao.CommentDao;
import com.planner.planner.Dto.CommentDto;
import com.planner.planner.Exception.NotFoundCommentException;
import com.planner.planner.Service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	private CommentDao commentDao;
	
	public CommentServiceImpl(CommentDao commentDao) {
		this.commentDao = commentDao;
	}

	@Override
	public int newComment(int reviewId, CommentDto comment) throws Exception {
		int newCommentId = commentDao.insertComment(reviewId, comment);
		return newCommentId;
	}

	@Override
	public CommentDto findByCommentId(int commentId) throws Exception {
		CommentDto comment = commentDao.selectCommentByCommentId(commentId);
		if(comment == null) {
			throw new NotFoundCommentException();
		}
		
		return comment;
	}

	@Override
	public void updateComment(int reviewId, CommentDto comment) throws Exception {
		commentDao.updateComment(reviewId, comment);
	}

	@Override
	public void deleteComment(int reviewId, int commentId) throws Exception {
		commentDao.deleteCommet(reviewId, commentId);
	}

}
