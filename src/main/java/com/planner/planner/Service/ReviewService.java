package com.planner.planner.Service;

import java.util.List;

import com.planner.planner.Dto.ReviewDto;

public interface ReviewService {
	public boolean insertReview(int accountId, ReviewDto review);
	public List<ReviewDto> findAllReview(int index);
	public ReviewDto findReview(int reviewId);
	public boolean updateReview(ReviewDto review);
	public boolean deleteReview(int reviewId);
}
