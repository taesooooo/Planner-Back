package com.planner.planner.Entity;

public class SpotLikeCount {
	private int contentId;
	private int likeCount;
	
	public SpotLikeCount(int contentId, int likeCount) {
		this.contentId = contentId;
		this.likeCount = likeCount;
	}

	public int getCotentId() {
		return contentId;
	}

	public int getLikeCount() {
		return likeCount;
	}
}
