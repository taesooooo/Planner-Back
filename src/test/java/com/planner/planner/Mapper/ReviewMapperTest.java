package com.planner.planner.Mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import com.planner.planner.Common.PageInfo;
import com.planner.planner.Common.PostType;
import com.planner.planner.Common.SortCriteria;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.CommentDto;
import com.planner.planner.Dto.CommonRequestParamDto;
import com.planner.planner.Dto.ReviewDto;

@ActiveProfiles("test")
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReviewMapperTest {
	@Autowired
	private ReviewMapper mapper;
	
	@DisplayName("리뷰 생성")
	@Test
	public void createReviewTest() {
		AccountDto user = AccountDto.builder()
				.accountId(1)
				.email("test@naver.com")
				.nickname("test")
				.build();
		ReviewDto createReview = ReviewDto.builder()
				.plannerId(1)
				.title("test")
				.content("test")
				.areaCode(null)
				.build();
		
		mapper.createReview(createReview, user, "");
		
		assertThat(createReview.getReviewId()).isEqualTo(4);
	}
	
	@DisplayName("리뷰 가져오기")
	@Test
	public void findById() {	
		CommentDto reComment3 = CommentDto.builder()
				.commentId(3)
				.reviewId(1)
				.writerId(1)
				.writer("test")
				.content("아니 이렇게 좋다구요?")
				.parentId(2)
				.build();
		
		CommentDto reComment2 = CommentDto.builder()
				.commentId(2)
				.reviewId(1)
				.writerId(1)
				.writer("test")
				.content("아니 이렇게 좋다구요?")
				.parentId(1)
				.build();
	
		CommentDto comment = CommentDto.builder()
				.commentId(1)
				.reviewId(1)
				.writerId(1)
				.writer("test")
				.content("아니 이렇게 좋다구요?")
				.parentId(null)
				.build();
		
		List<CommentDto> comments = Arrays.asList(comment, reComment2, reComment3);
		
		ReviewDto testReview = ReviewDto.builder()
				.reviewId(1)
				.plannerId(1)
				.title("이렇게 재미있는 여행! 후기 그치만 또 간다 근데 힘들어")
				.content("재미있었다링")
				.areaCode(null)
				.thumbnail("")
				.writer("test")
				.writerId(1)
				.comments(comments)
				.build();
		
		// 댓글을 한번에 가져와 부모, 자식 댓글 설정은 서비스에서 진행
		ReviewDto findReview = mapper.findById(1);
		
		assertThat(findReview).usingRecursiveComparison()
				.ignoringFields("createDate","updateDate","comments.createDate", "comments.updateDate")
				.isEqualTo(testReview);
	}
	
	@DisplayName("모든 리뷰 가져오기")
	@Test
	public void findAll() {
		List<ReviewDto> testReviews = Arrays.asList(
				ReviewDto.builder()
				.reviewId(3)
				.plannerId(1)
				.title("제목")
				.content("재미있었다링")
				.areaCode(2)
				.thumbnail("")
				.writer("test3")
				.writerId(3)
				.likeCount(0)
				.build(),
				ReviewDto.builder()
				.reviewId(2)
				.plannerId(1)
				.title("테스트 리뷰")
				.content("재미있었다링")
				.areaCode(1)
				.thumbnail("")
				.writer("test2")
				.writerId(2)
				.likeCount(0)
				.build(),
				ReviewDto.builder()
				.reviewId(1)
				.plannerId(1)
				.title("이렇게 재미있는 여행! 후기 그치만 또 간다 근데 힘들어")
				.content("재미있었다링")
				.areaCode(null)
				.thumbnail("")
				.writer("test")
				.writerId(1)
				.likeCount(0)
				.build()
				);
		
		CommonRequestParamDto commonRequestParamDto = CommonRequestParamDto.builder()
				.itemCount(10)
				.sortCriteria(SortCriteria.LATEST)
				.keyword("")
				.pageNum(1)
				.build();
		
		PageInfo pInfo = PageInfo.builder()
				.pageNum(commonRequestParamDto.getPageNum())
				.pageItemCount(commonRequestParamDto.getItemCount())
				.build();
		
		List<ReviewDto> findReviews = mapper.findAll(commonRequestParamDto, pInfo);
		
		assertThat(findReviews).usingRecursiveComparison()
				.ignoringFields("createDate", "updateDate")
				.isEqualTo(testReviews);
	}
	
	@DisplayName("리뷰 수정")
	@Test
	public void updateReview() {
		ReviewDto updateReview = ReviewDto.builder()
				.title("수정 테스트")
				.content("수정 테스트 내용")
				.areaCode(2)
				.build();
		
		int result = mapper.updateReview(1, updateReview);
		
		assertThat(result).isEqualTo(1);
	}
	
	@DisplayName("리뷰 썸네일 수정")
	@Test
	public void updateReviewThumbnail() {
		String updateThumbnail = "";
		
		int result = mapper.updateReviewThumbnail(1, updateThumbnail);
		
		assertThat(result).isEqualTo(1);
	}
	
	@DisplayName("리뷰 삭제")
	@Test
	public void deleteReview() {
		int result = mapper.deleteReview(1);
		
		assertThat(result).isEqualTo(1);
	}
	
	@DisplayName("리뷰 개수 가져오기")
	@Test
	public void getTotalCount() {
		CommonRequestParamDto commonRequestParamDto = CommonRequestParamDto.builder()
				.itemCount(10)
				.sortCriteria(SortCriteria.LATEST)
				.keyword("")
				.pageNum(1)
				.build();

		int totalCount = mapper.findTotalCount(commonRequestParamDto);
		
		assertThat(totalCount).isEqualTo(3);
	}
}
