package com.planner.planner.Dao.Impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.planner.planner.Common.PageInfo;
import com.planner.planner.Common.SortCriteria;
import com.planner.planner.Dao.SpotDao;
import com.planner.planner.Dto.CommonRequestParamDto;
import com.planner.planner.Dto.SpotLikeCountDto;
import com.planner.planner.Dto.SpotLikeDto;
import com.planner.planner.Exception.DuplicateLikeException;
import com.planner.planner.RowMapper.SpotLikeCountMapper;
import com.planner.planner.RowMapper.SpotLikeRowMapper;

@Repository
public class SpotDaoImpl implements SpotDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(SpotDaoImpl.class);

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private final String INSERT_SPOT_LIKE_SQL = "INSERT INTO spot_like (account_id, content_id, area_code, title, image, like_date) VALUES (:accountId, :contentId, :areaCode, :title, :image, now());";
	private final String DELETE_SPOT_LIKE_SQL = "DELETE FROM spot_like WHERE account_id = :accountId and content_id = :contentId;";
	// 동적 쿼리
	private final String SELECT_SPOT_LIKE_LIST_SQL = "SELECT SL.like_id, SL.account_id, SL.content_id, SL.area_code, SL.title, SL.image, SL.like_date, SUB.like_count "
			+ "FROM spot_like AS SL "
			+ "INNER JOIN ( "
			+ "	SELECT content_id, count(content_id) AS like_count "
			+ "	FROM spot_like "
			+ "	WHERE content_id IN (SELECT content_id FROM spot_like WHERE account_id) "
			+ "	GROUP BY content_id "
			+ ") AS SUB ON SUB.content_id = SL.content_id ";
	// 동적 쿼리
	private final String SELECT_SPOT_LIKE_TOTAL_COUNT_SQL = "SELECT count(content_id) FROM spot_like ";
	private final String SELECT_SPOT_LIKE_COUNT_SQL = "SELECT count(content_id) as like_count FROM spot_like WHERE content_id = :contentId;";
	private final String SELECT_SPOT_LIKE_COUNT_LIST_SQL = "SELECT content_id, count(content_id) as like_count FROM spot_like WHERE content_id IN (:contentIdList) GROUP BY content_id;";
	private final String SELECT_SPOT_LIKE_STATE_SQL = "SELECT like_id, account_id, content_id, title, image, like_date FROM spot_like WHERE content_id IN (:contentIdList) and account_id = :accountId;";

	public SpotDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	
	@Override
	public boolean insertSpotLike(int accountId, SpotLikeDto spotLikeDto) throws SQLException {
		try {
			MapSqlParameterSource parameterSource = new MapSqlParameterSource()
					.addValue("accountId", accountId)
					.addValue("contentId", spotLikeDto.getContentId())
					.addValue("areaCode", spotLikeDto.getAreaCode())
					.addValue("title", spotLikeDto.getTitle())
					.addValue("image", spotLikeDto.getImage());
			
			int result = namedParameterJdbcTemplate.update(INSERT_SPOT_LIKE_SQL, parameterSource);			
			return result > 0 ? true : false;
		}
		catch (DuplicateKeyException e) {
			throw new DuplicateLikeException("중복된 좋아요입니다.");
		}
	}

	@Override
	public boolean deleteSpotLike(int accountId, int contentId) {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("accountId", accountId)
				.addValue("contentId", contentId);
		
		int result = namedParameterJdbcTemplate.update(DELETE_SPOT_LIKE_SQL, parameterSource);
		return result > 0 ? true : false;
	}
	
	

	@Override
	public List<SpotLikeDto> selectSpotLikeList(int accountId, CommonRequestParamDto requestParamDto, PageInfo pageInfo) throws Exception {
		StringBuilder sb = new StringBuilder(SELECT_SPOT_LIKE_LIST_SQL);
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		
		sb.append("WHERE account_id = :accountId ");
		parameterSource.addValue("accountId", accountId);
		
		String keyword = requestParamDto.getKeyword();
		Integer areaCode = requestParamDto.getAreaCode();
		SortCriteria sortCriteria = requestParamDto.getSortCriteria();
		
		if(keyword != null) {
			sb.append("AND title LIKE :title ");
			parameterSource.addValue("title", "%" + keyword + "%");
		}
		
		if(areaCode != null && !areaCode.equals(0)) {
			sb.append("AND area_code = :areaCode ");
			parameterSource.addValue("areaCode", areaCode);
		}
		
		if(sortCriteria == SortCriteria.LATEST) {
			sb.append("ORDER BY like_id DESC ");
		}
		else if(sortCriteria == SortCriteria.LIKECOUNT) {
			sb.append("ORDER BY like_count DESC ");
		}
		
		sb.append("LIMIT :offset, :itemCount;");
		parameterSource.addValue("offset", pageInfo.getPageOffSet());
		parameterSource.addValue("itemCount", pageInfo.getPageItemCount());
		
		List<SpotLikeDto> list = namedParameterJdbcTemplate.query(sb.toString(), parameterSource, new SpotLikeRowMapper());
		
		return list;
	}
	@Override
	public int findListTotalCount(int accountId, CommonRequestParamDto requestParamDto) throws Exception {
		// TODO Auto-generated method stub
		//	private final String SELECT_SPOT_LIKE_COUNT_SQL = "SELECT count(content_id) as like_count FROM spot_like WHERE content_id = ?;";
		//	private final String SELECT_SPOT_LIKE_COUNT_LIST_SQL = "SELECT content_id, count(content_id) as like_count FROM spot_like WHERE content_id IN (%s) GROUP BY content_id;";
		//	private final String SELECT_SPOT_LIKE_TOTAL_COUNT_ACCOUNT_ID_SQL = "SELECT count(content_id) FROM spot_like WHERE account_id = ?;";
		//	private final String SELECT_SPOT_LIKE_TOTAL_COUNT_ACCOUNT_ID_KEYWORD_SQL = "SELECT count(content_id) FROM spot_like WHERE account_id = ? AND title LIKE \"%%%s%%\";";
		//	private final String SELECT_SPOT_LIKE_STATE_SQL = "SELECT like_id, account_id, content_id, title, image, like_date FROM spot_like WHERE content_id IN (%s) and account_id = ?;";
		StringBuilder sb = new StringBuilder(SELECT_SPOT_LIKE_TOTAL_COUNT_SQL);
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		
		String keyword = requestParamDto.getKeyword();
		Integer areaCode = requestParamDto.getAreaCode();

		if(keyword != null) {
			sb.append("WHERE title LIKE :keyword ");
			parameterSource.addValue("keyword", "%" + keyword + "%");
		}
		
		if(areaCode != null && !areaCode.equals(0)) {
			sb.append("AND area_code = :areaCode ");
			parameterSource.addValue("areaCode", areaCode);
		}
		
		sb.append("AND account_id = :accountId ");
		parameterSource.addValue("accountId", accountId);
		
		
		return namedParameterJdbcTemplate.queryForObject(sb.toString(), parameterSource, Integer.class);
	}

	@Override
	public int findSpotLikeCountByContentId(int contentId) {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("contentId", contentId);
		
		Integer likeCount = namedParameterJdbcTemplate.queryForObject(SELECT_SPOT_LIKE_COUNT_SQL, parameterSource, Integer.class);
		
		return likeCount;
	}

	@Override
	public List<SpotLikeCountDto> findSpotLikeCountByContentIdList(List<Integer> contentIdList) throws Exception {
		if(contentIdList == null || contentIdList.isEmpty()) {
			return new ArrayList<SpotLikeCountDto>();
		}
		
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		
		String contentIds = contentIdList.stream().map(String::valueOf).collect(Collectors.joining(","));
		
		parameterSource.addValue("contentIdList", contentIds);
		
		List<SpotLikeCountDto> list = namedParameterJdbcTemplate.query(SELECT_SPOT_LIKE_COUNT_LIST_SQL, parameterSource, new SpotLikeCountMapper());
		
		return list;
	}

	@Override
	public boolean findSpotLikeStateByContentId(Integer accountId, int contentId) {
		if(accountId == null) {
			return false;
		}
		
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();

		try {
			parameterSource.addValue("contentIdList", contentId);
			parameterSource.addValue("accountId", accountId);
			SpotLikeDto like = namedParameterJdbcTemplate.queryForObject(SELECT_SPOT_LIKE_STATE_SQL, parameterSource, new SpotLikeRowMapper());			
			
			return like != null ? true : false;
		}
		catch (EmptyResultDataAccessException e) {
			return false;
		}
	}

	@Override
	public List<SpotLikeDto> findSpotLikeStateByContentIdList(Integer accountId, List<Integer> contentIdList) {
		if(accountId == null || contentIdList == null || contentIdList.isEmpty()) {
			return new ArrayList<SpotLikeDto>();
		}

		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		
		String contentIds = contentIdList.stream().map(String::valueOf).collect(Collectors.joining(","));
		parameterSource.addValue("contentIdList", contentIds);
		parameterSource.addValue("accountId", accountId);
		List<SpotLikeDto> states = namedParameterJdbcTemplate.query(SELECT_SPOT_LIKE_STATE_SQL, parameterSource, new SpotLikeRowMapper());

		return states;
	}
	
	
}
