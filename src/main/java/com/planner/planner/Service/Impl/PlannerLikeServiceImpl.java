package com.planner.planner.Service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planner.planner.Mapper.PlannerLikeMapper;
import com.planner.planner.Service.PlannerLikeService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PlannerLikeServiceImpl implements PlannerLikeService {
	private final PlannerLikeMapper plannerLikeMapper;

	@Override
	public void plannerLikeOrUnLike(int accountId, int plannerId) {
		boolean isLike = plannerLikeMapper.isLike(accountId, plannerId);
		if (isLike) {
			plannerLikeMapper.plannerUnLike(accountId, plannerId);
		} else {
			plannerLikeMapper.plannerLike(accountId, plannerId);
		}
	}

}
