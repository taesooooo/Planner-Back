package com.planner.planner.Mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.ReflectionUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.planner.planner.Common.Coordinate;
import com.planner.planner.Common.PageInfo;
import com.planner.planner.Common.SortCriteria;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.CommonRequestParamDto;
import com.planner.planner.Dto.PlanDto;
import com.planner.planner.Dto.PlanLocationDto;
import com.planner.planner.Dto.PlanLocationRouteDto;
import com.planner.planner.Dto.PlanMemoDto;
import com.planner.planner.Dto.PlannerDto;

@ActiveProfiles("test")
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PlannerMapperTest {
	
	@Autowired
	private PlannerMapper mapper;
	
	@DisplayName("플래너 생성")
	@Test
	public void createPlannerTest() {
		// 필요한 유저 데이터만 입력
		AccountDto user = AccountDto.builder()
				.accountId(1)
				.email("test@naver.com")
				.nickname("test")
				.build();
		
		PlannerDto newPlanner = PlannerDto.builder()
				.accountId(1)
				.areaCode(1)
				.title("플래너 생성 테스트")
				.creator("test")
				.planDateStart(LocalDate.parse("2024-07-07"))
				.planDateEnd(LocalDate.parse("2024-07-09"))
				.expense(100000)
				.memberCount(2)
				.memberTypeId(1)
				.build();
		
		int newPlannerId = mapper.createPlanner(user, newPlanner);
		
		assertThat(newPlannerId).isEqualTo(newPlannerId);
	}
	
	@DisplayName("아이디로 플래너 가져오기")
	@Test
	public void findById() throws Exception {
		List<PlanMemoDto> planMemo = Arrays.asList(
				PlanMemoDto.builder()
				.memoId(1)
				.title("메모")
				.content("메모내용")
				.createDate(null)
				.updateDate(null)
				.build(),
				PlanMemoDto.builder()
				.memoId(2)
				.title("메모2")
				.content("메모내용2")
				.createDate(null)
				.updateDate(null)
				.build());
		
		List<PlanDto> plan = Arrays.asList(
				PlanDto.builder()
				.planId(1)
				.plannerId(1)
				.planDate(LocalDate.parse("2023-04-11"))
				.planLocations(Arrays.asList(PlanLocationDto.builder()
						.locationId(1)
						.locationName("바다")
						.locationContentId(2000)
						.locationImage("http://tong.visitkorea.or.kr/cms/resource/85/2031885_image2_1.jpg")
						.locationAddr("바다주소")
						.locationMapx(123.1234567891)
						.locationMapy(11.1234567891)
						.locationTransportation(1)
						.locationIndex(1024)
						.planId(1)
						.build()))
				.planIndex(1024)
				.planLocationRoutes(Arrays.asList(PlanLocationRouteDto.builder()
						.id(1)
						.planId(1)
						.startIndex(0)
						.endIndex(1)
						.routeList(Arrays.asList(new Coordinate(0.00, 0.00),new Coordinate(1.11, 1.11), new Coordinate(2.22, 2.22)))
						.routeWKT("LINESTRING(0 0,1.11 1.11,2.22 2.22)")
						.build(),
						PlanLocationRouteDto.builder()
						.id(2)
						.planId(1)
						.startIndex(1)
						.endIndex(2)
						.routeList(Arrays.asList(new Coordinate(2.22, 2.22),new Coordinate(3.33, 3.33), new Coordinate(4.44, 4.44)))
						.routeWKT("LINESTRING(2.22 2.22,3.33 3.33,4.44 4.44)")
						.build()))
				.build(),
				PlanDto.builder()
				.planId(3)
				.plannerId(1)
				.planDate(LocalDate.parse("2023-04-11"))
				.planLocations(Arrays.asList(PlanLocationDto.builder()
						.locationId(3)
						.locationName("바다")
						.locationContentId(2000)
						.locationImage("http://tong.visitkorea.or.kr/cms/resource/85/2031885_image2_1.jpg")
						.locationAddr("바다주소")
						.locationMapx(123.1234567891)
						.locationMapy(11.1234567891)
						.locationTransportation(1)
						.locationIndex(2048)
						.planId(3)
						.build()))
				.planLocationRoutes(Arrays.asList())
				.planIndex(2048)
				.build(),
				PlanDto.builder()
				.planId(2)
				.plannerId(1)
				.planDate(LocalDate.parse("2023-04-11"))
				.planLocations(Arrays.asList(PlanLocationDto.builder()
						.locationId(2)
						.locationName("바다")
						.locationContentId(2000)
						.locationImage("http://tong.visitkorea.or.kr/cms/resource/85/2031885_image2_1.jpg")
						.locationAddr("바다주소")
						.locationMapx(123.1234567891)
						.locationMapy(11.1234567891)
						.locationTransportation(1)
						.locationIndex(3072)
						.planId(2)
						.build()))
				.planIndex(3072)
				.planLocationRoutes(Arrays.asList(PlanLocationRouteDto.builder()
						.id(3)
						.planId(2)
						.startIndex(0)
						.endIndex(1)
						.routeList(Arrays.asList(new Coordinate(33.11, 129.11),new Coordinate(33.22, 129.22), new Coordinate(33.33, 129.33)))
						.routeWKT("LINESTRING(129.11 33.11,129.22 33.22,129.33 33.33)")
						.build(),
						PlanLocationRouteDto.builder()
						.id(4)
						.planId(2)
						.startIndex(1)
						.endIndex(2)
						.routeList(Arrays.asList(new Coordinate(33.44, 129.44),new Coordinate(33.55, 129.55), new Coordinate(33.66, 129.66)))
						.routeWKT("LINESTRING(129.44 33.44,129.55 33.55,129.66 33.66)")
						.build()))
				.build()
				);
		
		PlannerDto planner = PlannerDto.builder()
				.plannerId(1)
				.accountId(1)
				.areaCode(null)
				.creator("test")
				.title("이렇게 좋은 여행이 있었나아아아")
				.planDateStart(LocalDate.parse("2022-08-10"))
				.planDateEnd(LocalDate.parse("2022-08-12"))
				.expense(0)
				.planMembers(Arrays.asList("test", "test2"))
				.memberCount(1)
				.memberTypeId(1)
				.likeCount(3)
				.likeState(true)
				.thumbnail("http://tong.visitkorea.or.kr/cms/resource/85/2031885_image2_1.jpg")
				.createDate(LocalDateTime.now())
				.updateDate(LocalDateTime.now())
				.planMemos(planMemo)
				.plans(plan)
				.build();
		
		PlannerDto findPlanner = mapper.findByPlannerId(null, 1);
		
		assertThat(findPlanner).usingRecursiveComparison()
				.ignoringFields("createDate", "updateDate", "planMemos.createDate", "planMemos.updateDate")
				.isEqualTo(planner);
	}
	
	@DisplayName("사용자 아이디로 플래너 리스트 가져오기")
	@Test
	public void findListByAccountId() throws Exception {
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
		
		List<PlannerDto> list = Arrays.asList(
				PlannerDto.builder()
				.plannerId(7)
				.accountId(1)
				.creator("test")
				.areaCode(1)
				.title("제목")
				.planDateStart(LocalDate.parse("2022-08-10"))
				.planDateEnd(LocalDate.parse("2022-08-12"))
				.expense(3)
				.memberCount(1)
				.memberTypeId(1)
				.likeCount(0)
				.likeState(false)
				.createDate(LocalDateTime.parse("2024-06-18 16:14:51", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.updateDate(LocalDateTime.parse("2024-06-18 16:14:51", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.thumbnail(null)
				.build(),
				PlannerDto.builder()
				.plannerId(4)
				.accountId(1)
				.creator("test")
				.areaCode(1)
				.title("테스트 플래너")
				.planDateStart(LocalDate.parse("2022-08-10"))
				.planDateEnd(LocalDate.parse("2022-08-12"))
				.expense(0)
				.memberCount(1)
				.memberTypeId(1)
				.likeCount(1)
				.likeState(true)
				.createDate(LocalDateTime.parse("2024-06-18 16:14:51", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.updateDate(LocalDateTime.parse("2024-06-18 16:14:51", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.thumbnail(null)
				.build(),
				PlannerDto.builder()
				.plannerId(1)
				.accountId(1)
				.creator("test")
				.areaCode(null)
				.title("이렇게 좋은 여행이 있었나아아아")
				.planDateStart(LocalDate.parse("2022-08-10"))
				.planDateEnd(LocalDate.parse("2022-08-12"))
				.expense(0)
				.memberCount(1)
				.memberTypeId(1)
				.likeCount(3)
				.likeState(true)
				.createDate(LocalDateTime.parse("2024-06-18 16:14:51", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.updateDate(LocalDateTime.parse("2024-06-18 16:14:51", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.thumbnail("http://tong.visitkorea.or.kr/cms/resource/85/2031885_image2_1.jpg")
				.build());
		
		List<PlannerDto> findList = mapper.findListByAccountId(1, commonRequestParamDto, pInfo);
		
		assertThat(findList).usingRecursiveComparison()
				.ignoringFields("createDate", "updateDate")
				.isEqualTo(list);
	}
	
	@DisplayName("플래너 리스트 가져오기")
	@Test
	public void findAll() throws Exception {
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
		
		List<PlannerDto> findList = mapper.findAll(null, commonRequestParamDto, pInfo);
		
		assertThat(findList.size()).isEqualTo(9);
	}
	
	@DisplayName("좋아요 플래너 리스트 가져오기")
	@Test
	public void findLikeList() throws Exception {
		List<PlannerDto> testList = Arrays.asList(
				PlannerDto.builder()
				.plannerId(5)
				.accountId(2)
				.creator("test")
				.areaCode(null)
				.title("테스트 플래너")
				.planDateStart(LocalDate.parse("2022-08-10"))
				.planDateEnd(LocalDate.parse("2022-08-12"))
				.expense(0)
				.memberCount(1)
				.memberTypeId(1)
				.likeCount(1)
				.createDate(LocalDateTime.now())
				.updateDate(LocalDateTime.now())
				.likeState(true)
				.thumbnail(null)
				.build(),
				PlannerDto.builder()
				.plannerId(4)
				.accountId(1)
				.creator("test")
				.areaCode(1)
				.title("테스트 플래너")
				.planDateStart(LocalDate.parse("2022-08-10"))
				.planDateEnd(LocalDate.parse("2022-08-12"))
				.expense(0)
				.memberCount(1)
				.memberTypeId(1)
				.likeCount(1)
				.createDate(LocalDateTime.parse("2024-06-18 16:14:51", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.updateDate(LocalDateTime.parse("2024-06-18 16:14:51", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.likeState(true)
				.thumbnail(null)
				.build(),
				PlannerDto.builder()
				.plannerId(3)
				.accountId(3)
				.creator("test")
				.areaCode(null)
				.title("이렇게 좋은 여행이 있었나아아아")
				.planDateStart(LocalDate.parse("2022-08-10"))
				.planDateEnd(LocalDate.parse("2022-08-12"))
				.expense(0)
				.memberCount(1)
				.memberTypeId(1)
				.likeCount(3)
				.createDate(LocalDateTime.parse("2024-06-18 16:14:51", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.updateDate(LocalDateTime.parse("2024-06-18 16:14:51", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.likeState(true)
				.thumbnail(null)
				.build(),
				PlannerDto.builder()
				.plannerId(2)
				.accountId(2)
				.creator("test")
				.areaCode(null)
				.title("이렇게 좋은 여행이 있었나아아아")
				.planDateStart(LocalDate.parse("2022-08-10"))
				.planDateEnd(LocalDate.parse("2022-08-12"))
				.expense(0)
				.memberCount(1)
				.memberTypeId(1)
				.likeCount(3)
				.createDate(LocalDateTime.parse("2024-06-18 16:14:51", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.updateDate(LocalDateTime.parse("2024-06-18 16:14:51", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.likeState(true)
				.thumbnail("http://tong.visitkorea.or.kr/cms/resource/85/2031885_image2_1.jpg")
				.build(),
				PlannerDto.builder()
				.plannerId(1)
				.accountId(1)
				.creator("test")
				.areaCode(null)
				.title("이렇게 좋은 여행이 있었나아아아")
				.planDateStart(LocalDate.parse("2022-08-10"))
				.planDateEnd(LocalDate.parse("2022-08-12"))
				.expense(0)
				.memberCount(1)
				.memberTypeId(1)
				.likeCount(3)
				.createDate(LocalDateTime.parse("2024-06-18 16:14:51", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.updateDate(LocalDateTime.parse("2024-06-18 16:14:51", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.likeState(true)
				.thumbnail("http://tong.visitkorea.or.kr/cms/resource/85/2031885_image2_1.jpg")
				.build()
				);
		
		CommonRequestParamDto commonRequestParamDto = CommonRequestParamDto.builder()
				.itemCount(10)
				.sortCriteria(SortCriteria.LATEST)
				.keyword("")
				.pageNum(1)
				.build();
		
		PageInfo pageInfo = PageInfo.builder()
				.pageNum(commonRequestParamDto.getPageNum())
				.pageItemCount(commonRequestParamDto.getItemCount())
				.build();
		
		List<PlannerDto> findList = mapper.findLikeList(1, commonRequestParamDto, pageInfo);
		
		
		assertThat(findList).usingRecursiveComparison()
				.ignoringFields("createDate", "updateDate")
				.isEqualTo(testList);
	}
	
	@DisplayName("플래너 수정")
	@Test
	public void updatePlanner() throws Exception {
		PlannerDto updatePlanner = PlannerDto.builder()
				.title("수정 테스트")
				.planDateStart(LocalDate.parse("2024-07-06"))
				.planDateEnd(LocalDate.parse("2024-07-10"))
				.expense(10)
				.memberCount(5)
				.memberTypeId(2)
				.build();
		
		int update = mapper.updatePlanner(1, updatePlanner);
		
		assertThat(update).isEqualTo(1);
	}
	
	@DisplayName("플래너 삭제")
	@Test
	public void deletePlanner() throws Exception {
		int delete = mapper.deletePlanner(1);
		
		assertThat(delete).isEqualTo(1);
	}
	
	@DisplayName("플래너 총 개수 가져오기")
	@Test
	public void findListTotalCount() throws Exception {
		CommonRequestParamDto commonRequestParamDto = CommonRequestParamDto.builder()
				.itemCount(10)
				.sortCriteria(SortCriteria.LATEST)
				.keyword("")
				.pageNum(1)
				.build();
		
		int result = mapper.findListTotalCount(null, commonRequestParamDto);
		
		assertThat(result).isEqualTo(9);
	}
	
	@DisplayName("플래너 좋아요 총 개수 가져오기")
	@Test
	public void findLikeListTotalCount() throws Exception {
		CommonRequestParamDto commonRequestParamDto = CommonRequestParamDto.builder()
				.itemCount(10)
				.sortCriteria(SortCriteria.LATEST)
				.keyword("")
				.pageNum(1)
				.build();
		
		int result = mapper.findLikeListTotalCount(null, commonRequestParamDto);
		
		assertThat(result).isEqualTo(11);
	}
}
