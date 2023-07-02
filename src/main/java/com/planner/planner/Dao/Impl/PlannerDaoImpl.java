package com.planner.planner.Dao.Impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.planner.planner.Common.PageInfo;
import com.planner.planner.Common.SortCriteria;
import com.planner.planner.Dao.PlannerDao;
import com.planner.planner.Dto.CommonRequestParamDto;
import com.planner.planner.Dto.PlanDto;
import com.planner.planner.Dto.PlanLocationDto;
import com.planner.planner.Dto.PlanMemberDto;
import com.planner.planner.Dto.PlanMemoDto;
import com.planner.planner.Dto.PlannerDto;
import com.planner.planner.RowMapper.PlanLocationRowMapper;
import com.planner.planner.RowMapper.PlanMemberRowMapper;
import com.planner.planner.RowMapper.PlanMemoRowMapper;
import com.planner.planner.RowMapper.PlanRowMapper;
import com.planner.planner.RowMapper.PlannerNoLikeIdRowMapper;
import com.planner.planner.RowMapper.PlannerResultSetExtrator;
import com.planner.planner.RowMapper.PlannerRowMapper;

@Repository
public class PlannerDaoImpl implements PlannerDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlannerDaoImpl.class);

	private JdbcTemplate jdbcTemplate;
	private KeyHolder keyHolder;

	private final String INSERT_PLANNER_SQL = "INSERT INTO planner(account_id, creator, title, plan_date_start, plan_date_end, expense, member_count, member_type_id, create_date, update_date)"
			+ "VALUES(?, ?, ?, ?, ?, ? ,? ,? , now(), now());";
