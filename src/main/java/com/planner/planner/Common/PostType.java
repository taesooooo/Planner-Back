package com.planner.planner.Common;

public enum PostType {
	PLANNER(1, "Planner"),
	SPOT(2, "Spot"),
	REVIEW(3, "Review");
	
	private int num;
	private String type;
	
	PostType(int num, String type) {
		this.num = num;
		this.type = type;
	}

	public int getNum() {
		return num;
	}

	public String getType() {
		return type;
	}
	
	public static PostType of(String num) {
		for(PostType postType : PostType.values()) {
			if(postType.getNum() == Integer.parseInt(num)) {
				return postType;
			}
		}
		
		throw new IllegalArgumentException("잘못된 글 유형입니다.");
	}
}
