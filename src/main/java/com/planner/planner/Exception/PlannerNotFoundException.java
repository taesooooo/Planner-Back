package com.planner.planner.Exception;

public class PlannerNotFoundException extends RuntimeException {

	public PlannerNotFoundException() {
		super("생성된 플래너가 없습니다.");
		// TODO Auto-generated constructor stub
	}

	public PlannerNotFoundException(String message) {
		super(message);
	}

}
