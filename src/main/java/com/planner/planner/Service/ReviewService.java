package com.planner.planner.Service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.planner.planner.Dto.ReviewDto;

public interface ReviewService {
	public int insertReview(int accountId, ReviewDto review) throws Exception;
	public List<ReviewDto> findAllReview();
	public ReviewDto findReview(int reviewId);
	public void updateReview(ReviewDto review);
	public void deleteReview(int reviewId);
}
