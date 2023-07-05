package com.planner.planner.Service;

public interface PlannerLikeService {
	// 플래너 좋아요, 좋아요 취소
	public void plannerLikeOrUnLike(int accountId, int plannerId);
}
