package com.planner.planner.Exception;

public class NotFoundPlanner extends Exception {

	public NotFoundPlanner() {
		super("플래너를 찾을 수 없습니다.");
		// TODO Auto-generated constructor stub
	}

	public NotFoundPlanner(String message) {
		super(message);
	}

}
