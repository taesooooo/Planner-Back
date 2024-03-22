package com.planner.planner.Service;

import com.planner.planner.Common.Page;
import com.planner.planner.Dto.CommonRequestParamDto;
import com.planner.planner.Dto.ReviewDto;

public interface ReviewService {
	public int insertReview(int accountId, ReviewDto review) throws Exception;

	public Page<ReviewDto> findAllReview(CommonRequestParamDto commonRequestParamDto) throws Exception;

	public ReviewDto findReview(int reviewId);

	public void updateReview(int accountId, int reviewId, ReviewDto review) throws Exception;

	public void deleteReview(int reviewId);
}
