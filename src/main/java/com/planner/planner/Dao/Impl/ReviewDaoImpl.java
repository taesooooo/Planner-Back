package com.planner.planner.Dao.Impl;

import java.sql.PreparedStatement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.planner.planner.Dao.ReviewDao;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.ReviewDto;
import com.planner.planner.RowMapper.ReviewRowMapper;

@Repository
public class ReviewDaoImpl implements ReviewDao {
	private static final Logger logger = LoggerFactory.getLogger(ReviewDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	private KeyHolder keyHolder;
	
	private final String insertReviewSQL = "INSERT INTO review(planner_id, title, content, writer, writer_id, create_date, update_date) VALUES(?, ?, ?, ?, ?, now(), now());";
	private final String findAllReviewSQL = "SELECT review_id, planner_id, title, content, writer, writer_id, like_count, create_date, update_date FROM review;";
	private final String findReviewSQL = "SELECT review_id, planner_id, title, content, writer, writer_id, like_count, create_date, update_date FROM review WHERE review_id = ?;";
	private final String updateReviewSQL = "UPDATE review SET title = ?, content = ?, update_date = now() WHERE review_id = ?;";
	private final String deleteReviewSQL = "DELETE FROM review WHERE review_id = ?;";
	
	public ReviewDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.keyHolder = new GeneratedKeyHolder();
	}

	@Override
	public int insertReview(ReviewDto reviewDto, AccountDto accountDto) {
		int result = jdbcTemplate.update(conn -> {
			PreparedStatement ps = conn.prepareStatement(insertReviewSQL, new String[] {"review_id"});
			ps.setInt(1, reviewDto.getPlannerId());
			ps.setString(2, reviewDto.getTitle());
			ps.setString(3, reviewDto.getContent());
			ps.setString(4, reviewDto.getWriter());
			ps.setInt(5, accountDto.getAccountId());
			return ps;
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public List<ReviewDto> findAllReview() {
		List<ReviewDto> list = jdbcTemplate.query(findAllReviewSQL, new ReviewRowMapper());			
		return list.isEmpty() ? null : list;
	}

	@Override
	public ReviewDto findReview(int reviewId) {
		List<ReviewDto> list = jdbcTemplate.query(findReviewSQL, new ReviewRowMapper(), reviewId);
		ReviewDto review = DataAccessUtils.singleResult(list);
		return review;
	}

	@Override
	public void updateReview(ReviewDto reviewDto) {
		int result = jdbcTemplate.update(updateReviewSQL,reviewDto.getTitle(), reviewDto.getContent(), reviewDto.getReviewId());
	}

	@Override
	public void deleteReview(int reviewId) {
		int result = jdbcTemplate.update(deleteReviewSQL, reviewId);
	}

}
