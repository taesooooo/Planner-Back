package com.planner.planner.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planner.planner.Dao.PlannerDao;
import com.planner.planner.Dto.PlannerDto;

@Service
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

}
