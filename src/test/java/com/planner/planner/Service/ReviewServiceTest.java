package com.planner.planner.Service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.planner.planner.Dao.ReviewDao;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.ReviewDto;
import com.planner.planner.Service.Impl.ReviewServiceImpl;
import com.planner.planner.util.FileStore;

public class ReviewServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(ReviewServiceTest.class);

	// junit 임시폴더 테스트 종료시 자동 삭제(이미지 업로드 용도로 사용)
	@Rule
    public final TemporaryFolder folder = new TemporaryFolder();
	
	@InjectMocks
	private ReviewServiceImpl reviewService;
	
	@Mock
	private ReviewDao reviewDao;
	
	@Mock
	private AccountService accountService;
	@Spy
	private FileStore fileStore;
	@Mock
	private FileUploadService fileUploadService;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		fileStore.setBaseLocation(folder.newFolder("Images").getAbsolutePath() + File.separator);
	}

	@Test
	public void 리뷰_생성() throws Exception {
		AccountDto user = new AccountDto.Builder().setAccountId(1).setEmail("test@naver.com").setUserName("test").setNickName("test").build();
		ReviewDto reviewDto = new ReviewDto.Builder().setReviewId(1).setPlannerId(1).setTitle("테스트1").setContent("테스트1내용").setWriter("test").setWriterId(1).setLikeCount(0).build();
		when(accountService.findById(1)).thenReturn(user);
		when(reviewDao.insertReview(any(), any())).thenReturn(1);
		
		int reviewId = reviewService.insertReview(1, reviewDto);
		assertEquals(reviewId, 1);
	}
	
	@Test
	public void 리뷰_모두가져오기() {
		List<ReviewDto> list = new ArrayList<ReviewDto>();
		for(int i=0;i<3;i++) {
			ReviewDto review = new ReviewDto.Builder().setReviewId(i).setPlannerId(i).setTitle("테스트").setContent("test").setWriter("test").setWriterId(i).setLikeCount(0).build();
			list.add(review);
		}
		
		when(reviewDao.findAllReview()).thenReturn(list);
		
		List<ReviewDto> reviewList = reviewService.findAllReview();
	}
	
	@Test
	public void 리뷰_가져오기() {
		ReviewDto testReview = new ReviewDto.Builder().setReviewId(1).setPlannerId(1).setTitle("테스트").setContent("test").setWriter("test").setWriterId(1).setLikeCount(0).build();
		
		when(reviewDao.findReview(1)).thenReturn(testReview);
		
		ReviewDto review = reviewService.findReview(1);
		
		assertNotNull(review);
	}
	
	@Test
	public void 리뷰_수정() {
		ReviewDto reviewDto = new ReviewDto.Builder().setReviewId(1).setPlannerId(1).setTitle("테스트1").setContent("테스트1내용").setWriter("test").setWriterId(1).setLikeCount(0).build();
	
		reviewService.updateReview(reviewDto);
	}
	
	@Test
	public void 리뷰_삭제() {
		reviewService.deleteReview(1);
	}
}
