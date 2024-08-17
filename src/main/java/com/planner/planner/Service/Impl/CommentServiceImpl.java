package com.planner.planner.Service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planner.planner.Common.Notification.NotificationLink;
import com.planner.planner.Common.Notification.NotificationMessage;
import com.planner.planner.Common.Notification.NotificationType;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.CommentDto;
import com.planner.planner.Dto.NotificationDto;
import com.planner.planner.Dto.ReviewDto;
import com.planner.planner.Exception.CommentNotFoundException;
import com.planner.planner.Exception.ReviewNotFoundException;
import com.planner.planner.Exception.UserNotFoundException;
import com.planner.planner.Mapper.AccountMapper;
import com.planner.planner.Mapper.CommentMapper;
import com.planner.planner.Mapper.NotificationMapper;
import com.planner.planner.Mapper.ReviewMapper;
import com.planner.planner.Service.CommentService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
	private final CommentMapper commentMapper;
	private final NotificationMapper notificationMapper;
	private final ReviewMapper reviewMapper;
	private final AccountMapper AccountMapper;

	@Override
	public int newComment(int userId, int reviewId, CommentDto comment) throws Exception {
		AccountDto user = AccountMapper.findById(userId);
		if(user == null) {
			throw new UserNotFoundException("사용자 정보를 찾을 수 없습니다.");
		}
	
		int newCommentId = commentMapper.insertComment(userId, reviewId, comment);
		
		int notificationUserId = 0;
		String content = null;
		
		if(comment.getParentId() != null) {
			CommentDto parentComment = commentMapper.findById(userId);
			
			notificationUserId = parentComment.getWriterId();
			content = user.getNickname();
		}
		else {
			ReviewDto review = reviewMapper.findById(reviewId);
			if(review == null) {
				throw new ReviewNotFoundException("게시글이 존재하지 않습니다.");
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
		
		notificationMapper.insertNotification(notificationUserId, notification);
		
		return newCommentId;
	}

	@Override
	public CommentDto findByCommentId(int commentId) throws Exception {
		CommentDto comment = commentMapper.findById(commentId);
		if(comment == null) {
			throw new CommentNotFoundException();
		}
		
		return comment;
	}

	@Override
	public void updateComment(int reviewId, CommentDto comment) throws Exception {
		commentMapper.updateComment(reviewId, comment);
	}

	@Override
	public void deleteComment(int reviewId, int commentId) throws Exception {
		commentMapper.deleteComment(reviewId, commentId);
	}

}
