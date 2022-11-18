package com.planner.planner.Dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.planner.planner.Entity.SpotLikeCount;

@Repository
public class SpotDaoImpl implements SpotDao {

	private static final Logger logger = LoggerFactory.getLogger(SpotDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private final String likeAddSQL = "INSERT INTO spotlike (account_id, content_id, like_date) VALUES (?, ?, now());";
	private final String likeDeleteSQL = "DELETE FROM spotlike WHERE account_id = ? and content_id = ?;";
	private final String likeCountSQL = "SELECT content_id, count(content_id) as like_count FROM spotlike WHERE content_id IN (%s) GROUP BY content_id";

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
	public List<SpotLikeCount> spotLikeCount(String contentIds) {
		String sql = String.format(likeCountSQL, contentIds);
		List<SpotLikeCount> list = jdbcTemplate.query(sql, (rs, rowNum) -> {
			return new SpotLikeCount(rs.getInt(1),rs.getInt(2));
		});
		
		return list;
	}
}
