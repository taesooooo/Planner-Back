package com.planner.planner.Dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
	private String readSQL = "SELECT `planner_id`, `account_id`, `plan_date_start`, `plan_date_end`, `member_count`, `member`, `plan`, `like_count`, `create_date`, `update_date` "
			+ "FROM `planner` WHERE planner_id = ?;";
	private String updateSQL = "UPDATE `planner` SET plan_date_start = ?, plan_date_end = ?, member_count = ?, member = ?, plan = ?, update_date = now() WHERE planner_id = ?;";
	private String deleteSQL = "DELETE FROM `planner` WHERE planner_id = ?;";
	private String likeAddSQL = "INSERT INTO like_planner (planner_id, account_id) VALUES(?, ?);";
	private String likeSQL = "UPDATE `planner` SET like_count = +1 WHERE planner_id = ?";
	@Override
	public boolean create(Planner planner) {
		int result = jdbcTemplate.update(createSQL,planner.getAccountId(),planner.getTitle(), planner.getPlanDateStart(),planner.getPlanDateEnd(), planner.getMemberCount(),planner.getMember(), planner.getPlan());
		return result > 0 ? true : false;
	}

	@Override
	public PlannerDto read(int plannerId) {
		Planner plan = jdbcTemplate.queryForObject(readSQL, new PlannerRowMapper(), plannerId);
		if(plan != null) {
			return plan.toDto();
		}
		return null;		
	}

	@Override
	public boolean update(Planner planner) {
		int result = jdbcTemplate.update(updateSQL,planner.getPlanDateStart(),planner.getPlanDateEnd(), planner.getMemberCount(),  planner.getMember()
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
}
