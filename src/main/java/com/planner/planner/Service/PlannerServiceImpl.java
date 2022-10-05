package com.planner.planner.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planner.planner.Dao.PlannerDao;
import com.planner.planner.Dto.PlannerDto;

@Service
@Transactional
public class PlannerServiceImpl implements PlannerService {

	@Autowired
	private PlannerDao plannerDao;

	@Override
	public boolean create(PlannerDto plannerDto) {
		return plannerDao.create(plannerDto.toEntity());
	}

	@Override
	public PlannerDto read(int plannerId) {
		return plannerDao.read(plannerId);
	}

	@Override
	public boolean update(PlannerDto plannerDto) {
		return plannerDao.update(plannerDto.toEntity());
	}

	@Override
	public boolean delete(int plannerId) {
		return plannerDao.delete(plannerId);
	}

	@Override
	public boolean like(int plannerId, int accountId) {
		boolean result = plannerDao.like(plannerId);
		result = plannerDao.likePlanner(plannerId, accountId);

		return result;
	}

	@Override
	public boolean likeCancel(int plannerId, int accountId) {
		boolean result = plannerDao.likeCancel(plannerId);
		result = plannerDao.likeDelete(plannerId, accountId);

		return result;
	}

	@Override
	public List<PlannerDto> getAllPlanners() {
		return plannerDao.getAllPlanners().stream().map((p) -> p.toDto()).collect(Collectors.toList());
	}

}
