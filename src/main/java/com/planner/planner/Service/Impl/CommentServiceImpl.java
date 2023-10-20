package com.planner.planner.Service.Impl;

import org.springframework.stereotype.Service;

import com.planner.planner.Common.Notification.NotificationLink;
import com.planner.planner.Common.Notification.NotificationMessage;
import com.planner.planner.Common.Notification.NotificationType;
import com.planner.planner.Dao.CommentDao;
import com.planner.planner.Dao.NotificationDao;
import com.planner.planner.Dao.ReviewDao;
import com.planner.planner.Dto.CommentDto;
import com.planner.planner.Dto.NotificationDto;
import com.planner.planner.Dto.ReviewDto;
import com.planner.planner.Exception.NotFoundCommentException;
import com.planner.planner.Exception.NotFoundReviewException;
import com.planner.planner.Service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	private CommentDao commentDao;
	private NotificationDao notificationDao;
	private ReviewDao reviewDao;
	
	public CommentServiceImpl(CommentDao commentDao, NotificationDao notificationDao, ReviewDao reviewDao) {
		this.commentDao = commentDao;
		this.notificationDao = notificationDao;
		this.reviewDao = reviewDao;
	}

	@Override
	public int newComment(int reviewId, CommentDto comment) throws Exception {
		int newCommentId = commentDao.insertComment(reviewId, comment);
		
		if(comment.getParentId() != null) {
			CommentDto parentComment = commentDao.selectCommentByCommentId(comment.getParentId());
			
			NotificationDto notification = new NotificationDto.Builder()
					.setAccountId(parentComment.getWriterId())
					.setContent(String.format(NotificationMessage.COMMENT, comment.getWriter()))
					.setLink(String.format(NotificationLink.REVIEW_LINK, reviewId))
					.setNotificationType(NotificationType.COMMENT)
					.build();
			
			notificationDao.createNotification(parentComment.getWriterId(), notification);
		}
		else {
			ReviewDto review = reviewDao.findReview(reviewId);
			if(review == null) {
				throw new NotFoundReviewException("게시글이 존재하지 않습니다.");
			}
			
			NotificationDto notification = new NotificationDto.Builder()
					.setAccountId(review.getWriterId())
					.setContent(String.format(NotificationMessage.COMMENT, comment.getWriter()))
					.setLink(String.format(NotificationLink.REVIEW_LINK, reviewId))
					.setNotificationType(NotificationType.COMMENT)
					.build();
			
			notificationDao.createNotification(review.getWriterId(), notification);
		}
		
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
