package com.planner.planner.Dao.Impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.planner.planner.Dao.PlannerDao;
import com.planner.planner.Entity.Planner;
import com.planner.planner.RowMapper.PlannerRowMapper;

@Repository
public class PlannerDaoImpl implements PlannerDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlannerDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	
	private static final String INSERT_SQL = "INSERT INTO planner(account_id, title, plan_date_start, plan_date_end, like_count, create_date, update_date)"
			+ "VALUES(?, ?, ?, ?, ?, now(), now());";
	private static final String FIND_SQL = "SELECT p.planner_id, p.account_id, p.plan_date_start, p.plan_date_end, p.like_count, p.create_date, p.update_date FROM planner AS p WHERE p.planner_id = ?;";
	private static final String FINDS_SQL = "SELECT p.planner_id, p.account_id, p.plan_date_start, p.plan_date_end, p.like_count, p.create_date, p.update_date FROM planner AS p WHERE p.account_id = ?;";
	private static final String FIND_ALL_SQL = "SELECT p.planner_id, p.account_id, p.plan_date_start, p.plan_date_end, p.like_count, p.create_date, p.update_date FROM planner AS p;";
	private static final String UPDATE_SQL = "UPDATE planner AS p SET p.title = ?, p.plan_date_start = ?, p.plan_date_end = ?, p.update_date = NOW() WHERE p.planner_id = ?;";
	private static final String DELETE_SQL = "DELETE FROM planner WHERE planner.planner_id = ?;";
	
	public PlannerDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public boolean insertPlanner(Planner planner) {
		int result = jdbcTemplate.update(INSERT_SQL, planner.getAccountId(), planner.getTitle(), planner.getPlanDateStart(), planner.getPlanDateEnd(), planner.getLikeCount()
				, planner.getCreateDate(), planner.getUpdateDate());
		return result > 0 ? true : false;
	}

	@Override
	public Planner findPlannerByPlannerId(int plannerId) {
		return jdbcTemplate.queryForObject(FIND_SQL, new PlannerRowMapper(), plannerId);
	}

	@Override
	public List<Planner> findPlannersByAccountId(int accountId) {
		return jdbcTemplate.query(FINDS_SQL, new PlannerRowMapper(), accountId);
	}

	@Override
	public List<Planner> findPlannersAll() {
		return jdbcTemplate.query(FIND_ALL_SQL, new PlannerRowMapper());
	}

	@Override
	public boolean updatePlanner(int plannerId, Planner planner) {
		int result = jdbcTemplate.update(UPDATE_SQL, planner.getTitle(), planner.getPlanDateStart(), planner.getPlanDateEnd(), plannerId);
		return result > 0 ? true : false;
	}

	@Override
	public boolean deletePlanner(int plannerId) {
		int result = jdbcTemplate.update(DELETE_SQL, plannerId);
		return result > 0 ? true : false;
	}
}
