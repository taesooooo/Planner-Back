package com.planner.planner.Dao.Impl;

import java.sql.Types;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.planner.planner.Common.PageInfo;
import com.planner.planner.Common.SortCriteria;
import com.planner.planner.Dao.PlannerDao;
import com.planner.planner.Dto.CommonRequestParamDto;
import com.planner.planner.Dto.PlannerDto;
import com.planner.planner.RowMapper.PlannerFullResultSetExtrator;
import com.planner.planner.RowMapper.PlannerRowMapper;

@Repository
public class PlannerDaoImpl implements PlannerDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlannerDaoImpl.class);

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private final String INSERT_PLANNER_SQL = "INSERT INTO planner(account_id, creator, area_code, title, plan_date_start, plan_date_end, expense, member_count, member_type_id, create_date, update_date)"
			+ "VALUES(:accountId, :creator, :areaCode, :title, :planDateStart, :planDateEnd, :expense ,:memberCount ,:memberTypeId , now(), now());";

//	private final String FIND_SQL = "SELECT P.planner_id, P.account_id, P.creator, P.title, P.plan_date_start, P.plan_date_end, P.expense, P.member_count, P.member_type_id, P.like_count, P.create_date, P.update_date FROM planner AS p WHERE p.planner_id = ?;";
	// 동적 쿼리
	private final String FIND_PLANNER_COMMON_SQL = "SELECT P.planner_id, P.account_id, P.creator, P.area_code, P.title, P.plan_date_start, P.plan_date_end, P.expense, P.member_count, P.member_type_id, SUB.like_count, P.create_date, P.update_date, PL.like_id, "
			+ "plan_loc_one.location_image AS thumbnail "
			+ "FROM planner AS P "
			+ "LEFT JOIN (SELECT planner_id, MIN(plan_id) as plan_id FROM plan GROUP BY planner_id) AS plan_one ON plan_one.planner_id = P.planner_id "
			+ "LEFT JOIN (SELECT plan_id, location_image FROM plan_location) AS plan_loc_one ON plan_loc_one.plan_id = plan_one.plan_id ";

	private final String UPDATE_PLANNER_SQL = "UPDATE planner AS P SET P.area_code = :areaCode, P.title = :title, P.plan_date_start = :planDateStart, P.plan_date_end = :planDateEnd, P.expense = :expense, P.member_count = :memberCount, P.member_type_id = :memberTypeId, P.update_date = NOW() "
			+ "WHERE P.planner_id = :plannerId;";
	private final String DELETE_PLANNER_SQL = "DELETE FROM planner WHERE planner_id = :plannerId;";

	// 동적쿼리
	private final String FIND_PLANNER_BY_PLANNER_ID_JOIN_SQL = "SELECT A.planner_id, A.account_id, A.creator, A.area_code, A.title, A.plan_date_start, A.plan_date_end, A.expense, A.member_count, A.member_type_id, "
			+ "SUB.like_count, A.create_date, A.update_date, "
			+ "C.nickname, M.memo_id, M.memo_title, M.memo_content, M.memo_create_date, M.memo_update_date, D.plan_date, D.plan_index, D.plan_id,  "
			+ "E.location_id, E.location_name, E.location_content_id, E.location_image, E.location_addr, E.location_mapx, E.location_mapy, E.location_transportation, E.location_index, E.plan_id, "
			+ "PL.like_id "
			+ "FROM planner AS A " 
			+ "LEFT JOIN plan_member AS B ON A.planner_id = B.planner_id "
			+ "LEFT JOIN account AS C ON C.account_id = B.account_id "
			+ "LEFT JOIN plan_memo AS M ON A.planner_id = M.planner_id "
			+ "LEFT JOIN plan AS D ON A.planner_id = D.planner_id "
			+ "LEFT JOIN plan_location AS E ON D.plan_id = E.plan_id "
			+ "LEFT JOIN planner_like AS PL ON A.planner_id = PL.planner_id AND PL.account_id = :accountId "
			+ "LEFT JOIN (SELECT planner_id, count(planner_id) as like_count FROM planner_like GROUP BY planner_id) AS SUB ON A.planner_id = SUB.planner_id ";
//			+ "WHERE A.planner_id = ? AND PL.account_id = ? "
//			+ "ORDER BY A.planner_id, D.plan_index, E.location_index;";

	// 동적쿼리
	private final String FIND_TOTAL_COUNT_SQL = "SELECT count(*) AS total_count FROM planner ";
