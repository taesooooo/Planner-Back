package com.planner.planner.Mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.ActiveProfiles;

import com.planner.planner.Common.PageInfo;
import com.planner.planner.Common.SortCriteria;
import com.planner.planner.Dto.CommonRequestParamDto;
import com.planner.planner.Dto.SpotLikeCountDto;
import com.planner.planner.Dto.SpotLikeDto;

@ActiveProfiles("test")
@MybatisTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class SpotMapperTest {
	@Autowired
	private SpotMapper mapper;
	
	@DisplayName("여행지 좋아요")
	@Test
	public void createSpotLike() throws Exception {
		SpotLikeDto testLike = SpotLikeDto.builder()
				.contentId(123456)
				.areaCode(null)
				.title("여행지 이름")
				.image("")
				.build();
		
		boolean result = mapper.createSpotLike(1, testLike);
		
		assertThat(result).isTrue();
	}
	
	@DisplayName("여행지 좋아요 취소")
	@Test
	public void deleteSpotLike() throws Exception {
		boolean result = mapper.deleteSpotLike(1, 2733967);
		
		assertThat(result).isTrue();
	}
	
	@DisplayName("콘텐츠 아이디로 여행지 좋아요 정보 가져오기")
	@Test
	public void findSpotLike() throws Exception {
		SpotLikeDto testLike = SpotLikeDto.builder()
				.likeId(1)
				.accountId(1)
				.contentId(2733967)
				.areaCode(null)
				.title("테스트제목")
				.image("테스트이미지주소")
				.likeDate(null)
				.build();
		
		SpotLikeDto findSpotLike = mapper.findSpotLike(1, 2733967);
		
		assertThat(findSpotLike).usingRecursiveComparison()
				.ignoringFields("likeDate")
				.isEqualTo(testLike);
	}
	
	@DisplayName("계정 아이디로 여행지 좋아요 정보 가져오기")
	@Test
	public void findSpotLikeList() throws Exception {
		List<SpotLikeDto> testLike = Arrays.asList(
				SpotLikeDto.builder()
					.likeId(5)
					.accountId(1)
					.contentId(2733969)
					.areaCode(1)
					.title("제목")
					.image("테스트이미지주소")
					.likeDate(null)
					.build(),
				SpotLikeDto.builder()
					.likeId(4)
					.accountId(1)
					.contentId(2733968)
					.areaCode(2)
					.title("테스트제목2")
					.image("테스트이미지주소")
					.likeDate(null)
					.build(),
				SpotLikeDto.builder()
					.likeId(1)
					.accountId(1)
					.contentId(2733967)
					.areaCode(null)
					.title("테스트제목")
					.image("테스트이미지주소")
					.likeDate(null)
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
		
		List<SpotLikeDto> findSpotLikeList = mapper.findSpotLikeList(1, commonRequestParamDto, pInfo);
		
		assertThat(findSpotLikeList).usingRecursiveComparison()
				.ignoringFields("likeDate")
				.isEqualTo(testLike);
	}
	
	@DisplayName("여행지 좋아요 정보 총 개수 가져오기")
	@Test
	public void findListTotalCount() throws Exception {
		CommonRequestParamDto commonRequestParamDto = CommonRequestParamDto.builder()
				.itemCount(10)
				.sortCriteria(SortCriteria.LATEST)
				.keyword("")
				.pageNum(1)
				.build();
		
		int result = mapper.findListTotalCount(1, commonRequestParamDto);
		
		assertThat(result).isEqualTo(3);
	}
	
	@DisplayName("컨텐츠 아이디로 여행지 좋아요 개수 가져오기")
	@Test
	public void findLikeCountByContentId() throws Exception {
		int result = mapper.findSpotLikeCountByContentId(2733967);
		
		assertThat(result).isEqualTo(3);
	}
	
	@DisplayName("컨텐츠 아이디 리스트로 여행지 좋아요 개수 가져오기")
	@Test
	public void findLikeCountByContentIdList() throws Exception {
		List<Integer> testContentIdList = Arrays.asList(2733967, 2733968, 2733969);
		
		List<SpotLikeCountDto> result = mapper.findSpotLikeCountByContentIdList(testContentIdList);
		
		assertThat(result).extracting("contentId", "likeCount")
				.containsExactly(
						tuple(2733967, 3),
						tuple(2733968, 1),
						tuple(2733969, 1));
	}
	
	@DisplayName("컨텐츠 아이디로 여행지 좋아요 상태 가져오기")
	@Test
	public void findLikeStateByContentId() throws Exception {
		List<Integer> testContentIdList = Arrays.asList(2733967);
		boolean result = mapper.findSpotLikeStateByContentId(1, testContentIdList);
		
		assertThat(result).isTrue();
	}
	
	@DisplayName("컨텐츠 아이디 리스트로 여행지 좋아요 상태 가져오기")
	@Test
	public void findLikeStateByContentIdList() throws Exception {
		List<Integer> testContentIdList = Arrays.asList(2733967, 2733968, 2733969);
		List<SpotLikeDto> testSpotLikeList = Arrays.asList(
				SpotLikeDto.builder()
					.likeId(1)
					.accountId(1)
					.contentId(2733967)
					.areaCode(null)
					.title("테스트제목")
					.image("테스트이미지주소")
					.likeDate(null)
					.build(),
				SpotLikeDto.builder()
					.likeId(4)
					.accountId(1)
					.contentId(2733968)
					.areaCode(2)
					.title("테스트제목2")
					.image("테스트이미지주소")
					.likeDate(null)
					.build(),
				SpotLikeDto.builder()
					.likeId(5)
					.accountId(1)
					.contentId(2733969)
					.areaCode(1)
					.title("제목")
					.image("테스트이미지주소")
					.likeDate(null)
					.build());
		
		List<SpotLikeDto> result = mapper.findSpotLikeStateByContentIdList(1, testContentIdList);
		
		assertThat(result).usingRecursiveComparison()
				.ignoringFields("likeDate")
				.isEqualTo(testSpotLikeList);
	}
}
