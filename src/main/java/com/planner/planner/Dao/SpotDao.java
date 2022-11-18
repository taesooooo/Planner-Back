package com.planner.planner.Dao;

import java.util.List;

import com.planner.planner.Entity.SpotLikeCount;

public interface SpotDao {
	public boolean spotLikeAdd(int accountId, int contentId);
	public boolean spotLikeDelete(int accountId, int contentId);
	public List<SpotLikeCount> spotLikeCount(String contentIds);
}
