package com.planner.planner.Dao;

import java.util.List;

import com.planner.planner.Entity.Spot;

public interface SpotDao {
	public List<Spot> getAllSpot();
	public List<Spot> getSpotLikesByAccountId(int accountId);
	public boolean spotLike(int accountId);
}
