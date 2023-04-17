package com.planner.planner.Exception;

public class NotFoundReviewException extends RuntimeException {

	public NotFoundReviewException() {
		super("생성된 리뷰가 없습니다.");
		// TODO Auto-generated constructor stub
	}

	public NotFoundReviewException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
}
