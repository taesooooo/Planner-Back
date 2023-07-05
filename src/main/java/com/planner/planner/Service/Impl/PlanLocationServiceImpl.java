package com.planner.planner.Service.Impl;

import org.springframework.stereotype.Service;

import com.planner.planner.Dao.PlanLocationDao;
import com.planner.planner.Dto.PlanLocationDto;
import com.planner.planner.Service.PlanLocationService;

@Service
public class PlanLocationServiceImpl implements PlanLocationService {
	private PlanLocationDao planLocationDao;

	public PlanLocationServiceImpl(PlanLocationDao planLocationDao) {
		this.planLocationDao = planLocationDao;
	}

	@Override
	public int newPlanLocation(PlanLocationDto planLocationDto) throws Exception {
		return planLocationDao.insertPlanLocation(planLocationDto);
	}

	@Override
	public void updatePlanLocation(int planLocationId, PlanLocationDto planLocationDto) throws Exception {
		planLocationDao.updatePlanLocation(planLocationId, planLocationDto);
	}

	@Override
	public void deletePlanLocation(int planLocationId) throws Exception {
		planLocationDao.deletePlanLocation(planLocationId);
	}

}
