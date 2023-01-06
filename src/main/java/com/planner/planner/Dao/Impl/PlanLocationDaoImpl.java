package com.planner.planner.Dao.Impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.planner.planner.Dao.PlanLocationDao;
import com.planner.planner.Dto.PlanLocationDto;
import com.planner.planner.RowMapper.PlanLocationRowMapper;

@Repository
public class PlanLocationDaoImpl implements PlanLocationDao {
	private static final Logger logger = LoggerFactory.getLogger(PlanLocationDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	
	private static final String INSERT_SQL = "INSERT INTO plan_location(location_content_id, location_image, location_transportation, plan_id) VALUES(?, ?, ?, ?);";
	private static final String FIND_SQL = "SELECT plan_location.location_id, plan_location.location_content_id, plan_location.location_image, plan_location.location_transportation, plan_location.plan_id"
			+ "FROM plan_location"
			+ "WHERE plan_location.location_id = ?;";
	private static final String FINDS_SQL = "SELECT plan_location.location_id, plan_location.location_content_id, plan_location.location_image, plan_location.location_transportation, plan_location.plan_id"
			+ "FROM plan_location"
			+ "WHERE plan_location.plan_id = ?;";
	private static final String UPDATE_SQL = "UPDATE plan_location SET plan_location.location_transportation = ? WHERE plan_location.location_id = ?;";
	private static final String DELETE_SQL = "DELETE FROM plan_location WHERE plan_location.location_id = ?;";
	
	public PlanLocationDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public boolean insertPlanLocation(PlanLocationDto planLocationDto) {
		int result = jdbcTemplate.update(INSERT_SQL, planLocationDto.getLocationContetntId(), planLocationDto.getLocationImage(), planLocationDto.getLocationTranspotation(), planLocationDto.getPlanId());
		return result > 0 ? true : false;
	}

	@Override
	public PlanLocationDto findPlanLocationByPlanId(int locationId) {
		return jdbcTemplate.queryForObject(FIND_SQL, new PlanLocationRowMapper(), locationId);
	}

	@Override
	public List<PlanLocationDto> findPlanLocationsByPlanId(int planId) {
		return jdbcTemplate.query(FINDS_SQL, new PlanLocationRowMapper(), planId);
	}

	@Override
	public boolean updatePlanLocation(PlanLocationDto planLocationDto) {
		int result = jdbcTemplate.update(UPDATE_SQL, planLocationDto.getLocationTranspotation(), planLocationDto.getPlanId());
		return result > 0 ? true : false;
	}

	@Override
	public boolean deletePlanLocation(int locationId) {
		int result = jdbcTemplate.update(DELETE_SQL, locationId);
		return result > 0 ? true : false;
	}

}
