package com.planner.planner.Exception;

public class EmptyData extends RuntimeException {

	public EmptyData() {
		super("데이터가 없습니다.");
		// TODO Auto-generated constructor stub
	}

	public EmptyData(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
}
