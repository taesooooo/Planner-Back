package com.planner.planner.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.planner.planner.Dao.ReviewDao;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.ReviewDto;
import com.planner.planner.Entity.Review;

@Service
public class ReviewServiceImpl implements ReviewService {
	private static final Logger logger = LoggerFactory.getLogger(ReviewServiceImpl.class);
	
	private ReviewDao reviewDao;
	
	private AccountService accountService;
	
	public ReviewServiceImpl(ReviewDao reviewDao, AccountService accountService) {
		this.reviewDao = reviewDao;
		this.accountService = accountService;
	}

	@Override
	public boolean insertReview(int accountId, ReviewDto reviewDto) {
		AccountDto user = accountService.findById(accountId);
		
		return reviewDao.insertReview(reviewDto.toEntity(), user.toEntity());
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
