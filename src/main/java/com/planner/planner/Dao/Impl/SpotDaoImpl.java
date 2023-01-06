package com.planner.planner.Dao.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.planner.planner.Dao.SpotDao;
import com.planner.planner.Dto.SpotLikeCountDto;

@Repository
public class SpotDaoImpl implements SpotDao {

	private static final Logger logger = LoggerFactory.getLogger(SpotDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private final String likeAddSQL = "INSERT INTO spotlike (account_id, content_id, like_date) VALUES (?, ?, now());";
	private final String likeDeleteSQL = "DELETE FROM spotlike WHERE account_id = ? and content_id = ?;";
	private final String likeCountSQL = "SELECT content_id, count(content_id) as like_count FROM spotlike WHERE content_id = ?";

	@Override
	public boolean spotLikeAdd(int accountId, int contentId) {
		int result = jdbcTemplate.update(likeAddSQL, accountId, contentId);
		return result > 0 ? true : false;
	}

	@Override
	public boolean spotLikeDelete(int accountId, int contentId) {
		int result = jdbcTemplate.update(likeDeleteSQL, accountId, contentId);
		return result > 0 ? true : false;
	}
	
	@Override
	public SpotLikeCountDto spotLikeCount(int contentId) {
		SpotLikeCountDto list = jdbcTemplate.queryForObject(likeCountSQL, (rs, rowNum) -> {
			return new SpotLikeCountDto(rs.getInt(1),rs.getInt(2));
		}, contentId);
		
		return list;
	}
}