//	private final String FIND_SQL = "SELECT P.planner_id, P.account_id, P.creator, P.title, P.plan_date_start, P.plan_date_end, P.expense, P.member_count, P.member_type_id, P.like_count, P.create_date, P.update_date FROM planner AS p WHERE p.planner_id = ?;";
	// 동적 쿼리
	private final String FIND_ALL_BY_ACCOUNT_ID_SQL = "SELECT P.planner_id, P.account_id, P.creator, P.title, P.plan_date_start, P.plan_date_end, P.expense, P.member_count, P.member_type_id, P.like_count, P.create_date, P.update_date, PL.like_id ";
	// 동적 쿼리
	private final String FIND_ALL_SQL = "SELECT P.planner_id, P.account_id, P.creator, P.title, P.plan_date_start, P.plan_date_end, P.expense, P.member_count, P.member_type_id, P.like_count, P.create_date, P.update_date ";
	private final String UPDATE_PLANNER_SQL = "UPDATE planner AS P SET P.title = ?, P.plan_date_start = ?, P.plan_date_end = ?, P.expense = ?, P.member_count = ?, P.member_type_id = ?, P.update_date = NOW() WHERE P.planner_id = ?;";
	private final String DELETE_PLANNER_SQL = "DELETE FROM planner WHERE planner_id = ?;";

	private final String FIND_PLANNER_BY_PLANNER_ID_JOIN_SQL = "SELECT A.planner_id, A.account_id, A.creator, A.title, A.plan_date_start, A.plan_date_end, A.expense, A.member_count, A.member_type_id, "
			+ "A.like_count, A.create_date, A.update_date, "
			+ "C.nickname, M.memo_id, M.memo_title, M.memo_content, M.memo_create_date, M.memo_update_date, D.plan_date, D.plan_index, D.plan_id,  "
			+ "E.location_id, E.location_name, E.location_content_id, E.location_image, E.location_addr, E.location_mapx, E.location_mapy, E.location_transportation, E.location_index, E.plan_id, "
			+ "PL.like_id "
			+ "FROM planner AS A " + "LEFT JOIN plan_member AS B ON A.planner_id = B.planner_id "
			+ "LEFT JOIN account AS C ON C.account_id = B.account_id "
			+ "LEFT JOIN plan_memo AS M ON A.planner_id = M.planner_id "
			+ "LEFT JOIN plan AS D ON A.planner_id = D.planner_id "
			+ "LEFT JOIN planner_like AS PL ON A.planner_id = PL.planner_id AND A.account_id = PL.account_id "
			+ "LEFT JOIN plan_location AS E ON D.plan_id = E.plan_id " 
			+ "WHERE A.planner_id = ?;";

	private final String FIND_TOTAL_COUNT_SQL = "SELECT count(*) AS total_count FROM planner;";
	private final String FIND_TOTAL_COUNT_ACCOUNT_ID_SQL = "SELECT count(*) AS total_count FROM planner WHERE account_id = ?;";
	private final String FIND_TOTAL_COUNT_LIKE_SQL = "SELECT count(*) AS total_count FROM planner_like WHERE account_id = ?;";
	private final String FIND_TOTAL_COUNT_LIKE_KEYWORD_SQL = "SELECT count(*) as count FROM planner_like AS PL "
			+ "INNER JOIN planner AS P ON P.planner_id = PL.planner_id "
			+ "WHERE PL.account_id = ? AND P.title LIKE \"%%%s%%\";";
	private final String FIND_TOTAL_COUNT_KEYWORD_SQL = "SELECT count(*) AS total_count FROM planner WHERE title LIKE \"%%%s%%\";";
	private final String FIND_TOTAL_COUNT_KEYWORD_ACCOUNT_ID_SQL = "SELECT count(*) AS total_count FROM planner "
			+ "WHERE account_id = ? AND title LIKE \"%%%s%%\";";
	

	private final String INSERT_PLANMEMBER_SQL = "INSERT INTO plan_member(planner_id, account_id, invite_state, invite_date) VALUES(?, ?, 2, NOW());";
	private final String FINDS_PLANMEMBER_SQL = "SELECT plan_member.plan_member_id, plan_member.planner_id, plan_member.account_id FROM plan_member WHERE planner_id = ?;";
	private final String DELETE_PLANMEMBER_SQL = "DELETE FROM plan_member WHERE planner_id = ? AND account_id = ?;";
	private final String UPDATE_PLANMEMBER_ACCEPT_INVITE_SQL = "UPDATE plan_member SET invite_state = 1 WHERE planner_id = ? AND account_id = ?;";

	private final String INSERT_PLANMEMO_SQL = "INSERT INTO plan_memo(memo_title, memo_content, memo_create_date, memo_update_date, planner_id) VALUES(?, ?, NOW(), NOW(), ?);";
	private final String FINDS_PLANMEMO_SQL = "SELECT M.memo_id, M.memo_title, M.memo_content, M.memo_create_date, M.memo_update_date FROM plan_memo AS M WHERE M.planner_id = ?;";
	private final String UPDATE_PLANMEMO_SQL = "UPDATE plan_memo AS M SET M.memo_title = ?, M.memo_content = ?, M.memo_update_date = NOW() WHERE M.memo_id = ?;";
	private final String DELETE_PLANMEMO_SQL = "DELETE FROM plan_memo WHERE memo_id = ?;";

	private final String INSERT_PlAN_SQL = "INSERT INTO plan (plan_date, planner_id, plan_index)  VALUES(?, ?, ?);";
	private final String FINDS_PLAN_SQL = "SELECT plan.plan_id, plan.plan_date, plan.plan_index, plan.planner_id FROM plan WHERE plan.planner_id = ? ORDER BY plan_index;";
	private final String UPDATE_PLAN_SQL = "UPDATE plan SET plan.plan_date = ?, plan.plan_index = ? WHERE plan.plan_id = ?;";
	private final String DELETE_PLAN_SQL = "DELETE FROM plan WHERE plan.plan_id = ?;";

	private final String INSERT_PLANLOCATION_SQL = "INSERT INTO plan_location(location_name, location_content_id, location_image, location_addr, location_mapx, location_mapy, location_transportation, location_index, plan_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);";
	private final String FINDS_PLANLOCATION_SQL = "SELECT plan_location.location_id, plan_location.location_name, plan_location.location_content_id, plan_location.location_image, plan_location.location_addr, plan_location.location_mapx, plan_location.location_mapy, plan_location.location_transportation, plan_location.location_index, plan_location.plan_id "
			+ "FROM plan_location " 
			+ "WHERE plan_location.plan_id = ? "
			+ "ORDER BY plan_location.location_index;";
	private final String UPDATE_PLANLOCATION_SQL = "UPDATE plan_location AS PL SET PL.location_name = ?, PL.location_content_id = ?, PL.location_image = ? , PL.location_addr = ?, PL.location_mapx = ?, PL.location_mapy = ?, PL.location_transportation = ?, PL.location_index = ? WHERE PL.location_id = ?;";
	private final String DELETE_PLANLOCATION_SQL = "DELETE FROM plan_location WHERE plan_location.location_id = ?;";

	private final String INSERT_PLANNERLIKE_SQL = "INSERT INTO planner_like(account_id, planner_id, like_date) VALUES(?, ?, NOW());";
	private final String DELETE_PLANNERLIKE_SQL = "DELETE FROM planner_like WHERE account_id = ? AND planner_id = ?;";
	private final String PLANNERLIKE_COUNT_SQL = "SELECT count(*) as count FROM planner_like AS PL WHERE PL.account_id = ? AND PL.planner_id = ?;";
	// 동적 쿼리
	private final String FINDS_PLANNERLIKE_JOIN_SQL = "SELECT P.planner_id, P.account_id, P.creator, P.title, P.plan_date_start, P.plan_date_end, P.expense, P.member_count, P.member_type_id, P.like_count, P.create_date, P.update_date, PL.like_id ";
	private final String FINDS_PLANNER_LIKE__PLANNER_ID_LIST = "SELECT planner_id FROM planner_like WHERE planner_id IN (%s) AND account_id = ?;";

	public PlannerDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.keyHolder = new GeneratedKeyHolder();
	}

	@Override
	public int insertPlanner(PlannerDto plannerDto) {
		int result = jdbcTemplate.update(conn -> {
			PreparedStatement ps = conn.prepareStatement(INSERT_PLANNER_SQL, new String[] { "planner_id" });
			ps.setInt(1, plannerDto.getAccountId());
			ps.setString(2, plannerDto.getCreator());
			ps.setString(3, plannerDto.getTitle());
			ps.setDate(4, Date.valueOf(plannerDto.getPlanDateStart()));
			ps.setDate(5, Date.valueOf(plannerDto.getPlanDateEnd()));
			ps.setInt(6, plannerDto.getExpense());
			ps.setInt(7, plannerDto.getMemberCount());
			ps.setInt(8, plannerDto.getMemberTypeId());
			return ps;
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public PlannerDto findPlannerByPlannerId(int plannerId) {
		return jdbcTemplate.query(FIND_PLANNER_BY_PLANNER_ID_JOIN_SQL, new PlannerResultSetExtrator(), plannerId);
	}

	@Override
	public List<PlannerDto> findPlannersByAccountId(int accountId, SortCriteria sortCriteria, String keyword, PageInfo pageInfo) {		
		StringBuilder sb = new StringBuilder(FIND_ALL_BY_ACCOUNT_ID_SQL);
		sb.append("FROM planner AS P ");
		sb.append("LEFT JOIN planner_like AS PL ON P.planner_id = PL.planner_id AND PL.account_id = ? ");
		
		if(keyword != null) {
			sb.append("WHERE p.account_id = ? AND P.title LIKE \"%").append(keyword).append("%\" ");
		}
		
		if(sortCriteria == SortCriteria.LATEST) {
			sb.append("ORDER BY P.planner_id DESC ");
		}
		else if(sortCriteria == SortCriteria.LIKECOUNT) {
			sb.append("ORDER BY P.like_count DESC ");
		}
		
		sb.append("LIMIT ?, ?;");
		
		return jdbcTemplate.query(sb.toString(), new PlannerRowMapper(), accountId, accountId, pageInfo.getPageOffSet(), pageInfo.getPageItemCount());
	}

	@Override
	public List<PlannerDto> findPlannerAll(Integer accountId, SortCriteria criteria, String keyword, PageInfo pageInfo) {
		StringBuilder sb = new StringBuilder(FIND_ALL_SQL);
		
		if(accountId != null) {
			sb.append(", PL.like_id ");
			sb.append("FROM planner AS P ");
			sb.append("LEFT JOIN planner_like AS PL ON P.planner_id = PL.planner_id AND PL.account_id = ? ");
		}
		else {
			sb.append("FROM planner AS P ");
		}
		
		if(keyword != null) {
			sb.append("WHERE P.title LIKE \"%").append(keyword).append("%\" ");
		}
		
		if(criteria == SortCriteria.LATEST) {
			sb.append("ORDER BY P.planner_id DESC ");
		}
		else if(criteria == SortCriteria.LIKECOUNT) {
			sb.append("ORDER BY P.like_count DESC ");
		}
		
		sb.append("LIMIT ?, ?;");
		
		if(accountId != null) {
			return jdbcTemplate.query(sb.toString(), new PlannerRowMapper(), accountId, pageInfo.getPageOffSet(), pageInfo.getPageItemCount());			
		}
		else {
			return jdbcTemplate.query(sb.toString(), new PlannerNoLikeIdRowMapper(), pageInfo.getPageOffSet(), pageInfo.getPageItemCount());
		}
	}

	@Override
	public int updatePlanner(int plannerId, PlannerDto plannerDto) {
		int result = jdbcTemplate.update(UPDATE_PLANNER_SQL, plannerDto.getTitle(), plannerDto.getPlanDateStart(),
				plannerDto.getPlanDateEnd(), plannerDto.getExpense(), plannerDto.getMemberCount(),
				plannerDto.getMemberTypeId(), plannerId);
		return result;
	}

	@Override
	public int deletePlanner(int plannerId) {
		int result = jdbcTemplate.update(DELETE_PLANNER_SQL, plannerId);
		return result;
	}

	@Override
	public int getTotalCount() {
		return jdbcTemplate.queryForObject(FIND_TOTAL_COUNT_SQL, Integer.class);
	}

	@Override
	public int getTotalCount(int accountId) {
		return jdbcTemplate.queryForObject(FIND_TOTAL_COUNT_ACCOUNT_ID_SQL, Integer.class, accountId);
	}

	@Override
	public int getTotalCountByLike(int accountId) {
		return jdbcTemplate.queryForObject(FIND_TOTAL_COUNT_LIKE_SQL, Integer.class, accountId);
	}
	
	@Override
	public int getTotalCountByLike(int accountId, String keyword) {
		String sql = String.format(FIND_TOTAL_COUNT_LIKE_KEYWORD_SQL, keyword);
		return jdbcTemplate.queryForObject(sql, Integer.class, accountId);
	}

	@Override
	public int getTotalCountByKeyword(String keyword) {
		String sql = String.format(FIND_TOTAL_COUNT_KEYWORD_SQL, keyword);
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
	
	@Override
	public int getTotalCountByKeyword(int accountId, String keyword) {
		String sql = String.format(FIND_TOTAL_COUNT_KEYWORD_ACCOUNT_ID_SQL, keyword);
		return jdbcTemplate.queryForObject(sql, Integer.class, accountId);
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
		int result = jdbcTemplate.update(UPDATE_PLANMEMBER_ACCEPT_INVITE_SQL, plannerId, accountId);
		return result;
	}

	@Override
	public int insertPlan(PlanDto planDto) {
		int result = jdbcTemplate.update(conn -> {
			PreparedStatement ps = conn.prepareStatement(INSERT_PlAN_SQL, new String[] { "plan_id" });
			ps.setDate(1, Date.valueOf(planDto.getPlanDate()));
			ps.setInt(2, planDto.getPlannerId());
			ps.setInt(3, planDto.getIndex());
			return ps;
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public List<PlanDto> findPlansByPlannerId(int plannerId) {
		return jdbcTemplate.query(FINDS_PLAN_SQL, new PlanRowMapper(), plannerId);
	}

	@Override
	public int updatePlan(int planId, PlanDto planDto) {
		int result = jdbcTemplate.update(UPDATE_PLAN_SQL, planDto.getPlanDate(), planDto.getIndex(), planId);
		return result;
	}

	@Override
	public int deletePlan(int planId) {
		int result = jdbcTemplate.update(DELETE_PLAN_SQL, planId);
		return result;
	}

	@Override
	public int insertPlanLocation(PlanLocationDto planLocationDto) {
		int result = jdbcTemplate.update(conn -> {
			PreparedStatement ps = conn.prepareStatement(INSERT_PLANLOCATION_SQL, new String[] { "plan_location_id" });
			ps.setString(1, planLocationDto.getLocationName());
			ps.setInt(2, planLocationDto.getLocationContentId());
			ps.setString(3, planLocationDto.getLocationImage());
			ps.setString(4, planLocationDto.getLocationAddr());
			ps.setDouble(5, planLocationDto.getLocationMapx());
			ps.setDouble(6, planLocationDto.getLocationMapy());
			ps.setInt(7, planLocationDto.getLocationTransportation());
			ps.setInt(8, planLocationDto.getIndex());
			ps.setInt(9, planLocationDto.getPlanId());
			return ps;
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public List<PlanLocationDto> findPlanLocationsByPlanId(int planId) {
		return jdbcTemplate.query(FINDS_PLANLOCATION_SQL, new PlanLocationRowMapper(), planId);
	}

	@Override
	public int updatePlanLocation(int planLocationId, PlanLocationDto planLocationDto) {
		int result = jdbcTemplate.update(UPDATE_PLANLOCATION_SQL, 
				planLocationDto.getLocationName(),
				planLocationDto.getLocationContentId(), 
				planLocationDto.getLocationImage(),
				planLocationDto.getLocationAddr(),
				planLocationDto.getLocationMapx(),
				planLocationDto.getLocationMapy(),
				planLocationDto.getLocationTransportation(),
				planLocationDto.getIndex(),
				planLocationDto.getLocationId());
		return result;
	}

	@Override
	public int deletePlanLocation(int planLocationId) {
		int result = jdbcTemplate.update(DELETE_PLANLOCATION_SQL, planLocationId);
		return result;
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

	@Override
	public List<PlannerDto> likePlannerList(int accountId, SortCriteria criteria, String keyword, PageInfo pageInfo) {
//		+ "FROM planner AS P " 
//				+ "LEFT JOIN planner_like AS PL ON P.planner_id = PL.planner_id "
//				+ "WHERE PL.account_id = ? ORDER BY PL.planner_id ASC LIMIT ?, ?;";
		
		StringBuilder sb = new StringBuilder(FINDS_PLANNERLIKE_JOIN_SQL);
		sb.append("FROM planner AS P ");
		sb.append("LEFT JOIN planner_like AS PL ON P.planner_id = PL.planner_id ");
		
		if(keyword != null) {
			sb.append("WHERE PL.account_id = ? AND P.title LIKE \"%").append(keyword).append("%\" ");
		}
		else {
			sb.append("WHERE PL.account_id = ? ");
		}
		
		if(criteria == SortCriteria.LATEST) {
			sb.append("ORDER BY P.planner_id DESC ");
		}
		else if(criteria == SortCriteria.LIKECOUNT) {
			sb.append("ORDER BY P.like_count DESC ");
		}
		
		sb.append("LIMIT ?, ?;");
		
		List<PlannerDto> list = jdbcTemplate.query(sb.toString(), new PlannerRowMapper(), accountId, pageInfo.getPageOffSet(), pageInfo.getPageItemCount());
		return list;
	}

}
