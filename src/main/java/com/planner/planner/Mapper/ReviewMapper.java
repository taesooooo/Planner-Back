package com.planner.planner.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.planner.planner.Common.PageInfo;
import com.planner.planner.Common.Security.UserIdentifierDao;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.CommonRequestParamDto;
import com.planner.planner.Dto.ReviewDto;

@Mapper
public interface ReviewMapper extends UserIdentifierDao {
	public int createReview(ReviewDto reviewDto, AccountDto accountDto, String thumbnail);
	public ReviewDto findById(int reviewId);
	public List<ReviewDto> findAll(CommonRequestParamDto commonRequestParamDto, PageInfo pageInfo);
	public int updateReview(int reviewId, ReviewDto reviewDto);
	public int updateReviewThumbnail(int reviewId, String thumbnailName);
	public int deleteReview(int reviewId);
	public int findTotalCount(@Param("commonRequestParamDto")CommonRequestParamDto commonRequestParamDto);
}
