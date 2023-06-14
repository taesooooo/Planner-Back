package com.planner.planner.Controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planner.planner.Dto.CommentDto;
import com.planner.planner.Exception.ForbiddenException;
import com.planner.planner.Service.AccountService;
import com.planner.planner.Service.CommentService;
import com.planner.planner.util.ResponseMessage;
import com.planner.planner.util.UserIdUtil;

@RestController
@RequestMapping(value = "/api")
public class CommentController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);
	
	private AccountService accountService;
	private CommentService commentService;
	
	public CommentController(AccountService accountService, CommentService commentService) {
		this.accountService = accountService;
		this.commentService = commentService;
	}
	
	@PostMapping(value = "/reviews/{reviewId}/comments")
	public ResponseEntity<Object> newComment(HttpServletRequest req, @PathVariable int reviewId, @RequestBody CommentDto comment) throws Exception {
		int newCommentId = commentService.newComment(reviewId, comment);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(true, "", newCommentId));
	}
	
	@PatchMapping(value = "/reviews/{reviewId}/comments/{commentId}")
	public ResponseEntity<Object> modifyComment(HttpServletRequest req, @PathVariable int reviewId, @PathVariable int commentId, @RequestBody CommentDto comment) throws Exception {
		checkAuth(req, commentId);
		
		commentService.updateComment(reviewId, comment);

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, ""));
	}
	
	@DeleteMapping(value = "/reviews/{reviewId}/comments/{commentId}")
	public ResponseEntity<Object> deleteComment(HttpServletRequest req, @PathVariable int reviewId, @PathVariable int commentId) throws Exception {
		checkAuth(req, commentId);
		
		commentService.deleteComment(reviewId, commentId);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, ""));
	}
	
	private void checkAuth(HttpServletRequest req, int commentId) throws Exception {
		int id = UserIdUtil.getUserId(req);
		
		CommentDto comment = commentService.findByCommentId(commentId);
		
		if(comment.getWriterId() != id) {
			throw new ForbiddenException("접근 권한이 없습니다.");
		}
	}
}