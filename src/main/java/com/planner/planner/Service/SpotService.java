package com.planner.planner.Service;

import java.util.List;

import com.planner.planner.Dto.SpotDto;
import com.planner.planner.Entity.Spot;

public interface SpotService {
	public List<SpotDto> getAllSpot();
	public List<SpotDto> getSpotLikesByAccountId(int accountId);
	public boolean spotLike(int accountId);
}
