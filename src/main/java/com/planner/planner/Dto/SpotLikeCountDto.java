package com.planner.planner.Dto;

public class SpotLikeCountDto {
	private int contentId;
	private int likeCount;
	
	public SpotLikeCountDto(int contentId, int likeCount) {
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
