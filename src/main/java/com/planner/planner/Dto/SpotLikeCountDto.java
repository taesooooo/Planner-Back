package com.planner.planner.Dto;

import com.planner.planner.Entity.SpotLikeCount;

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
	
	public SpotLikeCount toEntity() {
		return new SpotLikeCount(contentId, likeCount);
	}
	
	public static SpotLikeCountDto form(SpotLikeCount spotLikeCount) {
		return new SpotLikeCountDto(spotLikeCount.getCotentId(),spotLikeCount.getLikeCount());
	}
}
