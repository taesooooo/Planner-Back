package com.planner.planner.Dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SpotDaoImpl implements SpotDao {

	private static final Logger logger = LoggerFactory.getLogger(SpotDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;


	private final String likeAddSQL = "INSERT INTO spotlike (account_id, content_id, like_date) VALUES (?, ?, now());";
	private final String likeDeleteSQL = "DELETE FROM spotlike WHERE account_id = ? and content_id = ?;";

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

}
