package com.planner.planner.Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.planner.planner.Dto.SpotLikeDto;
import com.planner.planner.Entity.Spot;
import com.planner.planner.Entity.SpotLike;
import com.planner.planner.RowMapper.LikeSpotsRowMapper;

@Repository
public class SpotDaoImpl implements SpotDao {
	
	private static final Logger logger = LoggerFactory.getLogger(SpotDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private final String spotLikesByAccountId = "SELECT like_id, account_id, content_id, like_date FROM spotlike WHERE account_id = ?;";
	private final String likeAddSQL = "INSERT INTO spotlike (account_id, content_id, like_date) VALUES (?, ?, now());";
	private final String likeDeleteSQL = "DELETE FROM spotlike WHERE account_id = ? and content_id = ?;";

	@Override
	public List<SpotLikeDto> spotLikesByAccountId(int accountId) {
		List<SpotLike> likes =  jdbcTemplate.query(spotLikesByAccountId, new RowMapper<SpotLike>() {
			@Override
			public SpotLike mapRow(ResultSet rs, int rowNum) throws SQLException {
				SpotLike spotLike = new SpotLike.Builder()
						.setLikeId(rs.getInt(1))
						.setAccountId(rs.getInt(2))
						.setContentId(rs.getInt(3))
						.setLikeDate(rs.getDate(4).toLocalDate())
						.build();
				return spotLike;
			}
		}, accountId);
		return likes.stream().map(like -> like.toDto()).collect(Collectors.toList());
	}

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
