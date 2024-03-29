package com.planner.planner.Dao;

import java.util.List;

import com.planner.planner.Common.PageInfo;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.CommonRequestParamDto;
import com.planner.planner.Dto.ReviewDto;

public interface ReviewDao {
	public int insertReview(ReviewDto reviewDto, AccountDto accountDto, String thumbnailName);
	public List<ReviewDto> findAllReview(CommonRequestParamDto commonRequestParamDto, PageInfo pageInfo);
	public ReviewDto findReview(int reviewId);
	public void updateReview(int reviewId, ReviewDto reviewDto);
	public void updateReviewThumbnail(int reviewId, String thumbnailName);
	public void deleteReview(int reviewId);
	public int getTotalCount(CommonRequestParamDto commonRequestParamDto);
}
