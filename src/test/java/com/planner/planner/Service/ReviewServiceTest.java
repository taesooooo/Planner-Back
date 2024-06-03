package com.planner.planner.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.planner.planner.Common.Page;
import com.planner.planner.Common.PageInfo;
import com.planner.planner.Common.SortCriteria;
import com.planner.planner.Dao.ReviewDao;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.CommonRequestParamDto;
import com.planner.planner.Dto.ReviewDto;
import com.planner.planner.Service.Impl.ReviewServiceImpl;
import com.planner.planner.Util.FileStore;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(ReviewServiceTest.class);

	// junit 임시폴더 테스트 종료시 자동 삭제(이미지 업로드 용도로 사용)
	@TempDir
    public Path folder;
	
	@InjectMocks
	private ReviewServiceImpl reviewService;
	
	@Mock
	private ReviewDao reviewDao;
	
	@Mock
	private AccountService accountService;
	@Spy
	private FileStore fileStore;
	@Mock
	private FileService fileUploadService;
	
	@BeforeEach
	public void setUp() throws Exception {
//		MockitoAnnotations.openMocks(this);
		folder = folder.resolve("Images");
		folder.toFile().mkdirs();
		logger.info(folder.toAbsolutePath().toString());
		fileStore.setBaseLocation(folder.toAbsolutePath().toString() + File.separator);
	}
	
	@Test
	public void 리뷰_리스트_가져오기() throws Exception {
		List<ReviewDto> testList = createReview();
		CommonRequestParamDto paramDto = CommonRequestParamDto.builder()
				.itemCount(10)
				.sortCriteria(SortCriteria.LATEST)
				.pageNum(1)
				.build();
		
		int testTotalCount = testList.size();
		
		when(reviewDao.findAllReview(any(CommonRequestParamDto.class), any(PageInfo.class))).thenReturn(testList);
		when(reviewDao.getTotalCount(any(CommonRequestParamDto.class))).thenReturn(testTotalCount);
		
		Page<ReviewDto> reviewList = reviewService.findAllReview(paramDto);
		
		verify(reviewDao).findAllReview(any(CommonRequestParamDto.class), any(PageInfo.class));
		verify(reviewDao).getTotalCount(any(CommonRequestParamDto.class));
		
		assertThat(reviewList).isNotNull();
		assertThat(reviewList.getList()).isNotNull();
		assertThat(reviewList.getTotalCount()).isEqualTo(testTotalCount);
	}
	
	@Test
	public void 리뷰_리스트_가져오기_키워드() throws Exception {
		List<ReviewDto> testList = createReview();
		CommonRequestParamDto paramDto = CommonRequestParamDto.builder()
				.itemCount(10)
				.sortCriteria(SortCriteria.LATEST)
				.keyword("테스트")
				.pageNum(1)
				.build();
		
		int testTotalCount = 1;
		
		when(reviewDao.findAllReview(any(CommonRequestParamDto.class), any(PageInfo.class))).thenReturn(testList);
		when(reviewDao.getTotalCount(any(CommonRequestParamDto.class))).thenReturn(testTotalCount);
		
		Page<ReviewDto> reviewList = reviewService.findAllReview(paramDto);
		
		verify(reviewDao).findAllReview(any(CommonRequestParamDto.class), any(PageInfo.class));
		verify(reviewDao).getTotalCount(any(CommonRequestParamDto.class));
		
		assertThat(reviewList).isNotNull();
		assertThat(reviewList.getList()).isNotNull();
		assertThat(reviewList.getTotalCount()).isEqualTo(testTotalCount);
	}
	
	@Test
	public void 리뷰_리스트_가져오기_지역코드() throws Exception {
		List<ReviewDto> testList = createReview();
		CommonRequestParamDto paramDto = CommonRequestParamDto.builder()
				.itemCount(10)
				.sortCriteria(SortCriteria.LATEST)
				.keyword("")
				.areaCode(1)
				.pageNum(1)
				.build();
		
		int testTotalCount = 1;
		
		when(reviewDao.findAllReview(any(CommonRequestParamDto.class), any(PageInfo.class))).thenReturn(testList);
		when(reviewDao.getTotalCount(any(CommonRequestParamDto.class))).thenReturn(testTotalCount);
		
		Page<ReviewDto> reviewList = reviewService.findAllReview(paramDto);
		
		verify(reviewDao).findAllReview(any(CommonRequestParamDto.class), any(PageInfo.class));
		verify(reviewDao).getTotalCount(any(CommonRequestParamDto.class));
		
		assertThat(reviewList).isNotNull();
		assertThat(reviewList.getList()).isNotNull();
		assertThat(reviewList.getTotalCount()).isEqualTo(testTotalCount);
	}
	
	@Test
	public void 리뷰_생성() {
		List<String> fileList = new ArrayList<String>();
		fileList.add("test.jpg");
		String thumbnailName = "test";
		
		AccountDto user = AccountDto.builder()
				.accountId(1)
				.email("test@naver.com")
				.nickname("test")
				.username("test")
				.build();
		ReviewDto review = ReviewDto.builder()
				.reviewId(1)
				.plannerId(null)
				.title("테스트 제목")
				.content("내용")
				.fileNames(fileList)
				.build();
		
		when(reviewDao.insertReview(any(ReviewDto.class),any(AccountDto.class), anyString())).thenReturn(1);
		
		int reviewId = reviewDao.insertReview(review, user, thumbnailName);
		
		assertThat(reviewId).isEqualTo(1);
	}
	
	private List<ReviewDto> createReview() {
		List<ReviewDto> reviewList = new ArrayList<ReviewDto>();
		for(int i=0;i<3;i++) {
			ReviewDto review = ReviewDto.builder()
					.reviewId(i)
					.plannerId(i)
					.title("테스트"+i)
					.content("test"+i)
					.areaCode(i)
					.writer("test"+i)
					.writerId(i)
					.likeCount(0)
					.build();
			reviewList.add(review);
		}
		return reviewList;
	}
}
