package com.planner.planner.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

public class SpotServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(SpotServiceTest.class);

	@Mock
	private SpotDaoImpl spotDao;
	@Mock
	private OpenAPIServiceImpl apiSerivce;

	@InjectMocks
	private SpotServiceImpl spotService;

	@Before
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void 여행지_지역코드_가져오기() throws Exception {
		List<AreaCodeDto> item = new ArrayList<AreaCodeDto>();
		item.add(new AreaCodeDto.Builder().setRnum("1").setCode("1").setName("서울").build());
		item.add(new AreaCodeDto.Builder().setRnum("2").setCode("2").setName("인천").build());
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
		
		OpenApiDto param = new OpenApiDto.Builder()
				.setAreaCode(1)
				.setContentTypeId(12)
				.setPageNo(1)
				.build();
		
		CommonListDto<CommonBasedDto> item = createBasedDtoList();
		
		List<SpotLikeDto> spotLikeList = new ArrayList<SpotLikeDto>();
		spotLikeList.add(new SpotLikeDto.Builder()
				.setLikeId(1)
				.setAccountId(1)
				.setContentId(2733967)
				.setLikeDate(LocalDate.now())
				.build());
		spotLikeList.add(new SpotLikeDto.Builder()
				.setLikeId(2)
				.setAccountId(1)
				.setContentId(2763807)
				.setLikeDate(LocalDate.now())
				.build());
		
		List<SpotLikeCountDto> spotLikeCountList = new ArrayList<SpotLikeCountDto>();
		spotLikeCountList.add(new SpotLikeCountDto.Builder()
				.setContentId(2733967)
				.setCount(1)
				.build());
		spotLikeCountList.add(new SpotLikeCountDto.Builder()
				.setContentId(2763807)
				.setCount(1)
				.build());

		//
		when(apiSerivce.getAreaList(any(OpenApiDto.class))).thenReturn(item);
		when(spotDao.selectSpotLikeByContentIdList(anyInt(), any())).thenReturn(spotLikeList);
		when(spotDao.selectSpotLikeCountByContentIdList(any())).thenReturn(spotLikeCountList);

		SpotListDto<SpotDto> resultList = spotService.getAreaList(accountId, param);

		assertThat(resultList).isNotNull();
		assertThat(resultList.getItems()).isNotNull()
				.extracting(SpotDto::getBasedSpot, SpotDto::getLikeCount, SpotDto::getLikeState)
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
//		spotLikeList.add(new SpotLikeDto.Builder()
//				.setLikeId(1)
//				.setAccountId(1)
//				.setContentId(2733967)
//				.setLikeDate(LocalDate.now())
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
		OpenApiDto param = new OpenApiDto.Builder()
				.setAreaCode(1)
				.setContentTypeId(1)
				.setKeyword("test")
				.setPageNo(1)
				.build();
		
		CommonListDto<CommonBasedDto> item = createBasedDtoList();
		
		List<SpotLikeDto> spotLikeList = new ArrayList<SpotLikeDto>();
		spotLikeList.add(new SpotLikeDto.Builder()
				.setLikeId(1)
				.setAccountId(1)
				.setContentId(2733967)
				.setLikeDate(LocalDate.now())
				.build());
		spotLikeList.add(new SpotLikeDto.Builder()
				.setLikeId(2)
				.setAccountId(1)
				.setContentId(2763807)
				.setLikeDate(LocalDate.now())
				.build());
		
		List<SpotLikeCountDto> spotLikeCountList = new ArrayList<SpotLikeCountDto>();
		spotLikeCountList.add(new SpotLikeCountDto.Builder()
				.setContentId(2733967)
				.setCount(1)
				.build());
		spotLikeCountList.add(new SpotLikeCountDto.Builder()
				.setContentId(2763807)
				.setCount(1)
				.build());

		//
		when(apiSerivce.getKeyword(any(OpenApiDto.class))).thenReturn(item);
		when(spotDao.selectSpotLikeByContentIdList(anyInt(), any())).thenReturn(spotLikeList);
		when(spotDao.selectSpotLikeCountByContentIdList(any())).thenReturn(spotLikeCountList);
		
		SpotListDto<SpotDto> resultList = spotService.getKeyword(accountId, param);

		assertThat(resultList).isNotNull();
		assertThat(resultList.getItems()).isNotNull()
		.extracting(SpotDto::getBasedSpot, SpotDto::getLikeCount, SpotDto::getLikeState)
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
		when(spotDao.selectSpotLikeCountByContentId(anyInt())).thenReturn(spotLikeCount);
		when(spotDao.selectSpotLikeByContentId(anyInt(), anyInt())).thenReturn(spotLikeState);

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
		
		SpotLikeDto spotLikeDto = new SpotLikeDto.Builder()
				.setContentId(2733967)
				.setTitle("테스트")
				.setImage("테스트이미지")
				.build();
		
		when(spotDao.insertSpotLike(anyInt(), any())).thenReturn(true);

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
		CommonRequestParamDto paramDto = new CommonRequestParamDto.Builder()
				.setItemCount(10)
				.setSortCriteria(SortCriteria.LATEST)
				.setPageNum(1)
				.build();
		
		List<SpotLikeDto> list = new ArrayList<SpotLikeDto>();
		list.add(new SpotLikeDto.Builder()
				.setLikeId(1)
				.setAccountId(1)
				.setContentId(2733967)
				.setTitle("테스트")
				.setImage("테스트이미지")
				.build());
		list.add(new SpotLikeDto.Builder()
				.setLikeId(2)
				.setAccountId(1)
				.setContentId(2733967)
				.setTitle("테스트")
				.setImage("테스트이미지")
				.build());

		//
		when(spotDao.selectSpotLikeList(anyInt(), any(SortCriteria.class), anyString(), any(PageInfo.class))).thenReturn(list);
		when(spotDao.getTotalCountByAccountId(anyInt())).thenReturn(2);
		
		Page<SpotLikeDto> spotList = spotService.getSpotLikeList(1, paramDto);
		
		assertThat(spotList).isNotNull();
		assertThat(spotList.getList()).isNotNull();
		assertThat(spotList.getTotalCount()).isEqualTo(2);
	}
	
	@Test
	public void 여행지_좋아요_리스트_가져오기_키워드() throws Exception {
		CommonRequestParamDto paramDto = new CommonRequestParamDto.Builder()
				.setItemCount(10)
				.setSortCriteria(SortCriteria.LATEST)
				.setKeyword("테스트")
				.setPageNum(1)
				.build();
		
		List<SpotLikeDto> list = new ArrayList<SpotLikeDto>();
		list.add(new SpotLikeDto.Builder()
				.setLikeId(1)
				.setAccountId(1)
				.setContentId(2733967)
				.setTitle("테스트")
				.setImage("테스트이미지")
				.build());

		//
		when(spotDao.selectSpotLikeList(anyInt(), any(SortCriteria.class), anyString(), any(PageInfo.class))).thenReturn(list);
		when(spotDao.getTotalCountByAccountId(anyInt(), anyString())).thenReturn(1);
		
		Page<SpotLikeDto> spotList = spotService.getSpotLikeList(1, paramDto);
		
		assertThat(spotList).isNotNull();
		assertThat(spotList.getList()).isNotNull();
		assertThat(spotList.getTotalCount()).isEqualTo(1);
	}

	private CommonListDto<CommonBasedDto> createBasedDtoList() {
		CommonBasedDto dto = new CommonBasedDto.Builder()
				.setAddr1("서울특별시 종로구 북촌로 57")
				.setAddr2("(가회동)")
				.setAreaCode("1")
				.setBookTour("")
				.setCat1("A02")
				.setCat2("A0201")
				.setCat3("A02010900")
				.setContentId("2733967")
				.setContentTypeId("12")
				.setCreatedTime("20210817184103")
				.setFirstImage("")
				.setFirstImage2("")
				.setMapx("126.9846616856")
				.setMapy("37.5820858828")
				.setMlevel("6")
				.setModifiedTime("20221024141458")
				.setSigunguCode("23")
				.setTel("")
				.setTitle("가회동성당")
				.setZipcode("03052")
				.build();
		CommonBasedDto dto2 = new CommonBasedDto.Builder()
				.setAddr1("서울특별시 동대문구 서울시립대로2길 59")
				.setAddr2("(답십리동)")
				.setAreaCode("1")
				.setBookTour("")
				.setCat1("A02")
				.setCat2("A0202")
				.setCat3("A02020700")
				.setContentId("2763807")
				.setContentTypeId("14")
				.setCreatedTime("20211027233001")
				.setFirstImage("")
				.setFirstImage2("")
				.setMapx("127.0490977427")
				.setMapy("37.5728520032")
				.setMlevel("6")
				.setModifiedTime("20211111194249")
				.setSigunguCode("11")
				.setTel("")
				.setTitle("간데메공원")
				.setZipcode("02595")
				.build();
		List<CommonBasedDto> basedList = new ArrayList<CommonBasedDto>();
		basedList.add(dto);
		basedList.add(dto2);

		CommonListDto<CommonBasedDto> list = new CommonListDto<CommonBasedDto>(basedList, 10, 2, 2);
		return list;
	}

	private CommonDetailDto createDetailDtoList() {
		CommonDetailDto dto = new CommonDetailDto.Builder()
				.setHomepage("")
				.setTelname("")
				.setZipcode("03052")
				.setOverview("가회동성당이 위치한 북촌 일대는...")
				.build();
		return dto;
	}
}
