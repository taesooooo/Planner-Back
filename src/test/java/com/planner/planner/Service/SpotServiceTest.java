package com.planner.planner.Service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.planner.planner.Config.RootAppContext;
import com.planner.planner.Dao.SpotDaoImpl;
import com.planner.planner.Dto.SpotDto;
import com.planner.planner.Dto.SpotLikeCountDto;
import com.planner.planner.Dto.OpenApi.AreaCodeDto;
import com.planner.planner.Dto.OpenApi.BasedDto;
import com.planner.planner.Dto.OpenApi.DetailCommonDto;
import com.planner.planner.Entity.SpotLikeCount;
import com.planner.planner.Exception.OpenAPIDataEmpty;

public class SpotServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(SpotServiceTest.class);

	@Mock
	private SpotDaoImpl spotDao;
	@Mock
	private OpenAPIServiceImpl apiSerivce;

	@InjectMocks
	private SpotServiceImpl spotService;
	
	private int areaCode = 1;
	private int contentTypeId = 12;
	private String keyword = "서울";
	private int index = 1;
	private int accountId = 1;
	private int contentId = 2733967;

	@Before
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void 여행지_지역코드_가져오기() throws Exception {
		List<AreaCodeDto> item = new ArrayList<AreaCodeDto>();
		item.add(new AreaCodeDto.Builder().setRnum("1").setCode("1").setName("").build());
		when(apiSerivce.getAreaNum()).thenReturn(item);
		
		List<AreaCodeDto> data = spotService.getAreaNum();
		
		assertEquals(item.get(0).getRnum(), data.get(0).getRnum());
	}
	
	@Test
	public void 여행지_지역기반리스트_가져오기() throws Exception {
		List<BasedDto> item = createBasedDtoList();
		
		List<SpotLikeCount> testLikeCounts = new ArrayList<SpotLikeCount>(); // 좋아요 없음
		
		List<SpotDto> testSpotList = createSpotDtoList(0);
		
		String contentIds = item.stream().map((i) -> i.getContentid()).collect(Collectors.joining(","));
		
		//
		when(apiSerivce.getAreaList(areaCode, contentTypeId, index)).thenReturn(item);
		when(spotDao.spotLikeCount(contentIds)).thenReturn(testLikeCounts);
		
		List<SpotDto> resultList = spotService.getAreaList(areaCode, contentTypeId, index);
		
		assertTrue(resultList.size() > 0);
		assertEquals(testSpotList.get(0).getContentid(), resultList.get(0).getContentid());
	}
	
	@Test(expected = OpenAPIDataEmpty.class)
	public void 여행지_지역기반리스트_가져오기_openApi_데이터가_없는경우() throws Exception {
		when(apiSerivce.getAreaList(areaCode, contentTypeId, index)).thenThrow(new OpenAPIDataEmpty());

		List<SpotDto> resultList = spotService.getAreaList(areaCode, contentTypeId, index);
	}
	
	@Test
	public void 여행지_지역기반리스트_가져오기_좋아요_있는경우() throws Exception {
		List<BasedDto> item = createBasedDtoList();
		
		List<SpotLikeCount> testLikeCounts = new ArrayList<SpotLikeCount>();
		SpotLikeCount testLikeCount = new SpotLikeCount(Integer.parseInt(item.get(0).getContentid()), 1);
		SpotLikeCount testLikeCount2 = new SpotLikeCount(Integer.parseInt(item.get(1).getContentid()), 1);
		testLikeCounts.add(testLikeCount);
		testLikeCounts.add(testLikeCount2);
		
		List<SpotDto> testSpotList = createSpotDtoList(1);
		
		String contentIds = item.stream().map((i) -> i.getContentid()).collect(Collectors.joining(","));
		
		//
		when(apiSerivce.getAreaList(areaCode, contentTypeId, index)).thenReturn(item);
		when(spotDao.spotLikeCount(contentIds)).thenReturn(testLikeCounts);
		
		List<SpotDto> resultList = spotService.getAreaList(areaCode, contentTypeId, index);
		
		assertTrue(resultList.size() > 0);
		assertEquals(testSpotList.get(0).getContentid(), resultList.get(0).getContentid());
		assertEquals(testSpotList.get(0).getLikeCount(), resultList.get(0).getLikeCount());
	}
	
	@Test
	public void 여행지_키워드별_가져오기() throws Exception {
		List<BasedDto> item = createBasedDtoList();
		
		List<SpotLikeCount> testLikeCounts = new ArrayList<SpotLikeCount>(); // 좋아요 없음
		
		List<SpotDto> testSpotList = createSpotDtoList(0);
		
		String contentIds = item.stream().map((i) -> i.getContentid()).collect(Collectors.joining(","));
		
		//
		when(apiSerivce.getKeyword(areaCode, contentTypeId, keyword, index)).thenReturn(item);
		when(spotDao.spotLikeCount(contentIds)).thenReturn(testLikeCounts);
		
		List<SpotDto> resultList = spotService.getKeyword(areaCode, contentTypeId, keyword, index);
		
		assertTrue(resultList.size() > 0);
		assertEquals(testSpotList.get(0).getContentid(), resultList.get(0).getContentid());
		assertEquals(testSpotList.get(0).getLikeCount(), resultList.get(0).getLikeCount());
	}
	
	@Test
	public void 여행지_키워드별_가져오기_좋아요_있는경우() throws Exception {
		List<BasedDto> item = createBasedDtoList();
		
		List<SpotLikeCount> testLikeCounts = new ArrayList<SpotLikeCount>();
		SpotLikeCount testLikeCount = new SpotLikeCount(Integer.parseInt(item.get(0).getContentid()), 1);
		SpotLikeCount testLikeCount2 = new SpotLikeCount(Integer.parseInt(item.get(1).getContentid()), 1);
		testLikeCounts.add(testLikeCount);
		testLikeCounts.add(testLikeCount2);
		
		List<SpotDto> testSpotList = createSpotDtoList(1);
		
		String contentIds = item.stream().map((i) -> i.getContentid()).collect(Collectors.joining(","));
		
		//
		when(apiSerivce.getKeyword(areaCode, contentTypeId, keyword, index)).thenReturn(item);
		when(spotDao.spotLikeCount(contentIds)).thenReturn(testLikeCounts);
		
		List<SpotDto> resultList = spotService.getKeyword(areaCode, contentTypeId, keyword, index);
		
		assertTrue(resultList.size() > 0);
		assertEquals(testSpotList.get(0).getContentid(), resultList.get(0).getContentid());
		assertEquals(testSpotList.get(0).getLikeCount(), resultList.get(0).getLikeCount());
	}
	
	@Test(expected = OpenAPIDataEmpty.class)
	public void 여행지_키워드별_가져오기_openApi_데이터가_없는경우() throws Exception {
		when(apiSerivce.getKeyword(areaCode, contentTypeId, keyword, index)).thenThrow(new OpenAPIDataEmpty());

		List<SpotDto> resultList = spotService.getKeyword(areaCode, contentTypeId, keyword, index);
	}
	
	@Test
	public void 여행지_세부사항_가져오기() throws Exception {
		DetailCommonDto testDetailDto = createDetailDtoList();
		when(apiSerivce.getDetail(contentId)).thenReturn(testDetailDto);
		
		DetailCommonDto resultDto =spotService.getDetail(contentId);
		
		assertEquals(testDetailDto.getZipcode(), resultDto.getZipcode());
		assertEquals(testDetailDto.getOverview(), resultDto.getOverview());
	}

	@Test
	public void 여행지_좋아요() {
		when(spotDao.spotLikeAdd(accountId, contentId)).thenReturn(true);
		
		assertTrue(spotService.spotLike(accountId, contentId));
	}

	@Test
	public void 여행지_좋아요_취소() {
		int accountId = 1;
		int contentId = 2733967;
		
		when(spotDao.spotLikeDelete(accountId, contentId)).thenReturn(true);
		
		assertTrue(spotService.spotLikeCancel(accountId, contentId));
	}
	
	private List<BasedDto> createBasedDtoList() {
		BasedDto dto = new BasedDto.Builder()
				.setAddr1("서울특별시 종로구 북촌로 57")
				.setAddr2("(가회동)")
				.setAreaCode("1")
				.setBookTour("")
				.setCat1("A02")
				.setCat2("A0201")
				.setCat3("A02010900")
				.setContentid("2733967")
				.setContenttypeid("12")
				.setCreatedtime("20210817184103")
				.setFirstimage("")
				.setFirstimage2("")
				.setMapx("126.9846616856")
				.setMapy("37.5820858828")
				.setMlevel("6")
				.setModifiedtime("20221024141458")
				.setReadCount("")
				.setSigunguCode("23")
				.setTel("")
				.setTitle("가회동성당")
				.setZipcode("03052")
				.build();
		BasedDto dto2 = new BasedDto.Builder()
				.setAddr1("서울특별시 동대문구 서울시립대로2길 59")
				.setAddr2("(답십리동)")
				.setAreaCode("1")
				.setBookTour("")
				.setCat1("A02")
				.setCat2("A0202")
				.setCat3("A02020700")
				.setContentid("2763807")
				.setContenttypeid("14")
				.setCreatedtime("20211027233001")
				.setFirstimage("")
				.setFirstimage2("")
				.setMapx("127.0490977427")
				.setMapy("37.5728520032")
				.setMlevel("6")
				.setModifiedtime("20211111194249")
				.setReadCount("")
				.setSigunguCode("11")
				.setTel("")
				.setTitle("간데메공원")
				.setZipcode("02595")
				.build();
		List<BasedDto> list =new ArrayList<BasedDto>();
		list.add(dto);
		list.add(dto2);
		return list;
	}
	
	private DetailCommonDto createDetailDtoList() {
		DetailCommonDto dto = new DetailCommonDto.Builder()
				.setZipcode("03052")
				.setOverview("가회동성당이 위치한 북촌 일대는...")
				.build();
		return dto;
	}
	
	private List<SpotDto> createSpotDtoList(int likeCount) {
		SpotDto dto = new SpotDto.Builder()
				.setAddr1("서울특별시 종로구 북촌로 57")
				.setAddr2("(가회동)")
				.setAreaCode("1")
				.setBookTour("")
				.setCat1("A02")
				.setCat2("A0201")
				.setCat3("A02010900")
				.setContentid("2733967")
				.setContenttypeid("12")
				.setCreatedtime("20210817184103")
				.setFirstimage("")
				.setFirstimage2("")
				.setMapx("126.9846616856")
				.setMapy("37.5820858828")
				.setMlevel("6")
				.setModifiedtime("20221024141458")
				.setReadCount("")
				.setSigunguCode("23")
				.setTel("")
				.setTitle("가회동성당")
				.setZipcode("03052")
				.setLikeCount(likeCount)
				.build();
		SpotDto dto2 = new SpotDto.Builder()
				.setAddr1("서울특별시 동대문구 서울시립대로2길 59")
				.setAddr2("(답십리동)")
				.setAreaCode("1")
				.setBookTour("")
				.setCat1("A02")
				.setCat2("A0202")
				.setCat3("A02020700")
				.setContentid("2763807")
				.setContenttypeid("14")
				.setCreatedtime("20211027233001")
				.setFirstimage("")
				.setFirstimage2("")
				.setMapx("127.0490977427")
				.setMapy("37.5728520032")
				.setMlevel("6")
				.setModifiedtime("20211111194249")
				.setReadCount("")
				.setSigunguCode("11")
				.setTel("")
				.setTitle("간데메공원")
				.setZipcode("02595")
				.setLikeCount(likeCount)
				.build();
		List<SpotDto> list =new ArrayList<SpotDto>();
		list.add(dto);
		list.add(dto2);
		return list;
	}
	
}
