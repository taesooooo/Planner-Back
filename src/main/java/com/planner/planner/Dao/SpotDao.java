package com.planner.planner.Dao;

import com.planner.planner.Dto.SpotLikeCountDto;

public interface SpotDao {
	public boolean spotLikeAdd(int accountId, int contentId);
	public boolean spotLikeDelete(int accountId, int contentId);
	public SpotLikeCountDto spotLikeCount(int contentIds);
}
