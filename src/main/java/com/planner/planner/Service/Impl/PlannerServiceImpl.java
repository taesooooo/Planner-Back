package com.planner.planner.Service.Impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planner.planner.Dao.PlanDao;
import com.planner.planner.Dao.PlanLocationDao;
import com.planner.planner.Dao.PlanMemberDao;
import com.planner.planner.Dao.PlannerDao;
import com.planner.planner.Dto.PlanDto;
import com.planner.planner.Dto.PlanLocationDto;
import com.planner.planner.Dto.PlanMemberDto;
import com.planner.planner.Dto.PlannerDto;
import com.planner.planner.Exception.EmptyData;
import com.planner.planner.Service.PlannerService;

@Service
@Transactional
public class PlannerServiceImpl implements PlannerService {
	private PlannerDao plannerDao;
	private PlanMemberDao planMemberDao;
	private PlanDao planDao;
	private PlanLocationDao planLocationDao;
	
	public PlannerServiceImpl(PlannerDao plannerDao) {
		this.plannerDao = plannerDao;
	}

	@Override
	public boolean newPlanner(PlannerDto plannerDto) {
		return plannerDao.insertPlanner(plannerDto);
	}

	@Override
	public PlannerDto findPlannerByPlannerId(int plannerId) {
		return plannerDao.findPlannerByPlannerId(plannerId);
	}

	@Override
	public List<PlannerDto> findPlannersByAccountId(int accountId) {
		List<PlannerDto> planner = plannerDao.findPlannersByAccountId(accountId);
		if(planner.isEmpty()) {
			throw new EmptyData();
		}
		return planner;
	}

	@Override
	public List<PlannerDto> findPlannerAll() {
		List<PlannerDto> planner = plannerDao.findPlannersAll();
		if(planner.isEmpty()) {
			throw new EmptyData();
		}
		return planner;
	}

	@Override
	public boolean modifyPlanner(PlannerDto plannerDto) {
		return plannerDao.updatePlanner(plannerDto.getPlannerId(), plannerDto);
	}

	@Override
	public boolean deletePlanner(int plannerId) {
		return plannerDao.deletePlanner(plannerId);
	}

	@Override
	public boolean newPlanMember(PlanMemberDto planMemberDto) {
		return planMemberDao.insertPlanMember(planMemberDto);
	}

	@Override
	public List<PlanMemberDto> findPlanMembersByPlannerId(int plannerId) {
		return planMemberDao.findPlanMembersByPlannerId(plannerId);
	}

	@Override
	public boolean deletePlanMember(int planMemberId) {
		return planMemberDao.deletePlanMember(planMemberId);
	}

	@Override
	public boolean newPlan(PlanDto planDto) {
		return planDao.insertPlan(planDto);
	}

	@Override
	public PlanDto findPlanByPlannerId(int planId) {
		return planDao.findPlanByPlannerId(planId);
	}

	@Override
	public boolean modifyPlan(PlanDto planDto) {
		return planDao.updatePlan(planDto);
	}

	@Override
	public boolean deletePlan(int planId) {
		return planDao.deletePlan(planId);
	}

	@Override
	public boolean newPlanLocation(PlanLocationDto planLocationDto) {
		return planLocationDao.insertPlanLocation(planLocationDto);
	}

	@Override
	public PlanLocationDto findPlanLocationByPlanId(int locationId) {
		return planLocationDao.findPlanLocationByPlanId(locationId);
	}

	@Override
	public boolean modifyPlanLocation(PlanLocationDto planLocationDto) {
		return planLocationDao.updatePlanLocation(planLocationDto);
	}

	@Override
	public boolean deletePlanLocation(int locationId) {
		return planLocationDao.deletePlanLocation(locationId);
	}
	
}
