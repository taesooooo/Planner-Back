package com.planner.planner.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.planner.planner.Common.Page;
import com.planner.planner.Common.PageInfo;
import com.planner.planner.Common.SortCriteria;
import com.planner.planner.Dao.Impl.SpotDaoImpl;
import com.planner.planner.Dto.CommonRequestParamDto;
import com.planner.planner.Dto.SpotDetailDto;
import com.planner.planner.Dto.SpotDto;
import com.planner.planner.Dto.SpotLikeCountDto;
import com.planner.planner.Dto.SpotLikeDto;
import com.planner.planner.Dto.SpotListDto;
import com.planner.planner.Dto.OpenApi.AreaCodeDto;
import com.planner.planner.Dto.OpenApi.CommonBasedDto;
import com.planner.planner.Dto.OpenApi.CommonDetailDto;
import com.planner.planner.Dto.OpenApi.CommonListDto;
import com.planner.planner.Dto.OpenApi.OpenApiDto;
import com.planner.planner.Service.Impl.OpenAPIServiceImpl;
import com.planner.planner.Service.Impl.SpotServiceImpl;

@ExtendWith(MockitoExtension.class)
public class SpotServiceTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(SpotServiceTest.class);

	@Mock
	private SpotDaoImpl spotDao;
	@Mock
	private OpenAPIServiceImpl apiSerivce;

	@InjectMocks
	private SpotServiceImpl spotService;

	@BeforeEach
	public void setup() {
//		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void 여행지_지역코드_가져오기() throws Exception {
		List<AreaCodeDto> item = new ArrayList<AreaCodeDto>();
		item.add(AreaCodeDto.builder().rnum("1").code("1").name("서울").build());
		item.add(AreaCodeDto.builder().rnum("2").code("2").name("인천").build());
		CommonListDto<AreaCodeDto> testList = new CommonListDto<AreaCodeDto>(item, 1, 1, 2);

		when(apiSerivce.getAreaNum()).thenReturn(testList);

		SpotListDto<AreaCodeDto> data = spotService.getAreaNum();

		assertThat(data).isNotNull();
		assertThat(data.getItems()).isNotNull()
				.usingRecursiveFieldByFieldElementComparator()
				.containsExactlyElementsOf(item);

		assertThat(data.getTotalCount()).isEqualTo(testList.getTotalCount());
	}

	@Test
	public void 여행지_지역기반리스트_가져오기() throws Exception {
		int accountId = 1;
		
		OpenApiDto param = OpenApiDto.builder()
				.areaCode(1)
				.contentTypeId(12)
				.pageNo(1)
				.build();
		
		CommonListDto<CommonBasedDto> item = createBasedDtoList();
		
		List<SpotLikeDto> spotLikeList = new ArrayList<SpotLikeDto>();
		spotLikeList.add(SpotLikeDto.builder()
				.likeId(1)
				.accountId(1)
				.contentId(2733967)
				.likeDate(LocalDate.now())
				.build());
		spotLikeList.add(SpotLikeDto.builder()
				.likeId(2)
				.accountId(1)
				.contentId(2763807)
				.likeDate(LocalDate.now())
				.build());
		
		List<SpotLikeCountDto> spotLikeCountList = new ArrayList<SpotLikeCountDto>();
		spotLikeCountList.add(SpotLikeCountDto.builder()
				.contentId(2733967)
				.likeCount(1)
				.build());
		spotLikeCountList.add(SpotLikeCountDto.builder()
				.contentId(2763807)
				.likeCount(1)
				.build());

		//
		when(apiSerivce.getAreaList(any(OpenApiDto.class))).thenReturn(item);
		when(spotDao.findSpotLikeStateByContentIdList(anyInt(), any())).thenReturn(spotLikeList);
		when(spotDao.findSpotLikeCountByContentIdList(any())).thenReturn(spotLikeCountList);

		SpotListDto<SpotDto> resultList = spotService.getAreaList(accountId, param);

		assertThat(resultList).isNotNull();
		assertThat(resultList.getItems())
				.isNotNull()
				.extracting("basedSpot", "likeCount", "likeState")
				.containsExactly(tuple(item.getItems().get(0), 1, true), tuple(item.getItems().get(1), 1, true));
		
	}

