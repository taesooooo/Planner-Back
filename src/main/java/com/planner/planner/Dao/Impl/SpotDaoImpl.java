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

	private final String INSERT_SPOT_LIKE_SQL = "INSERT INTO spot_like (account_id, content_id, like_date) VALUES (?, ?, now());";
	private final String DELETE_SPOT_LIKE_SQL = "DELETE FROM spot_like WHERE account_id = ? and content_id = ?;";
	private final String SELECT_SPOT_LIKE_COUNT_SQL = "SELECT count(content_id) as like_count FROM spot_like WHERE content_id = ?;";
	private final String SELECT_SPOT_LIKE_COUNT_LIST_SQL = "SELECT content_id, count(content_id) as like_count FROM spot_like WHERE content_id IN (%s) GROUP BY content_id;";
	private final String SELECT_SPOT_LIKE_STATE_SQL = "SELECT like_id, account_id, content_id, like_date FROM spot_like WHERE content_id IN (%s) and account_id = ?;";

	@Override
	public boolean insertSpotLike(int accountId, int contentId) throws SQLException {
		try {
			int result = jdbcTemplate.update(INSERT_SPOT_LIKE_SQL, accountId, contentId);			
			return result > 0 ? true : false;
		}
		catch (DuplicateKeyException e) {
			throw new DuplicateLikeException("좋아요를 했습니다.");
		}
	}

	@Override
	public boolean deleteSpotLike(int accountId, int contentId) {
		int result = jdbcTemplate.update(DELETE_SPOT_LIKE_SQL, accountId, contentId);
		return result > 0 ? true : false;
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
			like = jdbcTemplate.queryForObject(SELECT_SPOT_LIKE_STATE_SQL, new SpotLikeRowMapper(), contentId, accountId);			
			return like != null ? true : false;
		}
		catch (EmptyResultDataAccessException e) {
			return false;
		}
	}

	@Override
	public List<SpotLikeDto> selectSpotLikeByContentIdList(int accountId, List<Integer> contentIdList) {
		if(accountId <= 0) {
			return new ArrayList<SpotLikeDto>();
		}
		String contentIds = contentIdList.stream().map(String::valueOf).collect(Collectors.joining(","));
		String sql = String.format(SELECT_SPOT_LIKE_STATE_SQL, contentIds);
		List<SpotLikeDto> states = jdbcTemplate.query(sql, new SpotLikeRowMapper(), accountId);

		return states;
	}
	
	
}
