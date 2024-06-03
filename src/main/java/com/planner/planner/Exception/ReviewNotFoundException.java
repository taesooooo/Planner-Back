package com.planner.planner.Exception;

public class ReviewNotFoundException extends RuntimeException {

	public ReviewNotFoundException() {
		super("생성된 리뷰가 없습니다.");
		// TODO Auto-generated constructor stub
	}

	public ReviewNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
}
