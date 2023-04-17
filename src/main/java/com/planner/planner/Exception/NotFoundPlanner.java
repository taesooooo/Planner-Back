package com.planner.planner.Exception;

public class NotFoundPlanner extends Exception {

	public NotFoundPlanner() {
		super("생성된 플래너가 없습니다.");
		// TODO Auto-generated constructor stub
	}

	public NotFoundPlanner(String message) {
		super(message);
	}

}
