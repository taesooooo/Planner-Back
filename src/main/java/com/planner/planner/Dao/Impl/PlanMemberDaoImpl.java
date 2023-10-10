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

	private final String INSERT_PLAN_MEMBER_SQL = "INSERT INTO plan_member(planner_id, account_id, invite_state, invite_date) VALUES(?, ?, 2, NOW());";
	private final String FINDS_PLAN_MEMBER_SQL = "SELECT plan_member.plan_member_id, plan_member.planner_id, plan_member.account_id FROM plan_member WHERE planner_id = ?;";
	private final String DELETE_PLAN_MEMBER_SQL = "DELETE FROM plan_member WHERE planner_id = ? AND account_id = ?;";
	private final String UPDATE_PLAN_MEMBER_ACCEPT_INVITE_SQL = "UPDATE plan_member SET invite_state = 1 WHERE planner_id = ? AND account_id = ?;";
	private final String UPDATE_PLAN_MEMBER_REJECT_INVITE_SQL = "UPDATE plan_member SET invite_state = 0 WHERE planner_id = ? AND account_id = ?;";
	

	public PlanMemberDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public int insertPlanMember(int plannerId, int accountId) throws Exception {
		int result = jdbcTemplate.update(INSERT_PLAN_MEMBER_SQL, plannerId, accountId);
		return result;
	}

	@Override
	public List<PlanMemberDto> findMembersByPlannerId(int plannerId) throws Exception {
		try {
			List<PlanMemberDto> members = jdbcTemplate.query(FINDS_PLAN_MEMBER_SQL, new PlanMemberRowMapper(), plannerId);
			return members;			
		}
		catch(DataAccessException e) {
			return null;
		}
	}

	@Override
	public void deletePlanMember(int plannerId, int accountId) throws Exception {
		int result = jdbcTemplate.update(DELETE_PLAN_MEMBER_SQL, plannerId, accountId);
	}

	@Override
	public void inviteAcceptState(int plannerId, int accountId) throws Exception {
		int result = jdbcTemplate.update(UPDATE_PLAN_MEMBER_ACCEPT_INVITE_SQL, plannerId, accountId);
	}

	@Override
	public void inviteRejectState(int plannerId, int accountId) throws Exception {
		int result = jdbcTemplate.update(UPDATE_PLAN_MEMBER_REJECT_INVITE_SQL, plannerId, accountId);
	}

}
