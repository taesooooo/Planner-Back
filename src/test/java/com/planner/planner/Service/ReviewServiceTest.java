package com.planner.planner.Service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
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

import com.planner.planner.Common.Page;
import com.planner.planner.Common.PageInfo;
import com.planner.planner.Dao.ReviewDao;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.ReviewDto;
import com.planner.planner.Dto.ReviewListDto;
import com.planner.planner.Exception.NotFoundReviewException;
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
	public void 리뷰_리스트_가져오기() throws Exception {
		List<ReviewDto> testList = createReview();
		PageInfo pInfo = new PageInfo.Builder().setPageNum(1).setPageItemCount(10).build();
		int testTotalCount = testList.size();
		
		when(reviewDao.findAllReview(any())).thenReturn(testList);
		when(reviewDao.findTotalCount()).thenReturn(testTotalCount);
		
		Page<ReviewDto> reviewList = reviewService.findAllReview(anyInt());
		
		verify(reviewDao).findAllReview(any());
		verify(reviewDao).findTotalCount();
		
		assertEquals(testList.get(0).getReviewId(), reviewList.getList().get(0).getReviewId());
	}
	
	@Test(expected = NotFoundReviewException.class)
	public void 리뷰_리스트_가져오기_리스트_없을경우() throws Exception {
		//List<ReviewDto> testList = createReview();
		//int testTotalCount = testList.size();
		
		when(reviewDao.findAllReview(any())).thenReturn(new ArrayList<ReviewDto>());
		when(reviewDao.findTotalCount()).thenReturn(0);
		
		Page<ReviewDto> reviewList = reviewService.findAllReview(anyInt());
		
		verify(reviewDao).findAllReview(any());
		//verify(reviewDao).findTotalCount();
		
		//assertEquals(testList.get(0).getReviewId(), reviewList.getList().get(0).getReviewId());
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
	
	private List<ReviewDto> createReview() {
		List<ReviewDto> reviewList = new ArrayList<ReviewDto>();
		for(int i=0;i<3;i++) {
			ReviewDto review = new ReviewDto.Builder()
					.setReviewId(i)
					.setPlannerId(i)
					.setTitle("테스트"+i)
					.setContent("test"+i)
					.setWriter("test"+i)
					.setWriterId(i)
					.setLikeCount(0)
					.build();
			reviewList.add(review);
		}
		return reviewList;
	}
	
//	private ReviewListDto createReviewList() {
//		List<ReviewDto> reviewList = new ArrayList<ReviewDto>();
//		for(int i=0;i<3;i++) {
//			ReviewDto review = new ReviewDto.Builder()
//					.setReviewId(i)
//					.setPlannerId(i)
//					.setTitle("테스트"+i)
//					.setContent("test"+i)
//					.setWriter("test"+i)
//					.setWriterId(i)
//					.setLikeCount(0)
//					.build();
//			reviewList.add(review);
//		}
//		PagingInfo info = new PagingInfo.Builder()
//				.setTotalCount(3)
//				.build();
//		
//		ReviewListDto list = new ReviewListDto.Builder()
//				.setReviewList(reviewList)
//				.setPagingInfo(info)
//				.build();
//		
//		return list;
//	}
}
