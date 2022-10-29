package com.planner.planner.Dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.planner.planner.Entity.Review;

@Repository
public class ReviewDaoImpl implements ReviewDao {
	private static final Logger logger = LoggerFactory.getLogger(ReviewDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	
	private final String insertReviewSQL = "INSERT INTO planner.review(planner_id, title, writer, content, create_date, update_date) VALUES(?, ?, ?, ?, now(), now());";
	private final String findAllReviewSQL = "SELECT review_id, planner_id, title, writer, content, like_count, create_date, update_date FROM planner.review;";
	private final String findReviewSQL = "SELECT review_id, planner_id, title, writer, content, like_count, create_date, update_date FROM planner.review WHERE review_id = ?;";
	private final String updateReviewSQL = "UPDATE planner.review SET title = ?, content = ?, update_date = now() WHERE review_id = ?;";
	private final String deleteReviewSQL = "DELETE FROM planner.review WHERE reveiw_id = ?;";
	
	public ReviewDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public boolean insertReview(Review review) {
		int result = jdbcTemplate.update(insertReviewSQL, review.getPlannerId(), review.getTitle(),review.getWriter(),review.getContent());
		return result > 0 ? true : false;
	}

	@Override
	public List<Review> findAllReview(int index) {
		List<Review> list = jdbcTemplate.queryForList(findAllReviewSQL,Review.class);
		return list;
		
	}

	@Override
	public Review findReview(int reviewId) {
		Review review = jdbcTemplate.queryForObject(findReviewSQL, Review.class, reviewId);
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
