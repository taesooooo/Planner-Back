package com.planner.planner.Mapper;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.ActiveProfiles;

import com.planner.planner.Common.Coordinate;
import com.planner.planner.Dto.PlanLocationRouteDto;

@ActiveProfiles("test")
@MybatisTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class PlanLocationRouteMapperTest {
	
	private static final Logger log = LoggerFactory.getLogger(PlanLocationRouteMapperTest.class);

	@Autowired
	private PlanLocationRouteMapper planLocationRouteMapper;

	@BeforeEach
	void setUp() throws Exception {
	}

	@DisplayName("일정 여행지 루트 생성")
	@Test
	public void createPlanLocationRoute() {
		PlanLocationRouteDto dto = PlanLocationRouteDto.builder()
				.planId(1)
				.startIndex(0)
				.endIndex(1)
				.routeList(Arrays.asList(new Coordinate(0, 0), new Coordinate(1, 1), new Coordinate(2, 2)))
				.build();
	
		int result = planLocationRouteMapper.createPlanLocationRoute(dto);
		
		assertThat(result).isEqualTo(1);
		assertThat(dto.getId()).isEqualTo(5);
	}
	
	@DisplayName("일정 여행지 루트 가져오기(아이디)")
	@Test
	public void findPlanLocationRouteById() {
		PlanLocationRouteDto testDto = PlanLocationRouteDto.builder()
				.id(1)
				.planId(1)
				.startIndex(0)
				.endIndex(1)
				.routeList(List.of(new Coordinate(0.00, 0.00),new Coordinate(1.11, 1.11), new Coordinate(2.22, 2.22)))
				.routeWKT("LINESTRING(0 0,1.11 1.11,2.22 2.22)")
				.build();
		
		PlanLocationRouteDto dto = planLocationRouteMapper.findPlanLocationRouteById(1);
		
		
		assertThat(dto).usingRecursiveComparison().isEqualTo(testDto);
	}
	
	@DisplayName("일정 여행지 루트 리스트 가져오기(일정 아이디)")
	@Test
	public void findPlanLocationRouteListByPlanId() {
		List<PlanLocationRouteDto> testList = Arrays.asList(
				PlanLocationRouteDto.builder()
					.id(1)
					.planId(1)
					.startIndex(0)
					.endIndex(1)
					.routeList(List.of(new Coordinate(0.00, 0.00),new Coordinate(1.11, 1.11), new Coordinate(2.22, 2.22)))
					.routeWKT("LINESTRING(0 0,1.11 1.11,2.22 2.22)")
					.build(),
				PlanLocationRouteDto.builder()
					.id(2)
					.planId(1)
					.startIndex(1)
					.endIndex(2)
					.routeList(List.of(new Coordinate(2.22, 2.22),new Coordinate(3.33, 3.33), new Coordinate(4.44, 4.44)))
					.routeWKT("LINESTRING(2.22 2.22,3.33 3.33,4.44 4.44)")
					.build());

		List<PlanLocationRouteDto> dtoList = planLocationRouteMapper.findPlanLocationRouteListByPlanId(1);
		
		assertThat(dtoList).usingRecursiveComparison().isEqualTo(testList);
	}

	@DisplayName("일정 여행지 루트 수정(아이디)")
	@Test
	public void updatePlanLocationRouteById() {
		int locationRouteId = 1;
		PlanLocationRouteDto testDto = PlanLocationRouteDto.builder()
				.id(1)
				.routeList(List.of(new Coordinate(5.55, 5.55), new Coordinate(6.66, 6.66), new Coordinate(7.77, 7.77)))
				.build();
		
		int result = planLocationRouteMapper.updatePlanLocationRouteById(locationRouteId, testDto);
		
		assertThat(result).isEqualTo(1);
	}
	
	@DisplayName("일정 여행지 루트 삭제(아이디)")
	@Test
	public void deletePlanLocationRouteById() {
		int result = planLocationRouteMapper.deletePlanLocationRouteById(1);
		
		assertThat(result).isEqualTo(1);
	}
}
