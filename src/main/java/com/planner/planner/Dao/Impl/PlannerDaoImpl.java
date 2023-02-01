package com.planner.planner.Dao.Impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.planner.planner.Dao.PlannerDao;
import com.planner.planner.Dto.PlanDto;
import com.planner.planner.Dto.PlanLocationDto;
import com.planner.planner.Dto.PlanMemberDto;
import com.planner.planner.Dto.PlannerDto;
import com.planner.planner.RowMapper.PlanLocationRowMapper;
import com.planner.planner.RowMapper.PlanMemberRowMapper;
import com.planner.planner.RowMapper.PlanRowMapper;
import com.planner.planner.RowMapper.PlannerRowMapper;

@Repository
public class PlannerDaoImpl implements PlannerDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlannerDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	private KeyHolder keyHolder;
	
	private final String INSERT_PLANNER_SQL = "INSERT INTO planner(account_id, title, plan_date_start, plan_date_end, create_date, update_date)"
			+ "VALUES(?, ?, ?, ?, now(), now());";
	private final String FIND_SQL = "SELECT p.planner_id, p.account_id, p.title, p.plan_date_start, p.plan_date_end, p.like_count, p.create_date, p.update_date FROM planner AS p WHERE p.planner_id = ?;";
	private final String FINDS_SQL = "SELECT p.planner_id, p.account_id, p.plan_date_start, p.plan_date_end, p.like_count, p.create_date, p.update_date FROM planner AS p WHERE p.account_id = ?;";
	private final String FIND_ALL_SQL = "SELECT p.planner_id, p.account_id, p.title, p.plan_date_start, p.plan_date_end, p.like_count, p.create_date, p.update_date FROM planner AS p;";
	private final String UPDATE_PLANNER_SQL = "UPDATE planner AS p SET p.title = ?, p.plan_date_start = ?, p.plan_date_end = ?, p.update_date = NOW() WHERE p.planner_id = ?;";
	private final String DELETE_PLANNER_SQL = "DELETE FROM planner WHERE planner.planner_id = ?;";
	
	private final String FIND_PLANNER_BY_PLANNER_ID_SQL = "select A.planner_id, A.account_id, A.title, A.plan_date_start, A.plan_date_end, A.like_count, A.create_date, A.update_date, " 
			+"C.email, D.plan_id, D.plan_date, E.location_id, E.location_content_id, E.location_image, E.location_transportation, E.plan_id " 
			+"from planner as A " 
			+"left join plan_member AS B ON B.planner_id = A.planner_id " 
			+"left join account AS C ON C.account_id = B.account_id " 
			+"left join plan as D on A.planner_id = D.planner_id " 
			+"left join plan_location as E on D.plan_id = E.plan_id " 
			+"where A.planner_id = ?";
	private final String FIND_PLANNER_BY_ACCOUNT_ID_SQL = "select A.planner_id, A.account_id, A.title, A.plan_date_start, A.plan_date_end, A.like_count, A.create_date, A.update_date, "
			+ "B.plan_id, B.plan_date, "
			+ "C.location_id, C.location_content_id, C.location_image, C.location_transportation, C.plan_id "
			+ "from planner as A "
			+ "left join plan as B on A.planner_id = B.planner_id "
			+ "left join plan_location as C on B.plan_id = C.plan_id "
			+ "where A.account_id = ?;";
	
	private final String INSERT_PLANMEMBER_SQL = "INSERT INTO plan_member(planner_id, account_id, invite_state, invite_date) VALUES(?, ?, 2, NOW());";
	private final String FINDS_PLANMEMBER_SQL = "SELECT plan_member.plan_member_id, plan_member.planner_id, plan_member.account_id FROM plan_member WHERE planner_id = ?;";
	private final String DELETE_PLANMEMBER_SQL = "DELETE FROM plan_member WHERE planner_id = ? AND account_id = ?;";
	private final String UPDATE_PLANMEMBER_ACCEPT_INVITE = "UPDATE plan_member SET invite_state = 1 WHERE planner_id = ? AND account_id = ?;";
	
	private final String INSERT_PlAN_SQL = "INSERT INTO plan (plan_date, planner_id) VALUES(?, ?);";
	private final String FINDS_PLAN_SQL = "SELECT plan.plan_id, plan.plan_date, plan.planner_id FROM plan WHERE plan.planner_id = ?;";
	private final String UPDATE_PLAN_SQL = "UPDATE plan SET plan.plan_date = ? WHERE plan.plan_id = ?;";
	private final String DELETE_PLAN_SQL = "DELETE FROM plan WHERE plan.planner_id = ? AND plan.plan_id = ?;";
	
	private final String INSERT_PLANLOCATION_SQL = "INSERT INTO plan_location(location_content_id, location_image, location_transportation, plan_id) VALUES(?, ?, ?, ?);";
	private final String FINDS_PLANLOCATION_SQL = "\"SELECT plan_location.location_id, plan_location.location_content_id, plan_location.location_image, plan_location.location_transportation, plan_location.plan_id \"\r\n"
			+ "FROM plan_location "
			+ "WHERE plan_location.plan_id = ?;";
	private final String UPDATE_PLANLOCATION_SQL = "UPDATE plan_location SET plan_location.location_transportation = ? WHERE plan_location.location_id = ?;";
	private final String DELETE_PLANLOCATION_SQL = "DELETE FROM plan_location WHERE plan_location.plan_id = ? AND plan_location.location_id = ?;";
	
	
	public PlannerDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.keyHolder = new GeneratedKeyHolder();
	}

	@Override
	public int insertPlanner(PlannerDto plannerDto) {
		int result = jdbcTemplate.update(conn -> {
			PreparedStatement ps = conn.prepareStatement(INSERT_PLANNER_SQL, new String[] {"planner_id"});
			ps.setInt(1, plannerDto.getAccountId());
			ps.setString(2, plannerDto.getTitle());
			ps.setTimestamp(3, Timestamp.valueOf(plannerDto.getPlanDateStart()));
			ps.setTimestamp(4, Timestamp.valueOf(plannerDto.getPlanDateEnd()));
			return ps;
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public PlannerDto findPlannerByPlannerIda(int plannerId) {
		return jdbcTemplate.query(FIND_PLANNER_BY_PLANNER_ID_SQL, new ResultSetExtractor<PlannerDto>() {
			@Override
			public PlannerDto extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<String> members = new ArrayList<String>();
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
								.setPlanMemberEmails(members)
								.build();
					}
					
					String member = rs.getString("email");
					boolean memberCheck = members.stream().anyMatch((m) -> m.equals(member));
					if(!memberCheck) {
						members.add(member);
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
							.setLocationTransportation(rs.getInt("location_transportation"))
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
	public PlannerDto findPlannerByPlannerId(int plannerId) {
		return jdbcTemplate.queryForObject(FIND_SQL, new PlannerRowMapper(), plannerId);
	}

//	@Override
//	public List<PlannerDto> findPlannersByAccountId(int accountId) {
//		return jdbcTemplate.query(FIND_PLANNER_BY_ACCOUNT_ID_SQL, new ResultSetExtractor<List<PlannerDto>>() {
//			@Override
//			public List<PlannerDto> extractData(ResultSet rs) throws SQLException, DataAccessException {
//				List<PlannerDto> planners = new ArrayList<PlannerDto>();
//				List<PlanDto> plans = null;
//				List<PlanLocationDto> planLocations = null;
//				
//				int latestPlannerId = 0;
//				int latestPlanId = 0;
//				
//				while(rs.next()) {
//					int plannerId = rs.getInt("planner_id");
//					int planId = rs.getInt("plan_id");
//					
//					if(latestPlannerId != plannerId) {
//						latestPlannerId = plannerId;
//						plans = new ArrayList<PlanDto>();
//						
//						PlannerDto plannerDto = new PlannerDto.Builder()
//								.setPlannerId(rs.getInt("planner_id"))
//								.setAccountId(rs.getInt("account_id"))
//								.setTitle(rs.getString("title"))
//								.setPlanDateStart(rs.getTimestamp("plan_date_start").toLocalDateTime())
//								.setPlanDateEnd(rs.getTimestamp("plan_date_end").toLocalDateTime())
//								.setLikeCount(rs.getInt("like_count"))
//								.setPlans(plans)
//								.build();
//						planners.add(plannerDto);
//					}
//					
//					if(latestPlanId != planId) {
//						latestPlanId = planId;
//						planLocations = new ArrayList<PlanLocationDto>();
//						
//						PlanDto newPlan = new PlanDto.Builder()
//								.setPlanId(planId)
//								.setPlanDate(rs.getTimestamp("plan_date").toLocalDateTime())
//								.setPlannerId(rs.getInt("planner_id"))
//								.setPlanLocations(planLocations)
//								.build();
//						plans.add(newPlan);
//					}
//					
//					PlanLocationDto newPlanLocation = new PlanLocationDto.Builder()
//							.setLocationId(rs.getInt("location_id"))
//							.setLocationContetntId(rs.getInt("location_content_id"))
//							.setLocationImage(rs.getString("location_image"))
//							.setLocationTranspotation(rs.getInt("location_transportation"))
//							.setPlanId(rs.getInt("plan_id"))
//							.build();
//					planLocations.add(newPlanLocation);	
//				}
//				
//				return planners;
//			}
//		}, accountId);
//		//return jdbcTemplate.query(FINDS_SQL, new PlannerRowMapper(), accountId);
//	}
	
	@Override
	public List<PlannerDto> findPlannersByAccountId(int accountId) {
		return jdbcTemplate.query(FINDS_SQL, new PlannerRowMapper(), accountId);
	}

	@Override
	public List<PlannerDto> findPlannerAll() {
		return jdbcTemplate.query(FIND_ALL_SQL, new PlannerRowMapper());
	}

	@Override
	public int updatePlanner(int plannerId, PlannerDto plannerDto) {
		int result = jdbcTemplate.update(UPDATE_PLANNER_SQL, plannerDto.getTitle(), plannerDto.getPlanDateStart(), plannerDto.getPlanDateEnd(), plannerId);
		return result;
	}

	@Override
	public int deletePlanner(int plannerId) {
		int result = jdbcTemplate.update(DELETE_PLANNER_SQL, plannerId);
		return result;
	}
	
	@Override
	public int insertPlanMember(int plannerId, int accountId) {
		int result = jdbcTemplate.update(INSERT_PLANMEMBER_SQL, plannerId, accountId);
		return result;
	}

	@Override
	public List<PlanMemberDto> findMembersByPlannerId(int plannerId) {
		List<PlanMemberDto> members = jdbcTemplate.query(FINDS_PLANMEMBER_SQL, new PlanMemberRowMapper(), plannerId);
		return members;
	}

	@Override
	public int deletePlanMember(int plannerId, int accountId) {
		int result = jdbcTemplate.update(DELETE_PLANMEMBER_SQL, plannerId, accountId);
		return result;
	}

	@Override
	public int acceptInvitation(int plannerId, int accountId) {
		int result = jdbcTemplate.update(UPDATE_PLANMEMBER_ACCEPT_INVITE, plannerId, accountId);
		return result;
	}

	@Override
	public int insertPlan(PlanDto planDto) {
		int result = jdbcTemplate.update(conn -> {
			PreparedStatement ps = conn.prepareStatement(INSERT_PlAN_SQL, new String[] {"plan_id"});
			ps.setTimestamp(1, Timestamp.valueOf(planDto.getPlanDate()));
			ps.setInt(2, planDto.getPlannerId());
			return ps;
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public List<PlanDto> findPlansByPlannerId(int plannerId) {
		return jdbcTemplate.query(FINDS_PLAN_SQL, new PlanRowMapper(), plannerId);
	}

	@Override
	public int updatePlan(PlanDto planDto) {
		int result = jdbcTemplate.update(UPDATE_PLAN_SQL, planDto.getPlanDate());
		return result;
	}

	@Override
	public int deletePlan(int plannerId, int planId) {
		int result = jdbcTemplate.update(DELETE_PLAN_SQL, plannerId, planId);
		return result;
	}

	@Override
	public int insertPlanLocation(PlanLocationDto planLocationDto) {
		//int result = jdbcTemplate.update(INSERT_PLANLOCATION_SQL, planLocationDto.getLocationContetntId(), planLocationDto.getLocationImage(), planLocationDto.getLocationTranspotation(), planLocationDto.getPlanId());
		int result = jdbcTemplate.update(conn -> {
			PreparedStatement ps = conn.prepareStatement(INSERT_PLANLOCATION_SQL, new String[] {"plan_location_id"});
			ps.setInt(1, planLocationDto.getLocationContetntId());
			ps.setString(2, planLocationDto.getLocationImage());
			ps.setInt(3, planLocationDto.getLocationTransportation());
			ps.setInt(4, planLocationDto.getPlanId());
			return ps;
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public List<PlanLocationDto> findPlanLocationsByPlanId(int planId) {
		return jdbcTemplate.query(FINDS_PLANLOCATION_SQL, new PlanLocationRowMapper(), planId);
	}

	@Override
	public int updatePlanLocation(PlanLocationDto planLocationDto) {
		int result = jdbcTemplate.update(UPDATE_PLANLOCATION_SQL, planLocationDto.getLocationTransportation(), planLocationDto.getPlanId());
		return result;
	}

	@Override
	public int deletePlanLocation(int planId, int locationId) {
		int result = jdbcTemplate.update(DELETE_PLANLOCATION_SQL, planId, locationId);
		return result;
	}
}
