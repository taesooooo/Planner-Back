package com.planner.planner.Service.Impl;

import org.springframework.stereotype.Service;

import com.planner.planner.Dao.PlanDao;
import com.planner.planner.Dto.PlanDto;
import com.planner.planner.Service.PlanService;

@Service
public class PlanServiceImpl implements PlanService {
	private PlanDao planDao;
	
	public PlanServiceImpl(PlanDao planDao) {
		this.planDao = planDao;
	}

	@Override
	public int newPlan(PlanDto planDto) throws Exception {
		return planDao.insertPlan(planDto);
	}

	@Override
	public void updatePlan(int planId, PlanDto planDto) throws Exception {
		planDao.updatePlan(planId, planDto);
	}

	@Override
	public void deletePlan(int planId) throws Exception {
		planDao.deletePlan(planId);
	}

}
