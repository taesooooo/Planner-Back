package com.planner.planner.Dao;

import java.util.List;

import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.ReviewDto;

public interface ReviewDao {
	public boolean insertReview(ReviewDto reviewDto, AccountDto accountDto);
	public List<ReviewDto> findAllReview(int index);
	public ReviewDto findReview(int reviewId);
	public boolean updateReview(ReviewDto reviewDto);
	public boolean deleteReview(int reviewId);
}
