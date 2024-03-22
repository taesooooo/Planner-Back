package com.planner.planner.Service.Impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planner.planner.Common.FileInfo;
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
		String thumbnailName = createThumbnail(user.getAccountId(), reviewDto);
		
		// 게시글 저장
		int reviewId = reviewDao.insertReview(reviewDto, user, thumbnailName);

		List<String> newFileNames = reviewDto.getFileNames();
		newFileNames.add(thumbnailName);
		
		fileUploadService.updateBoardId(newFileNames, reviewId);

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
	public void updateReview(int accountId, int reviewId, ReviewDto reviewDto) throws Exception {
		ReviewDto review = reviewDao.findReview(reviewId);
		if(review == null) {
			throw new NotFoundReviewException("작성된 글을 찾을 수 없습니다.");
		}
		// 썸네일 생성
		String thumbnailName = createThumbnail(accountId, reviewDto);

		reviewDao.updateReview(reviewId, reviewDto);
		
		reviewDao.updateReviewThumbnail(reviewId, thumbnailName);
		
		// 기존 썸네일 연결 삭제 및 새로운 썸네일 연결
		fileUploadDao.updateBoardId(0, Arrays.asList(review.getThumbnail()));
		fileUploadDao.updateBoardId(reviewId, Arrays.asList(thumbnailName));
	}

	@Override
	public void deleteReview(int reviewId) {
		reviewDao.deleteReview(reviewId);
	}

	private String createThumbnail(int accountId, ReviewDto reviewDto) throws IOException {
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
				
				// 썸네일 정보 DB 저장
				FileInfoDto thumbnailFileInfoDto = new FileInfoDto();
				thumbnailFileInfoDto.setFileId(fileInfo.getFileId());
				thumbnailFileInfoDto.setFileWriterId(fileInfo.getFileWriterId());
				thumbnailFileInfoDto.setFileBoradId(fileInfo.getFileBoradId());
				thumbnailFileInfoDto.setFileName(thumbnailName);
				thumbnailFileInfoDto.setFilePath(fileStore.getThumbnailDir() + thumbnailName);
				thumbnailFileInfoDto.setFileType(fileInfo.getFileType());
				
				fileUploadDao.createFileInfo(accountId, thumbnailFileInfoDto);
			}

		}
		
		return thumbnailName;
	}
}
