package com.planner.planner.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlannerLikeMapper {
	// 플래너 좋아요, 좋아요 취소
	public int plannerLike(int accountId, int plannerId);

	public int plannerUnLike(int accountId, int plannerId);

	public boolean isLike(int accountId, int plannerId);

	public List<Integer> findLikePlannerIdList(int accountId, List<Integer> plannerIdList);
}
