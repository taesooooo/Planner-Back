package com.planner.planner.Service.Impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.planner.planner.Common.Page;
import com.planner.planner.Common.PageInfo;
import com.planner.planner.Dao.FileUploadDao;
import com.planner.planner.Dao.ReviewDao;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.CommonRequestParamDto;
import com.planner.planner.Dto.FileInfoDto;
import com.planner.planner.Dto.ReviewDto;
import com.planner.planner.Exception.ReviewNotFoundException;
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
		int reviewId = reviewDao.createReview(reviewDto, user, thumbnailName);

		List<String> newFileNames = reviewDto.getFileNames();
		newFileNames.add(thumbnailName);
		
		fileUploadService.updateBoardId(newFileNames, reviewId);

		return reviewId;
	}

	@Override
	public Page<ReviewDto> findAllReview(CommonRequestParamDto commonRequestParamDto) throws Exception {
		PageInfo pInfo = PageInfo.builder().pageNum(commonRequestParamDto.getPageNum()).pageItemCount(10)
				.build();

		List<ReviewDto> reviewList = reviewDao.findAll(commonRequestParamDto, pInfo);

		int totalCount = 0;

		totalCount = reviewDao.getTotalCount(commonRequestParamDto);

		Page<ReviewDto> ReviewListPage = new Page.Builder<ReviewDto>().setList(reviewList).setPageInfo(pInfo)
				.setTotalCount(totalCount).build();

		return ReviewListPage;
	}

	@Override
	public ReviewDto findReview(int reviewId) {
		return reviewDao.findById(reviewId);
	}

	@Override
	public void updateReview(int accountId, int reviewId, ReviewDto reviewDto) throws Exception {
		ReviewDto review = reviewDao.findById(reviewId);
		if(review == null) {
			throw new ReviewNotFoundException("작성된 글을 찾을 수 없습니다.");
		} 
		
		// 썸네일 생성
		// 현재 썸네일과 다른지 확인
		if(review.getThumbnail() == null || !review.getThumbnail().equals(reviewDto.getFileNames().get(0))) {
			String thumbnailName = null;
			String fileName = reviewDto.getFileNames().get(0);
			
			FileInfoDto thumbnail = fileUploadDao.getFileInfo(StringUtils.stripFilenameExtension(fileName) + "_thumb." + StringUtils.getFilenameExtension(fileName));
			if(thumbnail != null) {
				thumbnailName = thumbnail.getFileName();
			}
			else {
				thumbnailName = createThumbnail(accountId, reviewDto);				
			}
			
			reviewDao.updateReviewThumbnail(reviewId, thumbnailName);
			
			// 기존 썸네일 연결 삭제 및 새로운 썸네일 연결
			fileUploadDao.updateBoardId(0, Arrays.asList(review.getThumbnail()));
			fileUploadDao.updateBoardId(reviewId, Arrays.asList(thumbnailName));
		}
		
		reviewDao.updateReview(reviewId, reviewDto);
		
		// 이미지 리스트 다시 연결
		fileUploadDao.updateBoardId(reviewId, reviewDto.getFileNames());
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
				FileInfoDto thumbnailFileInfoDto = FileInfoDto.builder()
						.fileId(fileInfo.getFileId())
						.fileWriterId(fileInfo.getFileWriterId())
						.fileBoradId(fileInfo.getFileBoradId())
						.fileName(thumbnailName)
						.filePath(fileStore.getThumbnailDir() + thumbnailName)
						.fileType(fileInfo.getFileType())
						.build();
				
				fileUploadDao.createFileInfo(accountId, thumbnailFileInfoDto);
			}

		}
		
		return thumbnailName;
	}
}
