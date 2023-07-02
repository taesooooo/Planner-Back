package com.planner.planner.Dao.Impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.planner.planner.Common.PageInfo;
import com.planner.planner.Common.SortCriteria;
import com.planner.planner.Dao.SpotDao;
import com.planner.planner.Dto.SpotLikeCountDto;
import com.planner.planner.Dto.SpotLikeDto;
import com.planner.planner.Exception.DuplicateLikeException;
import com.planner.planner.RowMapper.SpotLikeCountMapper;
import com.planner.planner.RowMapper.SpotLikeRowMapper;

@Repository
public class SpotDaoImpl implements SpotDao {

	private static final Logger logger = LoggerFactory.getLogger(SpotDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

//	private final String INSERT_SPOT_LIKE_SQL = "INSERT INTO spot_like (account_id, content_id, like_date) VALUES (?, ?, now());";
	private final String INSERT_SPOT_LIKE_SQL = "INSERT INTO spot_like (account_id, content_id, title, image, like_date) VALUES (?, ?, ?, ?, now());";
	private final String DELETE_SPOT_LIKE_SQL = "DELETE FROM spot_like WHERE account_id = ? and content_id = ?;";
	private final String SELECT_SPOT_LIKE_TOTAL_COUNT_ACCOUNT_ID_SQL = "SELECT count(content_id) FROM spot_like WHERE account_id = ?;";
	private final String SELECT_SPOT_LIKE_TOTAL_COUNT_ACCOUNT_ID_KEYWORD_SQL = "SELECT count(content_id) FROM spot_like WHERE account_id = ? AND title LIKE \"%%%s%%\";";
	// 동적 쿼리
	private final String SELECT_SPOT_LIKE_lIST_SQL = "SELECT SL.like_id, SL.account_id, SL.content_id, SL.title, SL.image, SL.like_date, SUB.like_count "
			+ "FROM spot_like AS SL "
			+ "INNER JOIN ( "
			+ "	SELECT content_id, count(content_id) AS like_count "
			+ "	FROM spot_like "
			+ "	WHERE content_id IN (SELECT content_id FROM spot_like WHERE account_id) "
			+ "	GROUP BY content_id "
			+ ") AS SUB ON SUB.content_id = SL.content_id ";
	private final String SELECT_SPOT_LIKE_COUNT_SQL = "SELECT count(content_id) as like_count FROM spot_like WHERE content_id = ?;";
	private final String SELECT_SPOT_LIKE_COUNT_LIST_SQL = "SELECT content_id, count(content_id) as like_count FROM spot_like WHERE content_id IN (%s) GROUP BY content_id;";
	private final String SELECT_SPOT_LIKE_STATE_SQL = "SELECT like_id, account_id, content_id, title, image, like_date FROM spot_like WHERE content_id IN (%s) and account_id = ?;";

	@Override
	public boolean insertSpotLike(int accountId, SpotLikeDto spotLikeDto) throws SQLException {
		try {
			int result = jdbcTemplate.update(INSERT_SPOT_LIKE_SQL, accountId, spotLikeDto.getContentId(), spotLikeDto.getTitle(), spotLikeDto.getImage());			
			return result > 0 ? true : false;
		}
		catch (DuplicateKeyException e) {
			throw new DuplicateLikeException("중복된 좋아요입니다.");
		}
	}

	@Override
	public boolean deleteSpotLike(int accountId, int contentId) {
		int result = jdbcTemplate.update(DELETE_SPOT_LIKE_SQL, accountId, contentId);
		return result > 0 ? true : false;
	}
	
	@Override
	public int getTotalCountByAccountId(int accountId) throws Exception {
		Integer totalCount = jdbcTemplate.queryForObject(SELECT_SPOT_LIKE_TOTAL_COUNT_ACCOUNT_ID_SQL, Integer.class, accountId);
		
		return totalCount;
	}

	@Override
	public int getTotalCountByAccountId(int accountId, String keyword) throws Exception {
		String sql = String.format(SELECT_SPOT_LIKE_TOTAL_COUNT_ACCOUNT_ID_KEYWORD_SQL, keyword);
		
		Integer totalCount = jdbcTemplate.queryForObject(sql, Integer.class, accountId);
		return totalCount;
	}

	@Override
	public List<SpotLikeDto> selectSpotLikeList(int accountId, SortCriteria sortCriteria, String keyword, PageInfo pageInfo) throws Exception {
		StringBuilder sb = new StringBuilder(SELECT_SPOT_LIKE_lIST_SQL);
		
		if(keyword != null) {
			sb.append("WHERE account_id = ? AND title LIKE \"%").append(keyword).append("%\" ");
		}
		else {
			sb.append("WHERE account_id = ? ");
		}
		
		if(sortCriteria == SortCriteria.LATEST) {
			sb.append("ORDER BY like_id DESC ");
		}
		else if(sortCriteria == SortCriteria.LIKECOUNT) {
			sb.append("ORDER BY like_count DESC ");
		}
		
		sb.append("LIMIT ?, ?;");
		
		List<SpotLikeDto> list = jdbcTemplate.query(sb.toString(), new SpotLikeRowMapper(), accountId, pageInfo.getPageOffSet(), pageInfo.getPageItemCount());
		
		return list;
	}

	@Override
	public int selectSpotLikeCountByContentId(int contentId) {
		Integer likeCount = jdbcTemplate.queryForObject(SELECT_SPOT_LIKE_COUNT_SQL, Integer.class, contentId);
		
		return likeCount.intValue();
	}

	@Override
	public List<SpotLikeCountDto> selectSpotLikeCountByContentIdList(List<Integer> contentIdList) throws Exception {
		String contentIds = contentIdList.stream().map(String::valueOf).collect(Collectors.joining(","));
		String sql = String.format(SELECT_SPOT_LIKE_COUNT_LIST_SQL, contentIds);
		
		List<SpotLikeCountDto> list =jdbcTemplate.query(sql, new SpotLikeCountMapper());
		
		return list;
	}

	@Override
	public boolean selectSpotLikeByContentId(int accountId, int contentId) {
		if(accountId <= 0) {
			return false;
		}
		
		SpotLikeDto like = null;
		
		try {
			String sql = String.format(SELECT_SPOT_LIKE_STATE_SQL, contentId);
			like = jdbcTemplate.queryForObject(sql, new SpotLikeRowMapper(), accountId);			
			return like != null ? true : false;
		}
		catch (EmptyResultDataAccessException e) {
			return false;
		}
	}

	@Override
	public List<SpotLikeDto> selectSpotLikeByContentIdList(Integer accountId, List<Integer> contentIdList) {
		if(accountId == null) {
			return new ArrayList<SpotLikeDto>();
		}
		
		String contentIds = contentIdList.stream().map(String::valueOf).collect(Collectors.joining(","));
		String sql = String.format(SELECT_SPOT_LIKE_STATE_SQL, contentIds);
		List<SpotLikeDto> states = jdbcTemplate.query(sql, new SpotLikeRowMapper(), accountId);

		return states;
	}
	
	
}