//	private final String FIND_TOTAL_COUNT_ACCOUNT_ID_SQL = "SELECT count(*) AS total_count FROM planner WHERE account_id = :accountId;";
	// 동적쿼리
	private final String FIND_TOTAL_COUNT_LIKE_SQL = "SELECT count(*) AS total_count FROM planner_like AS PL ";
//	private final String FIND_TOTAL_COUNT_LIKE_SQL = "SELECT count(*) AS total_count FROM planner_like WHERE account_id = :accountId;";
//	private final String FIND_TOTAL_COUNT_LIKE_KEYWORD_SQL = "SELECT count(*) as count FROM planner_like AS PL "
//			+ "INNER JOIN planner AS P ON P.planner_id = PL.planner_id "
//			+ "WHERE PL.account_id = :accountId AND P.title LIKE :keyword;";
//	private final String FIND_TOTAL_COUNT_KEYWORD_SQL = "SELECT count(*) AS total_count FROM planner WHERE title LIKE :keyword;";
//	private final String FIND_TOTAL_COUNT_KEYWORD_ACCOUNT_ID_SQL = "SELECT count(*) AS total_count FROM planner "
//			+ "WHERE account_id = :accountId AND title LIKE :keyword;";
	
	
	
	public PlannerDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public int insertPlanner(PlannerDto plannerDto) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(plannerDto);
		
		int result = namedParameterJdbcTemplate.update(INSERT_PLANNER_SQL, parameterSource, keyHolder, new String[] { "planner_id" } );
		
		return keyHolder.getKey().intValue();
	}

	@Override
	public PlannerDto findPlannerByPlannerId(Integer accountId, int plannerId) {
		StringBuilder sb = new StringBuilder(FIND_PLANNER_BY_PLANNER_ID_JOIN_SQL);
		
		sb.append("WHERE A.planner_id = :plannerId ");
		
		sb.append("ORDER BY A.planner_id, D.plan_index, E.location_index;");
		
		SqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("accountId", accountId == null ? Types.NULL : accountId)
				.addValue("plannerId", plannerId);
		
		return namedParameterJdbcTemplate.query(sb.toString(), parameterSource, new PlannerFullResultSetExtrator());
	}

	@Override
	public List<PlannerDto> findPlannersByAccountId(Integer accountId, CommonRequestParamDto commonRequestParamDto, PageInfo pageInfo) {		
		StringBuilder sb = new StringBuilder(FIND_PLANNER_COMMON_SQL);
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		
		sb.append("LEFT JOIN (SELECT planner_id, count(planner_id) as like_count FROM planner_like GROUP BY planner_id) AS SUB ON P.planner_id = SUB.planner_id ");
		sb.append("LEFT JOIN planner_like AS PL ON P.planner_id = PL.planner_id AND PL.account_id = :accountId ");
		sb.append("WHERE P.account_id = :accountId ");
		parameterSource.addValue("accountId", accountId);
		
		if(commonRequestParamDto.getKeyword() != null) {
			sb.append("AND P.title LIKE :keyword ");			
			parameterSource.addValue("keyword", "%" + commonRequestParamDto.getKeyword() + "%");
		}
		
		if(commonRequestParamDto.getAreaCode() != null && !commonRequestParamDto.getAreaCode().equals(0)) {
			sb.append("AND P.area_code = :areaCode ");
			parameterSource.addValue("areaCode", commonRequestParamDto.getAreaCode());
		}
		
		if(commonRequestParamDto.getSortCriteria() == SortCriteria.LATEST) {
			sb.append("ORDER BY P.planner_id DESC ");
		}
		else if(commonRequestParamDto.getSortCriteria() == SortCriteria.LIKECOUNT) {
			sb.append("ORDER BY P.like_count DESC ");
		}
		
		sb.append("LIMIT :pageOffSet, :pageItemCount;");
		
		parameterSource.addValue("pageOffSet", pageInfo.getPageOffSet());
		parameterSource.addValue("pageItemCount", pageInfo.getPageItemCount());
		
		return namedParameterJdbcTemplate.query(sb.toString(), parameterSource, new PlannerRowMapper());
	}

	@Override
	public List<PlannerDto> findPlannerAll(Integer accountId, CommonRequestParamDto commonRequestParamDto, PageInfo pageInfo) throws Exception {
		StringBuilder sb = new StringBuilder(FIND_PLANNER_COMMON_SQL);
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();

		sb.append("LEFT JOIN (SELECT planner_id, count(planner_id) as like_count FROM planner_like GROUP BY planner_id) AS SUB ON P.planner_id = SUB.planner_id ");
		sb.append("LEFT JOIN planner_like AS PL ON P.planner_id = PL.planner_id AND PL.account_id = :accountId ");
		parameterSource.addValue("accountId", accountId, accountId != null ? Types.INTEGER : Types.NULL);

		if(commonRequestParamDto.getKeyword() != null) {
			sb.append("WHERE P.title LIKE :keyword ");			
			parameterSource.addValue("keyword", "%" + commonRequestParamDto.getKeyword() + "%");
		}
		
		if(commonRequestParamDto.getAreaCode() != null && !commonRequestParamDto.getAreaCode().equals(0)) {
			sb.append("AND P.area_code = :areaCode ");
			parameterSource.addValue("areaCode", commonRequestParamDto.getAreaCode());
		}
		
		if(commonRequestParamDto.getSortCriteria() == SortCriteria.LATEST) {
			sb.append("ORDER BY P.planner_id DESC ");
		}
		else if(commonRequestParamDto.getSortCriteria() == SortCriteria.LIKECOUNT) {
			sb.append("ORDER BY P.like_count DESC ");
		}
		
		sb.append("LIMIT :pageOffSet, :pageItemCount;");
		
		parameterSource.addValue("pageOffSet", pageInfo.getPageOffSet());
		parameterSource.addValue("pageItemCount", pageInfo.getPageItemCount());
		
		return namedParameterJdbcTemplate.query(sb.toString(), parameterSource, new PlannerRowMapper());
	}
	
	@Override
	public List<PlannerDto> findLikePlannerList(Integer accountId, CommonRequestParamDto commonRequestParamDto, PageInfo pageInfo) {
		StringBuilder sb = new StringBuilder(FIND_PLANNER_COMMON_SQL);
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		
		sb.append("LEFT JOIN (SELECT planner_id, count(planner_id) as like_count FROM planner_like GROUP BY planner_id) AS SUB ON P.planner_id = SUB.planner_id ");
		sb.append("LEFT JOIN planner_like AS PL ON PL.planner_id = P.planner_id ");
		sb.append("WHERE PL.like_id IN (SELECT like_id FROM planner_like WHERE account_id = :accountId) ");
		parameterSource.addValue("accountId", accountId);
	
		if(commonRequestParamDto.getKeyword() != null) {
			sb.append("AND P.title LIKE :keyword ");
			parameterSource.addValue("keyword", "%" + commonRequestParamDto.getKeyword() + "%");
		}
		
		if(commonRequestParamDto.getAreaCode() != null && !commonRequestParamDto.getAreaCode().equals(0)) {
			sb.append("AND P.area_code = :areaCode ");
			parameterSource.addValue("areaCode", commonRequestParamDto.getAreaCode());
		}
		
		if(commonRequestParamDto.getSortCriteria() == SortCriteria.LATEST) {
			sb.append("ORDER BY P.planner_id DESC ");
		}
		else if(commonRequestParamDto.getSortCriteria() == SortCriteria.LIKECOUNT) {
			sb.append("ORDER BY P.like_count DESC ");
		}
		
		sb.append("LIMIT :pageOffSet, :pageItemCount;");
		
		parameterSource.addValue("pageOffSet", pageInfo.getPageOffSet());
		parameterSource.addValue("pageItemCount", pageInfo.getPageItemCount());
		
		List<PlannerDto> list = namedParameterJdbcTemplate.query(sb.toString(), parameterSource, new PlannerRowMapper());
		return list;
	}

	@Override
	public int updatePlanner(int plannerId, PlannerDto plannerDto) {
		SqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("areaCode", plannerDto.getAreaCode() == 0 ? null : plannerDto.getAreaCode())
				.addValue("title",plannerDto.getTitle())
				.addValue("planDateStart", plannerDto.getPlanDateStart())
				.addValue("planDateEnd", plannerDto.getPlanDateEnd())
				.addValue("expense", plannerDto.getExpense())
				.addValue("memberCount", plannerDto.getMemberCount())
				.addValue("memberTypeId", plannerDto.getMemberTypeId())
				.addValue("plannerId", plannerId);
				
		int result = namedParameterJdbcTemplate.update(UPDATE_PLANNER_SQL, parameterSource);
		return result;
	}

	@Override
	public int deletePlanner(int plannerId) {
		SqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("plannerId", plannerId);
		
		int result = namedParameterJdbcTemplate.update(DELETE_PLANNER_SQL, parameterSource);
		return result;
	}

	@Override
	public int getListTotalCount(Integer accountId, CommonRequestParamDto commonRequestParamDto) throws Exception {
		StringBuilder sb = new StringBuilder(FIND_TOTAL_COUNT_SQL);
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		
		String keyword = commonRequestParamDto.getKeyword();
		Integer areaCode = commonRequestParamDto.getAreaCode();
		
		
		if(keyword != null) {
			sb.append("WHERE title LIKE :keyword ");
			parameterSource.addValue("keyword", "%" + keyword + "%");
		}
		
		if(areaCode != null && !commonRequestParamDto.getAreaCode().equals(0)) {
			sb.append("AND area_code = :areaCode ");
			parameterSource.addValue("areaCode", areaCode);
		}
		
		if(accountId != null) {
			sb.append("AND account_id = :accountId ");
			parameterSource.addValue("accountId", accountId);
		}
		
		return namedParameterJdbcTemplate.queryForObject(sb.toString(), parameterSource, Integer.class);
	}

	@Override
	public int getLikeListTotalCount(Integer accountId, CommonRequestParamDto commonRequestParamDto) throws Exception {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		StringBuilder sb = new StringBuilder(FIND_TOTAL_COUNT_LIKE_SQL);
		
		String keyword = commonRequestParamDto.getKeyword();
		Integer areaCode = commonRequestParamDto.getAreaCode();
		
		if(keyword != null) {
			sb.append("INNER JOIN planner AS P ON P.planner_id = PL.planner_id ");
			sb.append("WHERE title LIKE :keyword ");
			parameterSource.addValue("keyword", "%" + keyword + "%");
		}
		
		if(areaCode != null && !commonRequestParamDto.getAreaCode().equals(0)) {
			sb.append("AND P.area_code = :areaCode ");
			parameterSource.addValue("areaCode", areaCode);
		}
		
		if(accountId != null) {
			sb.append("AND PL.account_id = :accountId ");
			parameterSource.addValue("accountId", accountId);
		}

		return namedParameterJdbcTemplate.queryForObject(sb.toString(), parameterSource, Integer.class);
	}

