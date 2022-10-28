package com.planner.planner.Dao;

public interface SpotDao {
	public boolean spotLikeAdd(int accountId, int contentId);
	public boolean spotLikeDelete(int accountId, int contentId);
}
