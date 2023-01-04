package com.planner.planner.Service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planner.planner.Dao.PlanDao;
import com.planner.planner.Dao.PlanLocationDao;
import com.planner.planner.Dao.PlanMemberDao;
import com.planner.planner.Dao.PlannerDao;
import com.planner.planner.Dto.PlannerDto;
import com.planner.planner.Entity.Planner;
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
	public boolean add(PlannerDto plannerDto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PlannerDto findPlannerByPlannerId(int plannerId) {
		Planner planner = plannerDao.findPlannerByPlannerId(plannerId);
		return PlannerDto.from(planner);
	}

	@Override
	public List<PlannerDto> findPlannersByAccountId(int accountId) {
		List<Planner> planner = plannerDao.findPlannersByAccountId(accountId);
		if(planner.isEmpty()) {
			throw new EmptyData();
		}
		return planner.stream().map(PlannerDto::from).collect(Collectors.toList());
	}

	@Override
	public List<PlannerDto> findPlannerAll() {
		List<Planner> planner = plannerDao.findPlannersAll();
		if(planner.isEmpty()) {
			throw new EmptyData();
		}
		return planner.stream().map(PlannerDto::from).collect(Collectors.toList());
	}
}