//	@Override
//	public int getTotalCount(int accountId) {
//		SqlParameterSource parameterSource = new MapSqlParameterSource()
//				.addValue("accountId", accountId);
//		
//		return namedParameterJdbcTemplate.queryForObject(FIND_TOTAL_COUNT_ACCOUNT_ID_SQL, parameterSource, Integer.class);
//	}
//
//	@Override
//	public int getTotalCountByLike(int accountId) {
//		SqlParameterSource parameterSource = new MapSqlParameterSource()
//				.addValue("accountId", accountId);
//		
//		return namedParameterJdbcTemplate.queryForObject(FIND_TOTAL_COUNT_LIKE_SQL, parameterSource, Integer.class);
//	}
//	
//	@Override
//	public int getTotalCountByLike(int accountId, String keyword) {
//		SqlParameterSource parameterSource = new MapSqlParameterSource()
//				.addValue("accountId", accountId)
//				.addValue("keyword", "%" + keyword + "%");
//		
//		return namedParameterJdbcTemplate.queryForObject(FIND_TOTAL_COUNT_LIKE_KEYWORD_SQL, parameterSource, Integer.class);
//	}
//
//	@Override
//	public int getTotalCountByKeyword(String keyword) {
//		SqlParameterSource parameterSource = new MapSqlParameterSource()
//				.addValue("keyword", "%" + keyword + "%");
//
//		return namedParameterJdbcTemplate.queryForObject(FIND_TOTAL_COUNT_KEYWORD_SQL, parameterSource, Integer.class);
//	}
//	
//	@Override
//	public int getTotalCountByKeyword(int accountId, String keyword) {
//		SqlParameterSource parameterSource = new MapSqlParameterSource()
//				.addValue("accountId", accountId)
//				.addValue("keyword", "%" + keyword + "%");
//
//		return namedParameterJdbcTemplate.queryForObject(FIND_TOTAL_COUNT_KEYWORD_ACCOUNT_ID_SQL, parameterSource, Integer.class);
//	}
}
