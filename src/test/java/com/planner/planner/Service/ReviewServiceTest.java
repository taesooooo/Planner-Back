package com.planner.planner.Service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.planner.planner.Dao.ReviewDao;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.ReviewDto;
import com.planner.planner.Entity.Review;

public class ReviewServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(ReviewServiceTest.class);

	@InjectMocks
	private ReviewServiceImpl reviewService;
	
	@Mock
	private ReviewDao reviewDao;
	
	@Mock
	private AccountService accountService;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void 리뷰_생성_테스트() {
		AccountDto user = new AccountDto.Builder().setAccountId(1).setEmail("test@naver.com").setUserName("test").setNickName("test").build();
		ReviewDto reviewDto = new ReviewDto.Builder().setReviewId(1).setPlannerId(1).setTitle("테스트1").setContent("테스트1내용").setWriter("test").setWriterId(1).setLikeCount(0).build();
		when(accountService.findById(1)).thenReturn(user);
		when(reviewDao.insertReview(any(), any())).thenReturn(true);
	
		assertTrue(reviewService.insertReview(1, reviewDto));
	}
	
	@Test
	public void 리뷰_모두가져오기_테스트() {
		List<Review> list = new ArrayList<Review>();
		for(int i=0;i<3;i++) {
			Review review = new Review.Builder().setReviewId(i).setPlannerId(i).setTitle("테스트").setContent("test").setWriter("test").setWriterId(i).setLikeCount(0).build();
			list.add(review);
		}
		
		when(reviewDao.findAllReview(1)).thenReturn(list);
		
		assertEquals(reviewService.findAllReview(1).get(0).getReviewId(), list.get(0).getReviewId());
	}
	
	@Test
	public void 리뷰_가져오기_테스트() {
		Review review = new Review.Builder().setReviewId(1).setPlannerId(1).setTitle("테스트").setContent("test").setWriter("test").setWriterId(1).setLikeCount(0).build();
		
		when(reviewDao.findReview(0)).thenReturn(review);
		
		assertEquals(reviewService.findReview(0).getReviewId(), review.getReviewId());
	}
	
	@Test
	public void 리뷰_수정_테스트() {
		ReviewDto reviewDto = new ReviewDto.Builder().setReviewId(1).setPlannerId(1).setTitle("테스트1").setContent("테스트1내용").setWriter("test").setWriterId(1).setLikeCount(0).build();
		
		when(reviewDao.updateReview(any())).thenReturn(true);
		
		assertTrue(reviewService.updateReview(reviewDto));
	}
	
	@Test
	public void 리뷰_삭제_테스트() {
		when(reviewDao.deleteReview(1)).thenReturn(true);
		
		assertTrue(reviewService.deleteReview(1));
	}

}
