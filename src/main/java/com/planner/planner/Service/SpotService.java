package com.planner.planner.Service;

import java.util.List;

import com.planner.planner.Dto.SpotDto;
import com.planner.planner.Dto.SpotLikeDto;
import com.planner.planner.Dto.SpotLikeStateDto;
import com.planner.planner.Entity.Spot;
import com.planner.planner.Entity.SpotLike;

public interface SpotService {
	public List<SpotLikeDto> spotLikesFindByAccountId(int accountId);
	public List<SpotLikeStateDto> spotLikeStateCheck(int accountId, List<Integer> contentList);
	public boolean spotLike(int accountId, int contentId);
	public boolean spotLikeCancel(int accountId, int contentId);
}
