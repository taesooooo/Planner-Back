package com.planner.planner.Service.Impl;

import org.springframework.stereotype.Service;

import com.planner.planner.Dao.PlanMemoDao;
import com.planner.planner.Dto.PlanMemoDto;
import com.planner.planner.Service.PlanMemoService;

@Service
public class PlanMemoServiceImpl implements PlanMemoService {
	private PlanMemoDao planMemoDao;

	public PlanMemoServiceImpl(PlanMemoDao planMemoDao) {
		this.planMemoDao = planMemoDao;
	}

	@Override
	public int newMemo(int plannerId, PlanMemoDto planMemoDto) {
		return planMemoDao.insertPlanMemo(plannerId, planMemoDto);
	}

	@Override
	public void updateMemo(int memoId, PlanMemoDto planMemoDto) {
		planMemoDao.updatePlanMemo(memoId, planMemoDto);
	}

	@Override
	public void deleteMemo(int memoId) {
		planMemoDao.deletePlanMemo(memoId);
	}

}
