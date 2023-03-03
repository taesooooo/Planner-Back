package com.planner.planner.Service.Impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planner.planner.Dao.ReviewDao;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.ReviewDto;
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
		
		/*
		 * // 이미지 임시 업로드 폴더로 지정되어 있는 src값 -> 실제 업로드 저장 폴더로 치환 List<String> filePathList
		 * = new ArrayList<String>(); String content = reviewDto.getContent();
		 * StringBuilder sb = new StringBuilder(content);
		 * 
		 * Pattern pattern =
		 * Pattern.compile("<img.*src=([\"|'].*\\/temp\\/(\\.*[^\"]*)[\"|'])"); Matcher
		 * matcher = pattern.matcher(content); String searchText = "/temp/"; String
		 * replaceText = "/"; int correctionValue = 0; while(matcher.find()) { String
		 * filePath = matcher.group(2); String replaceStr =
		 * matcher.group(1).replace(searchText, replaceText);
		 * sb.replace(matcher.start(1)+correctionValue, matcher.end(1)+correctionValue,
		 * replaceStr); correctionValue -= searchText.length() - replaceText.length();
		 * filePathList.add(filePath); }
		 * 
		 * // 임시 업로드 폴더 업로드 파일 -> 저장 폴더로 이동 File destFile = new
		 * File(fileStore.getBaseLocation() + fileStore.getBoradImageDirName());
		 * for(String path : filePathList) { File file = new
		 * File(fileStore.getBaseLocation() + fileStore.getTempDirName() +
		 * File.separator + path); FileUtils.moveFileToDirectory(file, destFile, true);
		 * }
		 * 
		 * ReviewDto newReviewDto = new ReviewDto.Builder()
		 * .setReviewId(reviewDto.getReviewId()) .setTitle(reviewDto.getTitle())
		 * .setContent(sb.toString()) .setWriterId(reviewDto.getWriterId())
		 * .setWriter(reviewDto.getWriter()) .setPlannerId(reviewDto.getPlannerId())
		 * .build();
		 */
		// 게시글 저장
		int reviewId = reviewDao.insertReview(reviewDto, user);
		fileUploadService.updateBoardId(reviewDto.getFileNames(), reviewId);
		return reviewId;
	}

	@Override
	public List<ReviewDto> findAllReview() {
		return reviewDao.findAllReview();
	}

	@Override
	public ReviewDto findReview(int reviewId) {
		return reviewDao.findReview(reviewId); 
	}

	@Override
	public void updateReview(ReviewDto reviewDto) {
		reviewDao.updateReview(reviewDto);
	}

	@Override
	public void deleteReview(int reviewId) {
		reviewDao.deleteReview(reviewId);
	}
}
