package com.planner.planner.Dao.Impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.planner.planner.Dao.PlannerDao;
import com.planner.planner.Dto.PlanDto;
import com.planner.planner.Dto.PlanLocationDto;
import com.planner.planner.Dto.PlannerDto;
import com.planner.planner.RowMapper.PlannerRowMapper;

@Repository
public class PlannerDaoImpl implements PlannerDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlannerDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	
	private static final String INSERT_PLANNER_SQL = "INSERT INTO planner(account_id, title, plan_date_start, plan_date_end, like_count, create_date, update_date)"
			+ "VALUES(?, ?, ?, ?, ?, now(), now());";
	private static final String FIND_SQL = "SELECT p.planner_id, p.account_id, p.plan_date_start, p.plan_date_end, p.like_count, p.create_date, p.update_date FROM planner AS p WHERE p.planner_id = ?;";
	private static final String FINDS_SQL = "SELECT p.planner_id, p.account_id, p.plan_date_start, p.plan_date_end, p.like_count, p.create_date, p.update_date FROM planner AS p WHERE p.account_id = ?;";
	private static final String FIND_ALL_SQL = "SELECT p.planner_id, p.account_id, p.plan_date_start, p.plan_date_end, p.like_count, p.create_date, p.update_date FROM planner AS p;";
	private static final String UPDATE_PLANNER_SQL = "UPDATE planner AS p SET p.title = ?, p.plan_date_start = ?, p.plan_date_end = ?, p.update_date = NOW() WHERE p.planner_id = ?;";
	private static final String DELETE_PLANNER_SQL = "DELETE FROM planner WHERE planner.planner_id = ?;";
	
	private static final String FIND_PLANNER_BY_PLANNER_ID_SQL = "select A.planner_id, A.account_id, A.title, A.plan_date_start, A.plan_date_end, A.like_count, A.create_date, A.update_date, "
			+ "B.plan_id, B.plan_date, "
			+ "C.location_id, C.location_content_id, C.location_image, C.location_transportation, C.plan_id "
			+ "from planner as A "
			+ "left join plan as B on A.planner_id = B.planner_id "
			+ "left join plan_location as C on B.plan_id = C.plan_id "
			+ "where A.planner_id = ?;";
	private static final String FIND_PLANNER_BY_ACCOUNT_ID_SQL = "select A.planner_id, A.account_id, A.title, A.plan_date_start, A.plan_date_end, A.like_count, A.create_date, A.update_date, "
			+ "B.plan_id, B.plan_date, "
			+ "C.location_id, C.location_content_id, C.location_image, C.location_transportation, C.plan_id "
			+ "from planner as A "
			+ "left join plan as B on A.planner_id = B.planner_id "
			+ "left join plan_location as C on B.plan_id = C.plan_id "
			+ "where A.account_id = ?;";
	
	public PlannerDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public boolean insertPlanner(PlannerDto plannerDto) {
		int result = jdbcTemplate.update(INSERT_PLANNER_SQL, plannerDto.getAccountId(), plannerDto.getTitle(), plannerDto.getPlanDateStart(), plannerDto.getPlanDateEnd(), plannerDto.getLikeCount()
				, plannerDto.getCreateDate(), plannerDto.getUpdateDate());
		return result > 0 ? true : false;
	}

	@Override
	public PlannerDto findPlannerByPlannerId(int plannerId) {
		return jdbcTemplate.query(FIND_PLANNER_BY_PLANNER_ID_SQL, new ResultSetExtractor<PlannerDto>() {
			@Override
			public PlannerDto extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<PlanDto> plans = new ArrayList<PlanDto>();
				List<PlanLocationDto> planLocations = null;
				int latestPlanId = 0;
				
				PlannerDto plannerDto = null;
				
				while(rs.next()) {
					if(plannerDto == null) {
						plannerDto = new PlannerDto.Builder()
								.setPlannerId(rs.getInt("planner_id"))
								.setAccountId(rs.getInt("account_id"))
								.setTitle(rs.getString("title"))
								.setPlanDateStart(rs.getTimestamp("plan_date_start").toLocalDateTime())
								.setPlanDateEnd(rs.getTimestamp("plan_date_end").toLocalDateTime())
								.setLikeCount(rs.getInt("like_count"))
								.setPlans(plans)
								.build();
					}
					
					int planId = rs.getInt("plan_id");
					if(latestPlanId != planId) {
						latestPlanId = planId;
						planLocations = new ArrayList<PlanLocationDto>();
						
						PlanDto newPlan = new PlanDto.Builder()
								.setPlanId(planId)
								.setPlanDate(rs.getTimestamp("plan_date").toLocalDateTime())
								.setPlannerId(rs.getInt("planner_id"))
								.setPlanLocations(planLocations)
								.build();
						plans.add(newPlan);
					}
					
					PlanLocationDto pl = new PlanLocationDto.Builder()
							.setLocationId(rs.getInt("location_id"))
							.setLocationContetntId(rs.getInt("location_content_id"))
							.setLocationImage(rs.getString("location_image"))
							.setLocationTranspotation(rs.getInt("location_transportation"))
							.setPlanId(rs.getInt("plan_id"))
							.build();
					planLocations.add(pl);
					
				}
				return plannerDto;
			}
		}, plannerId);
		//return jdbcTemplate.queryForObject(FIND_SQL, new PlannerRowMapper(), plannerId);
	}

	@Override
	public List<PlannerDto> findPlannersByAccountId(int accountId) {
		return jdbcTemplate.query(FIND_PLANNER_BY_ACCOUNT_ID_SQL, new ResultSetExtractor<List<PlannerDto>>() {
			@Override
			public List<PlannerDto> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<PlannerDto> planners = new ArrayList<PlannerDto>();
				List<PlanDto> plans = null;
				List<PlanLocationDto> planLocations = null;
				
				int latestPlannerId = 0;
				int latestPlanId = 0;
				
				while(rs.next()) {
					int plannerId = rs.getInt("planner_id");
					int planId = rs.getInt("plan_id");
					
					if(latestPlannerId != plannerId) {
						latestPlannerId = plannerId;
						plans = new ArrayList<PlanDto>();
						
						PlannerDto plannerDto = new PlannerDto.Builder()
								.setPlannerId(rs.getInt("planner_id"))
								.setAccountId(rs.getInt("account_id"))
								.setTitle(rs.getString("title"))
								.setPlanDateStart(rs.getTimestamp("plan_date_start").toLocalDateTime())
								.setPlanDateEnd(rs.getTimestamp("plan_date_end").toLocalDateTime())
								.setLikeCount(rs.getInt("like_count"))
								.setPlans(plans)
								.build();
						planners.add(plannerDto);
					}
					
					if(latestPlanId != planId) {
						latestPlanId = planId;
						planLocations = new ArrayList<PlanLocationDto>();
						
						PlanDto newPlan = new PlanDto.Builder()
								.setPlanId(planId)
								.setPlanDate(rs.getTimestamp("plan_date").toLocalDateTime())
								.setPlannerId(rs.getInt("planner_id"))
								.setPlanLocations(planLocations)
								.build();
						plans.add(newPlan);
					}
					
					PlanLocationDto newPlanLocation = new PlanLocationDto.Builder()
							.setLocationId(rs.getInt("location_id"))
							.setLocationContetntId(rs.getInt("location_content_id"))
							.setLocationImage(rs.getString("location_image"))
							.setLocationTranspotation(rs.getInt("location_transportation"))
							.setPlanId(rs.getInt("plan_id"))
							.build();
					planLocations.add(newPlanLocation);	
				}
				
				return planners;
			}
		}, accountId);
		//return jdbcTemplate.query(FINDS_SQL, new PlannerRowMapper(), accountId);
	}

	@Override
	public List<PlannerDto> findPlannersAll() {
		return jdbcTemplate.query(FIND_ALL_SQL, new PlannerRowMapper());
	}

	@Override
	public boolean updatePlanner(int plannerId, PlannerDto plannerDto) {
		int result = jdbcTemplate.update(UPDATE_PLANNER_SQL, plannerDto.getTitle(), plannerDto.getPlanDateStart(), plannerDto.getPlanDateEnd(), plannerId);
		return result > 0 ? true : false;
	}

	@Override
	public boolean deletePlanner(int plannerId) {
		int result = jdbcTemplate.update(DELETE_PLANNER_SQL, plannerId);
		return result > 0 ? true : false;
	}
}
