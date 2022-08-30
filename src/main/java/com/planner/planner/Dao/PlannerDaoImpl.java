package com.planner.planner.Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.planner.planner.Dto.PlannerDto;
import com.planner.planner.Entity.Planner;
import com.planner.planner.RowMapper.PlannerRowMapper;

@Repository
public class PlannerDaoImpl implements PlannerDao {
	
	private static final Logger logger = LoggerFactory.getLogger(PlannerDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private String createSQL = "INSERT INTO planner (account_id, title, plan_date_start, plan_date_end, member_count, member, plan, create_date, update_date) VALUES (?, ?, ?, ?, ?, ?, ?, now(), now());";
	private String readSQL = "SELECT planner_id, account_id, title, plan_date_start, plan_date_end, member_count, member, plan, like_count, create_date, update_date "
			+ "FROM planner WHERE planner_id = ?;";
	private String updateSQL = "UPDATE `planner` SET title = ?, plan_date_start = ?, plan_date_end = ?, member_count = ?, member = ?, plan = ?, update_date = now() WHERE planner_id = ?;";
	private String deleteSQL = "DELETE FROM planner WHERE planner_id = ?;";
	private String likeSQL = "UPDATE planner SET like_count = like_count + 1 WHERE planner_id = ?";
	private String likeAddSQL = "INSERT INTO `plannerlike` (planner_id, account_id, like_date) VALUES(?, ?, now());";
	private String likeCancelSQL = "UPDATE planner SET like_count = like_count - 1 WHERE planner_id = ?";
	private String likeDeleteSQL = "DELETE FROM `plannerlike` WHERE planner_id = ? and account_id = ?;";
	private String readAllSQL = "SELECT planner_id, title, plan_date_start, plan_date_end, like_count FROM planner;";
	
	@Override
	public boolean create(Planner planner) {
		int result = jdbcTemplate.update(createSQL,planner.getAccountId(),planner.getTitle(), planner.getPlanDateStart(),planner.getPlanDateEnd(), planner.getMemberCount(),planner.getMember(), planner.getPlan());
		return result > 0 ? true : false;
	}

	@Override
	public PlannerDto read(int plannerId) {
		Planner plan = jdbcTemplate.queryForObject(readSQL, new RowMapper<Planner>() {
			@Override
			public Planner mapRow(ResultSet rs, int rowNum) throws SQLException {
				Planner planner = new Planner.Builder()
						.setPlannerId(rs.getInt(1))
						.setAccountId(rs.getInt(2))
						.setTitle(rs.getString(3))
						.setPlanDateStart(rs.getTimestamp(4)
						.toLocalDateTime().toLocalDate())
						.setPlanDateEnd(rs.getTimestamp((5))
						.toLocalDateTime().toLocalDate())
						.setMemberCount(rs.getInt(6))
						.setMember(rs.getString(7)).setPlan(rs.getString(8))
						.setRecommendCount(rs.getInt(9))
						.setCreateDate(rs.getTimestamp(10)
						.toLocalDateTime())
						.setUpdateDate(rs.getTimestamp(11)
						.toLocalDateTime()).build();
				return planner;
			}
		}, plannerId);
		if(plan != null) {
			return plan.toDto();
		}
		return null;		
	}

	@Override
	public boolean update(Planner planner) {
		int result = jdbcTemplate.update(updateSQL,planner.getTitle(), planner.getPlanDateStart(),planner.getPlanDateEnd(), planner.getMemberCount(),  planner.getMember()
				, planner.getPlan(),planner.getPlannerId());
		return result > 0 ? true : false;
	}

	@Override
	public boolean delete(int plannerId) {
		int result = jdbcTemplate.update(deleteSQL,plannerId);
		return result > 0 ? true : false;
	}

	@Override
	public boolean likePlanner(int plannerId, int accountId) {
		int result = jdbcTemplate.update(likeAddSQL, plannerId, accountId);
		return result > 0 ? true : false;
	}

	@Override
	public boolean like(int plannerId) {
		int result = jdbcTemplate.update(likeSQL, plannerId);
		return result > 0 ? true : false;
	}

	@Override
	public boolean likeCancel(int plannerId) {
		int result = jdbcTemplate.update(likeCancelSQL, plannerId);
		return result > 0 ? true : false;
	}

	@Override
	public boolean likeDelete(int plannerId, int accountId) {
		int result = jdbcTemplate.update(likeDeleteSQL, plannerId, accountId);
		return result > 0 ? true : false;
	}

	@Override
	public List<Planner> getAllPlanners() {
		return jdbcTemplate.query(readAllSQL,new RowMapper<Planner>() {
			@Override
			public Planner mapRow(ResultSet rs, int rowNum) throws SQLException {
				Planner planner = new Planner.Builder()
						.setPlannerId(rs.getInt(1))
						.setTitle(rs.getString(2))
						.setPlanDateStart(rs.getTimestamp(3).toLocalDateTime().toLocalDate())
						.setPlanDateEnd(rs.getTimestamp((4)).toLocalDateTime().toLocalDate())
						.build();
				return planner;
			}
		});
	}
}
