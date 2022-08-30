package com.planner.planner.Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.planner.planner.Entity.Spot;
import com.planner.planner.RowMapper.LikeSpotsRowMapper;

@Repository
public class SpotDaoImpl implements SpotDao {
	
	private static final Logger logger = LoggerFactory.getLogger(SpotDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private final String AllSpotSQL = "SELECT spot_id, spot_name, spot_image, country_name, city_name, detail, like_count FROM planner.spot;";
	private final String spotLikesByAccountId = "SELECT spot_id, spot_name, spot_image, country_name, city_name FROM spot WHERE spot_id IN (SELECT spot_id FROM spotlike WHERE account_id = 1);";
	private final String likeSQL = "UPDATE spot SET like_count = like_count + 1 WHERE spot_id = ?;";
	private final String likeCancelSQL = "UPDATE spot SET like_count = IF(like_count > 0, like_count - 1, 0) WHERE spot_id = ?;";
	private final String likeAddSQL = "INSERT INTO spotlike (account_id, spot_id, like_date) VALUES (?, ?, now());";
	private final String likeDeleteSQL = "DELETE FROM `spotlike` WHERE account_id = ? and spot_id = ?;";

	@Override
	public List<Spot> getAllSpot() {
		return jdbcTemplate.query(AllSpotSQL, new RowMapper<Spot>() {
			@Override
			public Spot mapRow(ResultSet rs, int rowNum) throws SQLException {
				Spot spot = new Spot.Builder()
						.setSpotId(rs.getInt(1))
						.setSpotName(rs.getString(2))
						.setSpotImage(rs.getString(3))
						.setContryName(rs.getString(4))
						.setCityName(rs.getString(5))
						.setDetail(rs.getString(6))
						.setLikeCount(rs.getInt(7))
						.build();
				return spot;
			}
		});
	}

	@Override
	public List<Spot> getSpotLikesByAccountId(int accountId) {
		List<Spot> spots =  jdbcTemplate.query(spotLikesByAccountId,new LikeSpotsRowMapper(), accountId);
		return spots;
	}

	@Override
	public boolean spotLike(int spotId) {
		int result = jdbcTemplate.update(likeSQL, spotId);
		return result > 0 ? true : false;
	}

	@Override
	public boolean spotLikeAdd(int spotId, int accountId) {
		int result = jdbcTemplate.update(likeAddSQL, accountId, spotId);
		return result > 0 ? true : false;
	}

	@Override
	public boolean spotLikeCancel(int spotId) {
		int result = jdbcTemplate.update(likeCancelSQL, spotId);
		return result > 0 ? true : false;
	}

	@Override
	public boolean spotLikeDelete(int accountId, int spotId) {
		int result = jdbcTemplate.update(likeDeleteSQL, accountId, spotId);
		return result > 0 ? true : false;
	}

}
