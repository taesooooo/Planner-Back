package com.planner.planner.Dao;

import java.util.List;

import com.planner.planner.Common.PageInfo;
import com.planner.planner.Common.Security.UserIdentifierDao;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.CommonRequestParamDto;
import com.planner.planner.Dto.ReviewDto;

public interface ReviewDao extends UserIdentifierDao {
	public int createReview(ReviewDto reviewDto, AccountDto accountDto, String thumbnail);
	public List<ReviewDto> findAll(CommonRequestParamDto commonRequestParamDto, PageInfo pageInfo);
	public ReviewDto findById(int reviewId);
	public void updateReview(int reviewId, ReviewDto reviewDto);
	public void updateReviewThumbnail(int reviewId, String thumbnailName);
	public void deleteReview(int reviewId);
	public int getTotalCount(CommonRequestParamDto commonRequestParamDto);
}
