package com.planner.planner.Exception;

public class CommentNotFoundException extends RuntimeException {

	public CommentNotFoundException() {
		super("댓글을 찾을 수 없습니다.");
		// TODO Auto-generated constructor stub
	}

	public CommentNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
}
