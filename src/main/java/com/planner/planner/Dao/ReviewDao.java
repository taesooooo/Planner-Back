package com.planner.planner.Dao;

import java.util.List;

import com.planner.planner.Dto.ReviewDto;
import com.planner.planner.Entity.Review;

public interface ReviewDao {
	public boolean insertReview(Review review);
	public List<Review> findAllReview(int index);
	public Review findReview(int reviewId);
	public boolean updateReview(Review review);
	public boolean deleteReview(int reviewId);
}
