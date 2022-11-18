package com.planner.planner.Exception;

public class OpenAPIDataEmpty extends RuntimeException {

	public OpenAPIDataEmpty() {
		super("OPEN API에서 데이터를 가져오지 못헀습니다.");
		// TODO Auto-generated constructor stub
	}

	public OpenAPIDataEmpty(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
}
