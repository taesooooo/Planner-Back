package com.planner.planner.Exception;

public class NotFoundUserException extends Exception {

	public NotFoundUserException() {
		super("계정을 찾을 수 없습니다.");
	}

	public NotFoundUserException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
