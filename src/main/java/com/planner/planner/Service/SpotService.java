package com.planner.planner.Service;

public interface SpotService {
	public boolean spotLike(int accountId, int contentId);
	public boolean spotLikeCancel(int accountId, int contentId);
}
