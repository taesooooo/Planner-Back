package com.planner.planner.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.planner.planner.Dao.ReviewDao;
import com.planner.planner.Dto.ReviewDto;
import com.planner.planner.Entity.Review;

@Service
public class ReviewServiceImpl implements ReviewService {
	private static final Logger logger = LoggerFactory.getLogger(ReviewServiceImpl.class);
	
	private ReviewDao reviewDao;
	
	public ReviewServiceImpl(ReviewDao reviewDao) {
		this.reviewDao = reviewDao;
	}

	@Override
	public boolean insertReview(ReviewDto reviewDto) {
		return reviewDao.insertReview(reviewDto.toEntity());
	}

	@Override
	public List<ReviewDto> findAllReview(int index) {
		return reviewDao.findAllReview(index).stream().map(ReviewDto::from).collect(Collectors.toList());
	}

	@Override
	public ReviewDto findReview(int reviewId) {
		Review reivew = reviewDao.findReview(reviewId); 
		return ReviewDto.from(reivew);
	}

	@Override
	public boolean updateReview(ReviewDto reviewDto) {
		return reviewDao.updateReview(reviewDto.toEntity());
	}

	@Override
	public boolean deleteReview(int reviewId) {
		return reviewDao.deleteReview(reviewId);
	}

}
