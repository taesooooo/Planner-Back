package com.planner.planner.Common;

public enum SortCriteria {
	LATEST(1,"Latest"),
	LIKECOUNT(2,"LikeCount");
	
	private int num;
	private String name;
	
	private SortCriteria(int num, String name) {
		this.num = num;
		this.name = name;
	}

	public int getNum() {
		return num;
	}

	public String getName() {
		return name;
	}
	
	public static SortCriteria of(String num) {
		for(SortCriteria item : SortCriteria.values()) {
			if(item.getNum() == Integer.parseInt(num)) {
				return item;
			}
		}
		
		throw new IllegalArgumentException("잘못된 기준값 입니다.");
	}
}
