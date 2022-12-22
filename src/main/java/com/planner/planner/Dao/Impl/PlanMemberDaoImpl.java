package com.planner.planner.Dao.Impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.planner.planner.Dao.PlanMemberDao;
import com.planner.planner.Entity.PlanMember;
import com.planner.planner.RowMapper.PlanMemberRowMapper;

@Repository
public class PlanMemberDaoImpl implements PlanMemberDao {
	private static final Logger logger = LoggerFactory.getLogger(PlanMemberDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	
	private static final String INSERT_SQL = "INSERT INTO plan_member(planner_id, account_id) VALUES(?, ?);";
	private static final String FIND_SQL = "SELECT plan_member.plan_member_id, plan_member.planner_id, plan_member.account_id FROM plan_member WHERE plan_member.plan_member_id = ?;";
	private static final String FINDS_SQL = "SELECT plan_member.plan_member_id, plan_member.planner_id, plan_member.account_id FROM plan_member WHERE planner_id = ?;";
	private static final String UPDATE_SQL = "";
	private static final String DELETE_SQL = "DELETE FROM plan_member WHERE planner_id = ?;";
	
	public PlanMemberDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public boolean insertPlanMember(PlanMember planMember) {
		int result = jdbcTemplate.update(INSERT_SQL, planMember.getPlannerId(), planMember.getAccountId());
		return result > 0 ? true : false;
	}

	@Override
	public PlanMember findPlanMemberByPlannerId(int planMemberId) {
		return jdbcTemplate.queryForObject(FIND_SQL, new PlanMemberRowMapper(), planMemberId);
	}

	@Override
	public List<PlanMember> findPlanMembersByPlannerId(int plannerId) {
		return jdbcTemplate.query(FINDS_SQL, new PlanMemberRowMapper(), plannerId);
	}

//	@Override
//	public boolean updatePlanMember(PlanMember planMember) {
//		return false;
//	}

	@Override
	public boolean deletePlanMember(int planMemberId) {
		int result = jdbcTemplate.update(DELETE_SQL, planMemberId);
		return result > 0 ? true : false;
	}
}
