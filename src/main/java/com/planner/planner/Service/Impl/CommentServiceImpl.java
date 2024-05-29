package com.planner.planner.Service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planner.planner.Common.Notification.NotificationLink;
import com.planner.planner.Common.Notification.NotificationMessage;
import com.planner.planner.Common.Notification.NotificationType;
import com.planner.planner.Dao.AccountDao;
import com.planner.planner.Dao.CommentDao;
import com.planner.planner.Dao.NotificationDao;
import com.planner.planner.Dao.ReviewDao;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.CommentDto;
import com.planner.planner.Dto.NotificationDto;
import com.planner.planner.Dto.ReviewDto;
import com.planner.planner.Exception.NotFoundCommentException;
import com.planner.planner.Exception.NotFoundReviewException;
import com.planner.planner.Exception.NotFoundUserException;
import com.planner.planner.Service.CommentService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
	private final CommentDao commentDao;
	private final NotificationDao notificationDao;
	private final ReviewDao reviewDao;
	private final AccountDao accountDao;

	@Override
	public int newComment(int userId, int reviewId, CommentDto comment) throws Exception {
		AccountDto user = accountDao.findById(userId);
		if(user == null) {
			throw new NotFoundUserException("사용자 정보를 찾을 수 없습니다.");
		}
	
		int newCommentId = commentDao.insertComment(userId, reviewId, comment);
		
		int notificationUserId = 0;
		String content = null;
		
		if(comment.getParentId() != null) {
			CommentDto parentComment = commentDao.findById(userId);
			
			notificationUserId = parentComment.getWriterId();
			content = user.getNickname();
		}
		else {
			ReviewDto review = reviewDao.findById(reviewId);
			if(review == null) {
				throw new NotFoundReviewException("게시글이 존재하지 않습니다.");
			}
			
			notificationUserId = review.getWriterId();
			content = user.getNickname();
		}
		
		NotificationDto notification = NotificationDto.builder()
				.accountId(notificationUserId)
				.content(String.format(NotificationMessage.COMMENT, content))
				.link(String.format(NotificationLink.REVIEW_LINK, reviewId))
				.notificationType(NotificationType.COMMENT)
				.build();
		
		notificationDao.createNotification(notificationUserId, notification);
		
		return newCommentId;
	}

	@Override
	public CommentDto findByCommentId(int commentId) throws Exception {
		CommentDto comment = commentDao.findById(commentId);
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
