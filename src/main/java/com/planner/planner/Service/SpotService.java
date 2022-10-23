package com.planner.planner.Service;

import java.util.List;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.planner.planner.Dto.SpotDto;
import com.planner.planner.Dto.SpotLikeCountDto;

public interface SpotService {
	public boolean spotLike(int accountId, int contentId);
	public boolean spotLikeCancel(int accountId, int contentId);
	public List<SpotLikeCountDto> spotLikeCount(List<String> contentIds);
}
