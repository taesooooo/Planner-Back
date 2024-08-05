package com.planner.planner.Dao.Impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.planner.planner.Dao.PlanMemberDao;
import com.planner.planner.Dto.PlanMemberDto;
import com.planner.planner.RowMapper.PlanMemberRowMapper;

@Repository
public class PlanMemberDaoImpl implements PlanMemberDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlanMemberDaoImpl.class);

	private JdbcTemplate jdbcTemplate;

	private final String INSERT_PLAN_MEMBER_SQL = "INSERT INTO plan_member(planner_id, account_id) VALUES(?, ?);";
	private final String FINDS_PLAN_MEMBER_SQL = "SELECT plan_member.plan_member_id, plan_member.planner_id, plan_member.account_id FROM plan_member WHERE planner_id = ?;";
	private final String DELETE_PLAN_MEMBER_SQL = "DELETE FROM plan_member WHERE planner_id = ? AND account_id = ?;";
	
	public PlanMemberDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public int insertPlanMember(int plannerId, int accountId) throws Exception {
		int result = jdbcTemplate.update(INSERT_PLAN_MEMBER_SQL, plannerId, accountId);
		return result;
	}

	@Override
	public List<PlanMemberDto> findPlanMemberListByPlannerId(int plannerId) throws Exception {
		try {
			List<PlanMemberDto> members = jdbcTemplate.query(FINDS_PLAN_MEMBER_SQL, new PlanMemberRowMapper(), plannerId);
			return members;			
		}
		catch(DataAccessException e) {
			return null;
		}
	}

	@Override
	public int deletePlanMember(int plannerId, int accountId) throws Exception {
		int result = jdbcTemplate.update(DELETE_PLAN_MEMBER_SQL, plannerId, accountId);
		return result;
	}
}
