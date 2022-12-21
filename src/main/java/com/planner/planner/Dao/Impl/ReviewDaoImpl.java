package com.planner.planner.Dao.Impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.planner.planner.Dao.ReviewDao;
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
	public boolean insertReview(Review review, Account account) {
		int result = jdbcTemplate.update(insertReviewSQL, review.getPlannerId(), review.getTitle(),review.getContent(), account.getNickName(), account.getAccountId());
		return result > 0 ? true : false;
	}

	@Override
	public List<Review> findAllReview(int index) {
		List<Review> list = jdbcTemplate.query(findAllReviewSQL, (rs, rowNum) -> {
			return new Review.Builder()
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
	public Review findReview(int reviewId) {
		Review review = jdbcTemplate.queryForObject(findReviewSQL, (rs, rowNum) -> {
			return new Review.Builder()
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
	public boolean updateReview(Review review) {
		int result = jdbcTemplate.update(updateReviewSQL,review.getTitle(), review.getContent(),review.getReviewId());
		return result > 0 ? true : false;
	}

	@Override
	public boolean deleteReview(int reviewId) {
		int result = jdbcTemplate.update(deleteReviewSQL, reviewId);
		return result > 0 ? true : false;
	}

}
