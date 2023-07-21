package com.planner.planner.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
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
import com.planner.planner.Common.SortCriteria;
import com.planner.planner.Dao.ReviewDao;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.CommonRequestParamDto;
import com.planner.planner.Dto.ReviewDto;
import com.planner.planner.Exception.NotFoundReviewException;
import com.planner.planner.Service.Impl.ReviewServiceImpl;
import com.planner.planner.Util.FileStore;

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
	public void 리뷰_리스트_가져오기() throws Exception {
		List<ReviewDto> testList = createReview();
		CommonRequestParamDto paramDto = new CommonRequestParamDto.Builder()
				.setItemCount(10)
				.setSortCriteria(SortCriteria.LATEST)
				.setPageNum(1)
				.build();
		
		int testTotalCount = testList.size();
		
		when(reviewDao.findAllReview(any(SortCriteria.class), anyString(), any(PageInfo.class))).thenReturn(testList);
		when(reviewDao.getTotalCount()).thenReturn(testTotalCount);
		
		Page<ReviewDto> reviewList = reviewService.findAllReview(paramDto);
		
		verify(reviewDao).findAllReview(any(SortCriteria.class), any(), any(PageInfo.class));
		verify(reviewDao).getTotalCount();
		
		assertThat(reviewList).isNotNull();
		assertThat(reviewList.getList()).isNotNull();
		assertThat(reviewList.getTotalCount()).isEqualTo(testTotalCount);
	}
	
	@Test
	public void 리뷰_리스트_가져오기_키워드() throws Exception {
		List<ReviewDto> testList = createReview();
		CommonRequestParamDto paramDto = new CommonRequestParamDto.Builder()
				.setItemCount(10)
				.setSortCriteria(SortCriteria.LATEST)
				.setKeyword("테스트")
				.setPageNum(1)
				.build();
		
		int testTotalCount = 1;
		
		when(reviewDao.findAllReview(any(SortCriteria.class), anyString(), any(PageInfo.class))).thenReturn(testList);
		when(reviewDao.getTotalCountByKeyword(any(String.class))).thenReturn(testTotalCount);
		
		Page<ReviewDto> reviewList = reviewService.findAllReview(paramDto);
		
		verify(reviewDao).findAllReview(any(SortCriteria.class), any(), any(PageInfo.class));
		verify(reviewDao).getTotalCountByKeyword(anyString());
		
		assertThat(reviewList).isNotNull();
		assertThat(reviewList.getList()).isNotNull();
		assertThat(reviewList.getTotalCount()).isEqualTo(testTotalCount);
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
}
