package com.planner.planner.Dao.Impl;

import java.sql.PreparedStatement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.planner.planner.Dao.PlanMemoDao;
import com.planner.planner.Dto.PlanMemoDto;
import com.planner.planner.RowMapper.PlanMemoRowMapper;

@Repository
public class PlanMemoDaoImpl implements PlanMemoDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlannerDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	private KeyHolder keyHolder;
	
	private final String INSERT_PLANMEMO_SQL = "INSERT INTO plan_memo(memo_title, memo_content, memo_create_date, memo_update_date, planner_id) VALUES(?, ?, NOW(), NOW(), ?);";
	private final String FINDS_PLANMEMO_SQL = "SELECT M.memo_id, M.memo_title, M.memo_content, M.memo_create_date, M.memo_update_date FROM plan_memo AS M WHERE M.planner_id = ?;";
	private final String UPDATE_PLANMEMO_SQL = "UPDATE plan_memo AS M SET M.memo_title = ?, M.memo_content = ?, M.memo_update_date = NOW() WHERE M.memo_id = ?;";
	private final String DELETE_PLANMEMO_SQL = "DELETE FROM plan_memo WHERE memo_id = ?;";
	
	
	public PlanMemoDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.keyHolder = new GeneratedKeyHolder();
	}

	@Override
	public int insertPlanMemo(int plannerId, PlanMemoDto planMemoDto) {
		int result = jdbcTemplate.update(conn -> {
			PreparedStatement ps = conn.prepareStatement(INSERT_PLANMEMO_SQL, new String[] { "memo_id" });
			ps.setString(1, planMemoDto.getTitle());
			ps.setString(2, planMemoDto.getContent());
			ps.setInt(3, plannerId);
			return ps;
		}, keyHolder);

		return keyHolder.getKey().intValue();
	}

	@Override
	public List<PlanMemoDto> findPlanMemoByPlannerId(int plannerId) {
		List<PlanMemoDto> list = jdbcTemplate.query(FINDS_PLANMEMO_SQL, new PlanMemoRowMapper(), plannerId);
		return list;
	}

	@Override
	public int updatePlanMemo(int planMemoId, PlanMemoDto planMemoDto) {
		int result = jdbcTemplate.update(UPDATE_PLANMEMO_SQL, planMemoDto.getTitle(), planMemoDto.getContent(),
				planMemoId);
		return result;
	}

	@Override
	public int deletePlanMemo(int planMemoId) {
		int result = jdbcTemplate.update(DELETE_PLANMEMO_SQL, planMemoId);
		return result;
	}
}
