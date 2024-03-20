package com.planner.planner.Service.Impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planner.planner.Common.Page;
import com.planner.planner.Common.PageInfo;
import com.planner.planner.Dao.FileUploadDao;
import com.planner.planner.Dao.ReviewDao;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.CommonRequestParamDto;
import com.planner.planner.Dto.FileInfoDto;
import com.planner.planner.Dto.ReviewDto;
import com.planner.planner.Exception.NotFoundReviewException;
import com.planner.planner.Service.AccountService;
import com.planner.planner.Service.FileService;
import com.planner.planner.Service.ReviewService;
import com.planner.planner.Util.FileStore;
import com.planner.planner.Util.ImageUtil;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {
	private static final Logger logger = LoggerFactory.getLogger(ReviewServiceImpl.class);

	private ReviewDao reviewDao;
	private FileUploadDao fileUploadDao;

	private AccountService accountService;
	private FileService fileUploadService;

	private FileStore fileStore;
	private ImageUtil imageUtil;

	public ReviewServiceImpl(ReviewDao reviewDao, AccountService accountService, FileUploadDao fileUploadDao,
			FileService fileUploadService, FileStore fileStore, ImageUtil imageUtil) {
		this.reviewDao = reviewDao;
		this.accountService = accountService;
		this.fileUploadDao = fileUploadDao;
		this.fileUploadService = fileUploadService;
		this.fileStore = fileStore;
		this.imageUtil = imageUtil;
	}

	@Override
	public int insertReview(int accountId, ReviewDto reviewDto) throws Exception {
		AccountDto user = accountService.findById(accountId);

		// 썸네일 생성
		String thumbnailName = createThumbnail(reviewDto);

		// 게시글 저장
		int reviewId = reviewDao.insertReview(reviewDto, user, thumbnailName);

		fileUploadService.updateBoardId(reviewDto.getFileNames(), reviewId);

		return reviewId;
	}

	@Override
	public Page<ReviewDto> findAllReview(CommonRequestParamDto commonRequestParamDto) throws Exception {
		PageInfo pInfo = new PageInfo.Builder().setPageNum(commonRequestParamDto.getPageNum()).setPageItemCount(10)
				.build();

		List<ReviewDto> reviewList = reviewDao.findAllReview(commonRequestParamDto, pInfo);

		int totalCount = 0;

		totalCount = reviewDao.getTotalCount(commonRequestParamDto);

		Page<ReviewDto> ReviewListPage = new Page.Builder<ReviewDto>().setList(reviewList).setPageInfo(pInfo)
				.setTotalCount(totalCount).build();

		return ReviewListPage;
	}

	@Override
	public ReviewDto findReview(int reviewId) {
		return reviewDao.findReview(reviewId);
	}

	@Override
	public void updateReview(int reviewId, ReviewDto reviewDto) throws Exception {
		// 썸네일 생성
		String thumbnailName = createThumbnail(reviewDto);

		reviewDao.updateReview(reviewId, reviewDto);
		
		reviewDao.updateReviewThumbnail(reviewId, thumbnailName);
	}

	@Override
	public void deleteReview(int reviewId) {
		reviewDao.deleteReview(reviewId);
	}

	private String createThumbnail(ReviewDto reviewDto) throws IOException {
		// 게시글 썸네일 생성
		List<String> fileList = reviewDto.getFileNames();
		String thumbnailName = null;
		if (fileList != null && !fileList.isEmpty()) {
			// 게시글 이미지에서 첫번째 이미지를 가져와 생성
			FileInfoDto fileInfo = fileUploadDao.getFileInfo(fileList.get(0));
			if (fileInfo != null) {
				// 이미지 리사이징
				BufferedImage resizeImage = imageUtil.resize(new File(fileInfo.getFilePath()));
				// 썸네일 저장
				thumbnailName = fileStore.saveThumbnail(resizeImage, new File(fileInfo.getFileName()));
			}

		}
		
		return thumbnailName;
	}
}
