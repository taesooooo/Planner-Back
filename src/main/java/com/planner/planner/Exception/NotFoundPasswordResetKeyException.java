package com.planner.planner.Exception;

public class NotFoundPasswordResetKeyException extends RuntimeException {

	public NotFoundPasswordResetKeyException() {
		super();
	}

	public NotFoundPasswordResetKeyException(String message) {
		super(message);
	}

}
