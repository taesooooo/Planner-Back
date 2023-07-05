package com.planner.planner.Service.Impl;

import org.springframework.stereotype.Service;

import com.planner.planner.Dao.PlannerLikeDao;
import com.planner.planner.Service.PlannerLikeService;

@Service
public class PlannerLikeServiceImpl implements PlannerLikeService {
	private PlannerLikeDao plannerLikeDao;

	public PlannerLikeServiceImpl(PlannerLikeDao plannerLikeDao) {
		this.plannerLikeDao = plannerLikeDao;
	}

	@Override
	public void plannerLikeOrUnLike(int accountId, int plannerId) {
		boolean isLike = plannerLikeDao.isLike(accountId, plannerId);
		if (isLike) {
			plannerLikeDao.plannerUnLike(accountId, plannerId);
		} else {
			plannerLikeDao.plannerLike(accountId, plannerId);
		}
	}

}
