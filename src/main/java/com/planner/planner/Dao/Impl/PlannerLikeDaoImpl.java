package com.planner.planner.Dao.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.planner.planner.Common.PageInfo;
import com.planner.planner.Common.SortCriteria;
import com.planner.planner.Dao.PlannerLikeDao;
import com.planner.planner.Dto.PlannerDto;
import com.planner.planner.RowMapper.PlannerRowMapper;

@Repository
public class PlannerLikeDaoImpl implements PlannerLikeDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlannerLikeDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	
	private final String INSERT_PLANNERLIKE_SQL = "INSERT INTO planner_like(account_id, planner_id, like_date) VALUES(?, ?, NOW());";
	private final String DELETE_PLANNERLIKE_SQL = "DELETE FROM planner_like WHERE account_id = ? AND planner_id = ?;";
	private final String PLANNERLIKE_COUNT_SQL = "SELECT count(*) as count FROM planner_like AS PL WHERE PL.account_id = ? AND PL.planner_id = ?;";

	private final String FINDS_PLANNER_LIKE__PLANNER_ID_LIST = "SELECT planner_id FROM planner_like WHERE planner_id IN (%s) AND account_id = ?;";

	public PlannerLikeDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public int plannerLike(int accountId, int plannerId) {
		int result = jdbcTemplate.update(INSERT_PLANNERLIKE_SQL, accountId, plannerId);
		return result;
	}

	@Override
	public int plannerUnLike(int accountId, int plannerId) {
		int result = jdbcTemplate.update(DELETE_PLANNERLIKE_SQL, accountId, plannerId);
		return result;
	}

	@Override
	public boolean isLike(int accountId, int plannerId) {
		try {
			int result = jdbcTemplate.queryForObject(PLANNERLIKE_COUNT_SQL, Integer.class, accountId, plannerId);
			return result == 0 ? false : true;
		} catch (EmptyResultDataAccessException e) {
			return false;
		}
	}

	@Override
	public List<Integer> returnLikePlannerIdList(int accountId, List<Integer> plannerIdList) {
		String plannerIds = plannerIdList.stream().map(String::valueOf).collect(Collectors.joining(","));
		String sql = String.format(FINDS_PLANNER_LIKE__PLANNER_ID_LIST, plannerIds);
		List<Integer> list = jdbcTemplate.queryForList(sql, Integer.class, accountId);
		
		return list;
	}
}
