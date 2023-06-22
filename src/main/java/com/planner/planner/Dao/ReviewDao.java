package com.planner.planner.Dao;

import java.util.List;

import com.planner.planner.Common.PageInfo;
import com.planner.planner.Common.SortCriteria;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.ReviewDto;

public interface ReviewDao {
	public int insertReview(ReviewDto reviewDto, AccountDto accountDto);
	public List<ReviewDto> findAllReview(SortCriteria sortCriteria, String keyword, PageInfo pageInfo);
	public ReviewDto findReview(int reviewId);
	public void updateReview(ReviewDto reviewDto);
	public void deleteReview(int reviewId);
	public int getTotalCount();
	public int getTotalCountByKeyword(String keyword);
}
