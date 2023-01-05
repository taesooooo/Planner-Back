package com.planner.planner.Dao.Impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.planner.planner.Dao.ReviewDao;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.ReviewDto;
import com.planner.planner.Entity.Account;
import com.planner.planner.Entity.Review;

@Repository
public class ReviewDaoImpl implements ReviewDao {
	private static final Logger logger = LoggerFactory.getLogger(ReviewDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	
	private final String insertReviewSQL = "INSERT INTO review(planner_id, title, content, writer, writer_id, create_date, update_date) VALUES(?, ?, ?, ?, ?, now(), now());";
	private final String findAllReviewSQL = "SELECT review_id, planner_id, title, content, writer, writer_id, like_count, create_date, update_date FROM review;";
	private final String findReviewSQL = "SELECT review_id, planner_id, title, content, writer, writer_id, like_count, create_date, update_date FROM review WHERE review_id = ?;";
	private final String updateReviewSQL = "UPDATE review SET title = ?, content = ?, update_date = now() WHERE review_id = ?;";
	private final String deleteReviewSQL = "DELETE FROM review WHERE review_id = ?;";
	
	public ReviewDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public boolean insertReview(ReviewDto reviewDto, AccountDto accountDto) {
		int result = jdbcTemplate.update(insertReviewSQL, reviewDto.getPlannerId(), reviewDto.getTitle(),reviewDto.getContent(), accountDto.getNickName(), accountDto.getAccountId());
		return result > 0 ? true : false;
	}

	@Override
	public List<ReviewDto> findAllReview(int index) {
		List<ReviewDto> list = jdbcTemplate.query(findAllReviewSQL, (rs, rowNum) -> {
			return new ReviewDto.Builder()
					.setReviewId(rs.getInt(1))
					.setPlannerId(rs.getInt(2))
					.setTitle(rs.getString(3))
					.setContent(rs.getString(4))
					.setWriter(rs.getString(5))
					.setWriterId(rs.getInt(6))
					.setLikeCount(rs.getInt(7))
					.setCreateTime(rs.getTimestamp(8).toLocalDateTime())
					.setUpdateTime(rs.getTimestamp(9).toLocalDateTime())
					.build();
		});
		return list;
	}

	@Override
	public ReviewDto findReview(int reviewId) {
		ReviewDto review = jdbcTemplate.queryForObject(findReviewSQL, (rs, rowNum) -> {
			return new ReviewDto.Builder()
					.setReviewId(rs.getInt(1))
					.setPlannerId(rs.getInt(2))
					.setTitle(rs.getString(3))
					.setContent(rs.getString(4))
					.setWriter(rs.getString(5))
					.setWriterId(rs.getInt(6))
					.setLikeCount(rs.getInt(7))
					.setCreateTime(rs.getTimestamp(8).toLocalDateTime())
					.setUpdateTime(rs.getTimestamp(9).toLocalDateTime())
					.build();
		}, reviewId);
		return review;
	}

	@Override
	public boolean updateReview(ReviewDto reviewDto) {
		int result = jdbcTemplate.update(updateReviewSQL,reviewDto.getTitle(), reviewDto.getContent(), reviewDto.getReviewId());
		return result > 0 ? true : false;
	}

	@Override
	public boolean deleteReview(int reviewId) {
		int result = jdbcTemplate.update(deleteReviewSQL, reviewId);
		return result > 0 ? true : false;
	}

}
