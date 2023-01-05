package com.planner.planner.Dao.Impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.planner.planner.Dao.PlanDao;
import com.planner.planner.Dto.PlanDto;
import com.planner.planner.Entity.Plan;
import com.planner.planner.RowMapper.PlanRowMapper;

@Repository
public class PlanDaoImpl implements PlanDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlanDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	
	private static final String INSERT_SQL = "INSERT INTO plan (plan_date, planner_id) VALUES(?, ?);";
	private static final String FIND_SQL = "SELECT plan.plan_id, plan.plan_date, plan.planner_id FROM plan WHERE plan.plan_id = ?;";
	private static final String FINDS_SQL = "SELECT plan.plan_id, plan.plan_date, plan.planner_id FROM plan WHERE plan.planner_id = ?;";
	private static final String UPDATE_SQL = "UPDATE plan SET plan.plan_date = ? WHERE plan.plan_id = ?;";
	private static final String DELETE_SQL = "DELETE FROM plan WHERE plan.plan_id = ?;";
	
	public PlanDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public boolean insertPlan(PlanDto planDto) {
		int result = jdbcTemplate.update(INSERT_SQL, planDto.getPlanDate(), planDto.getPlannerId());
		return result > 0 ? true : false;
	}

	@Override
	public PlanDto findPlanByPlannerId(int planId) {
		return jdbcTemplate.queryForObject(FIND_SQL, new PlanRowMapper(), planId);
	}

	@Override
	public List<PlanDto> findPlansByPlannerId(int plannerId) {
		return jdbcTemplate.query(FINDS_SQL, new PlanRowMapper(), plannerId);
	}

	@Override
	public boolean updatePlan(PlanDto planDto) {
		int result = jdbcTemplate.update(UPDATE_SQL, planDto.getPlanDate());
		return result > 0 ? true : false;
	}

	@Override
	public boolean deletePlan(int planId) {
		int result = jdbcTemplate.update(DELETE_SQL, planId);
		return result > 0 ? true : false;
	}

}
