package com.planner.planner.Dao.Impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.planner.planner.Dao.PlanDao;
import com.planner.planner.Dto.PlanDto;
import com.planner.planner.RowMapper.PlanRowMapper;

@Repository
public class PlanDaoImpl implements PlanDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlanDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	private KeyHolder keyHolder;
	
	private final String INSERT_PlAN_SQL = "INSERT INTO plan (plan_date, planner_id, plan_index) VALUES"
			+ "(?, ?, (SELECT ifnull(MAX(p.plan_index), 0) + 1024 FROM plan AS p WHERE p.planner_id = ?));";
	private final String FINDS_PLAN_SQL = "SELECT plan.plan_id, plan.plan_date, plan.plan_index, plan.planner_id FROM plan WHERE plan.planner_id = ? ORDER BY plan_index;";
	private final String UPDATE_PLAN_SQL = "UPDATE plan SET plan.plan_date = ?, plan.plan_index = ? WHERE plan.plan_id = ?;";
	private final String DELETE_PLAN_SQL = "DELETE FROM plan WHERE plan.plan_id = ?;";

	
	public PlanDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.keyHolder = new GeneratedKeyHolder();
	}

	@Override
	public int insertPlan(int plannerId, PlanDto planDto) {
		int result = jdbcTemplate.update(conn -> {
			PreparedStatement ps = conn.prepareStatement(INSERT_PlAN_SQL, new String[] { "plan_id" });
			ps.setDate(1, Date.valueOf(planDto.getPlanDate()));
			ps.setInt(2, plannerId);
			ps.setInt(3, plannerId);
			return ps;
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public List<PlanDto> findPlansByPlannerId(int plannerId) {
		return jdbcTemplate.query(FINDS_PLAN_SQL, new PlanRowMapper(), plannerId);
	}

	@Override
	public int updatePlan(int planId, PlanDto planDto) {
		int result = jdbcTemplate.update(UPDATE_PLAN_SQL, planDto.getPlanDate(), planDto.getIndex(), planId);
		return result;
	}

	@Override
	public int deletePlan(int planId) {
		int result = jdbcTemplate.update(DELETE_PLAN_SQL, planId);
		return result;
	}

}
