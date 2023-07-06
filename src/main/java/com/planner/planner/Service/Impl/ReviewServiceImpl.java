package com.planner.planner.Service.Impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planner.planner.Common.Page;
import com.planner.planner.Common.PageInfo;
import com.planner.planner.Dao.ReviewDao;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.CommonRequestParamDto;
import com.planner.planner.Dto.ReviewDto;
import com.planner.planner.Exception.NotFoundReviewException;
import com.planner.planner.Service.AccountService;
import com.planner.planner.Service.FileUploadService;
import com.planner.planner.Service.ReviewService;
import com.planner.planner.util.FileStore;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {
	private static final Logger logger = LoggerFactory.getLogger(ReviewServiceImpl.class);
	
	private ReviewDao reviewDao;
	
	private AccountService accountService;
	private FileUploadService fileUploadService;
	
	private FileStore fileStore;
	
	public ReviewServiceImpl(ReviewDao reviewDao, AccountService accountService, FileUploadService fileUploadService, FileStore fileStore) {
		this.reviewDao = reviewDao;
		this.accountService = accountService;
		this.fileUploadService = fileUploadService;
		this.fileStore = fileStore;
	}

	@Override
	public int insertReview(int accountId, ReviewDto reviewDto) throws Exception {
		AccountDto user = accountService.findById(accountId);
		
		// 게시글 저장
		int reviewId = reviewDao.insertReview(reviewDto, user);
		fileUploadService.updateBoardId(reviewDto.getFileNames(), reviewId);
		return reviewId;
	}

	@Override
	public Page<ReviewDto> findAllReview(CommonRequestParamDto commonRequestParamDto) throws Exception{
		PageInfo pInfo = new PageInfo.Builder().setPageNum(commonRequestParamDto.getPageNum()).setPageItemCount(10).build();
		
		List<ReviewDto> reviewList = reviewDao.findAllReview(commonRequestParamDto.getSortCriteria(), commonRequestParamDto.getKeyword(), pInfo);
		
		int totalCount = 0;
		String keyword = commonRequestParamDto.getKeyword();
		
		if(keyword != null && !keyword.isEmpty()) {
			totalCount = reviewDao.getTotalCountByKeyword(keyword);
		}
		else {
			totalCount = reviewDao.getTotalCount();
		}
		
		Page<ReviewDto> ReviewListPage = new Page.Builder<ReviewDto>()
				.setList(reviewList)
				.setPageInfo(pInfo)
				.setTotalCount(totalCount)
				.build();

		return ReviewListPage;
	}

	@Override
	public ReviewDto findReview(int reviewId) {
		return reviewDao.findReview(reviewId); 
	}

	@Override
	public void updateReview(int reviewId, ReviewDto reviewDto) {
		reviewDao.updateReview(reviewId, reviewDto);
	}

	@Override
	public void deleteReview(int reviewId) {
		reviewDao.deleteReview(reviewId);
	}
}