//	@Test
//	public void 여행지_위치기반리스트_가져오기() throws Exception {
//		int accountId = 1;
//		int areaCode = 1;
//		int contentTypeId = 12;
//		int index = 1;
//		
//		CommonListDto<CommonBasedDto> item = createBasedDtoList();
//		
//		List<SpotLikeDto> spotLikeList = new ArrayList<SpotLikeDto>();
//		spotLikeList.add(SpotLikeDto.builder()
//				.likeId(1)
//				.accountId(1)
//				.contentId(2733967)
//				.likeDate(LocalDate.now())
//				.build());
//
//		//
//		when(apiSerivce.getLocationBasedList(anyDouble(), anyDouble(), anyInt(), anyInt())).thenReturn(item);
//		when(spotDao.selectSpotLikeByContentIdList(anyInt(), any())).thenReturn(spotLikeList);
//
//		SpotListDto<SpotDto> resultList = spotService.getLocationBasedList(accountId, areaCode, contentTypeId, index);
//
//		assertThat(resultList).isNotNull();
//		assertThat(resultList.getItems()).isNotNull()
//		.extracting(SpotDto::getBasedSpot, SpotDto::getLikeState)
//		.containsExactly(tuple(item.getItems().get(0), true), tuple(item.getItems().get(1), false));
//		
//	}
	
	@Test
	public void 여행지_키워드별리스트_가져오기() throws Exception {
		int accountId = 1;
		OpenApiDto param = OpenApiDto.builder()
				.areaCode(1)
				.contentTypeId(1)
				.keyword("test")
				.pageNo(1)
				.build();
		
		CommonListDto<CommonBasedDto> item = createBasedDtoList();
		
		List<SpotLikeDto> spotLikeList = new ArrayList<SpotLikeDto>();
		spotLikeList.add(SpotLikeDto.builder()
				.likeId(1)
				.accountId(1)
				.contentId(2733967)
				.likeDate(LocalDate.now())
				.build());
		spotLikeList.add(SpotLikeDto.builder()
				.likeId(2)
				.accountId(1)
				.contentId(2763807)
				.likeDate(LocalDate.now())
				.build());
		
		List<SpotLikeCountDto> spotLikeCountList = new ArrayList<SpotLikeCountDto>();
		spotLikeCountList.add(SpotLikeCountDto.builder()
				.contentId(2733967)
				.likeCount(1)
				.build());
		spotLikeCountList.add(SpotLikeCountDto.builder()
				.contentId(2763807)
				.likeCount(1)
				.build());

		//
		when(apiSerivce.getKeyword(any(OpenApiDto.class))).thenReturn(item);
		when(spotDao.findSpotLikeStateByContentIdList(anyInt(), any())).thenReturn(spotLikeList);
		when(spotDao.findSpotLikeCountByContentIdList(any())).thenReturn(spotLikeCountList);
		
		SpotListDto<SpotDto> resultList = spotService.getKeyword(accountId, param);

		assertThat(resultList).isNotNull();
		assertThat(resultList.getItems()).isNotNull()
		.extracting("basedSpot", "likeCount", "likeState")
		.containsExactly(tuple(item.getItems().get(0), 1, true), tuple(item.getItems().get(1), 1, true));
		
	}

	@Test
	public void 여행지_세부사항_가져오기() throws Exception {
		int accountId = 1;
		int contentId = 2733967;
		CommonDetailDto testDetail = createDetailDtoList();
		int spotLikeCount = 1;
		boolean spotLikeState = true;

		when(apiSerivce.getDetail(anyInt())).thenReturn(testDetail);
		when(spotDao.findSpotLikeCountByContentId(anyInt())).thenReturn(spotLikeCount);
		when(spotDao.findSpotLikeStateByContentId(anyInt(), anyInt())).thenReturn(spotLikeState);

		SpotDetailDto resultDto = spotService.getDetail(accountId, contentId);
		
		assertThat(resultDto).isNotNull()
				.hasFieldOrPropertyWithValue("likeCount", 1)
				.hasFieldOrPropertyWithValue("likeState", true);
		
		assertThat(resultDto.getDetail()).usingRecursiveComparison()
				.isEqualTo(testDetail);
	}

	@Test
	public void 여행지_좋아요() throws Exception {
		int accountId = 1;
		
		SpotLikeDto spotLikeDto = SpotLikeDto.builder()
				.contentId(2733967)
				.title("테스트")
				.image("테스트이미지")
				.build();
		
		when(spotDao.createSpotLike(anyInt(), any())).thenReturn(true);

		boolean like = spotService.addSpotLike(accountId, spotLikeDto);
		
		assertThat(like).isEqualTo(true);
	}

	@Test
	public void 여행지_좋아요_취소() throws Exception {
		int accountId = 1;
		int contentId = 2733967;

		when(spotDao.deleteSpotLike(anyInt(), anyInt())).thenReturn(true);

		boolean like = spotService.removeSpotLike(accountId, contentId);
		
		assertThat(like).isEqualTo(true);
	}
	
	@Test
	public void 여행지_좋아요_리스트_가져오기() throws Exception {
		CommonRequestParamDto paramDto = CommonRequestParamDto.builder()
				.itemCount(10)
				.sortCriteria(SortCriteria.LATEST)
				.pageNum(1)
				.build();
		
		List<SpotLikeDto> list = new ArrayList<SpotLikeDto>();
		list.add(SpotLikeDto.builder()
				.likeId(1)
				.accountId(1)
				.contentId(2733967)
				.title("테스트")
				.image("테스트이미지")
				.build());
		list.add(SpotLikeDto.builder()
				.likeId(2)
				.accountId(1)
				.contentId(2733967)
				.title("테스트")
				.image("테스트이미지")
				.build());

		//
		when(spotDao.findSpotLikeList(anyInt(), any(CommonRequestParamDto.class), any(PageInfo.class))).thenReturn(list);
		when(spotDao.findListTotalCount(anyInt(), any(CommonRequestParamDto.class))).thenReturn(2);
		
		Page<SpotLikeDto> spotList = spotService.getSpotLikeList(1, paramDto);
		
		assertThat(spotList).isNotNull();
		assertThat(spotList.getList()).isNotNull();
		assertThat(spotList.getTotalCount()).isEqualTo(2);
	}
	
	@Test
	public void 여행지_좋아요_리스트_가져오기_키워드() throws Exception {
		CommonRequestParamDto paramDto = CommonRequestParamDto.builder()
				.itemCount(10)
				.sortCriteria(SortCriteria.LATEST)
				.keyword("테스트")
				.pageNum(1)
				.build();
		
		List<SpotLikeDto> list = new ArrayList<SpotLikeDto>();
		list.add(SpotLikeDto.builder()
				.likeId(1)
				.accountId(1)
				.contentId(2733967)
				.title("테스트")
				.image("테스트이미지")
				.build());

		//
		when(spotDao.findSpotLikeList(anyInt(), any(CommonRequestParamDto.class), any(PageInfo.class))).thenReturn(list);
		when(spotDao.findListTotalCount(anyInt(), any(CommonRequestParamDto.class))).thenReturn(1);
		
		Page<SpotLikeDto> spotList = spotService.getSpotLikeList(1, paramDto);
		
		assertThat(spotList).isNotNull();
		assertThat(spotList.getList()).isNotNull();
		assertThat(spotList.getTotalCount()).isEqualTo(1);
	}

	private CommonListDto<CommonBasedDto> createBasedDtoList() {
		CommonBasedDto dto = CommonBasedDto.builder()
				.addr1("서울특별시 종로구 북촌로 57")
				.addr2("(가회동)")
				.areaCode("1")
				.bookTour("")
				.cat1("A02")
				.cat2("A0201")
				.cat3("A02010900")
				.contentId("2733967")
				.contentTypeId("12")
				.createdTime("20210817184103")
				.firstImage("")
				.firstImage2("")
				.mapx("126.9846616856")
				.mapy("37.5820858828")
				.mlevel("6")
				.modifiedTime("20221024141458")
				.sigunguCode("23")
				.tel("")
				.title("가회동성당")
				.zipcode("03052")
				.build();
		CommonBasedDto dto2 = CommonBasedDto.builder()
				.addr1("서울특별시 동대문구 서울시립대로2길 59")
				.addr2("(답십리동)")
				.areaCode("1")
				.bookTour("")
				.cat1("A02")
				.cat2("A0202")
				.cat3("A02020700")
				.contentId("2763807")
				.contentTypeId("14")
				.createdTime("20211027233001")
				.firstImage("")
				.firstImage2("")
				.mapx("127.0490977427")
				.mapy("37.5728520032")
				.mlevel("6")
				.modifiedTime("20211111194249")
				.sigunguCode("11")
				.tel("")
				.title("간데메공원")
				.zipcode("02595")
				.build();
		List<CommonBasedDto> basedList = new ArrayList<CommonBasedDto>();
		basedList.add(dto);
		basedList.add(dto2);

		CommonListDto<CommonBasedDto> list = new CommonListDto<CommonBasedDto>(basedList, 10, 2, 2);
		return list;
	}

	private CommonDetailDto createDetailDtoList() {
		CommonDetailDto dto = CommonDetailDto.builder()
				.homepage("")
				.telname("")
				.zipcode("03052")
				.overview("가회동성당이 위치한 북촌 일대는...")
				.build();
		return dto;
	}
}
