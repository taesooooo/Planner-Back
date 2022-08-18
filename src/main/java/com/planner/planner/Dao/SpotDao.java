package com.planner.planner.Dao;

import java.util.List;

import com.planner.planner.Entity.Spot;

public interface SpotDao {
	public List<Spot> getAllSpot();
	public List<Spot> getSpotLikesByAccountId(int accountId);
	public boolean spotLike(int spotId);
	public boolean spotLikeAdd(int spotId, int accountId);
	public boolean spotLikeCancel(int spotId);
	public boolean spotLikeDelete(int accountId, int spotId);
}
