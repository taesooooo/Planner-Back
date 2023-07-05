package com.planner.planner.Dao;

import java.util.List;

public interface PlannerLikeDao {
	// 플래너 좋아요, 좋아요 취소
	public int plannerLike(int accountId, int plannerId);

	public int plannerUnLike(int accountId, int plannerId);

	public boolean isLike(int accountId, int plannerId);

	public List<Integer> returnLikePlannerIdList(int accountId, List<Integer> plannerIdList);
}
