package com.planner.planner.Exception;

public class NotFoundCommentException extends Exception {

	public NotFoundCommentException() {
		super("댓글을 찾을 수 없습니다.");
		// TODO Auto-generated constructor stub
	}

	public NotFoundCommentException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
}
