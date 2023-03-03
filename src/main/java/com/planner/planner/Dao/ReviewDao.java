package com.planner.planner.Dao;

import java.util.List;

import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.ReviewDto;

public interface ReviewDao {
	public int insertReview(ReviewDto reviewDto, AccountDto accountDto);
	public List<ReviewDto> findAllReview();
	public ReviewDto findReview(int reviewId);
	public void updateReview(ReviewDto reviewDto);
	public void deleteReview(int reviewId);

}
