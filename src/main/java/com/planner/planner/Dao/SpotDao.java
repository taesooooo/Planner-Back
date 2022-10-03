package com.planner.planner.Dao;

import java.util.List;

import com.planner.planner.Dto.SpotLikeDto;

public interface SpotDao {
	public List<SpotLikeDto> spotLikesByAccountId(int accountId);
	public List<SpotLikeDto> spotLikeByContentIds(int accountId, List<Integer> contentId);
	public boolean spotLikeAdd(int accountId, int contentId);
	public boolean spotLikeDelete(int accountId, int contentId);
}
