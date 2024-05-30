package com.planner.planner.Exception;

public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException() {
		super("계정을 찾을 수 없습니다.");
	}

	public UserNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
