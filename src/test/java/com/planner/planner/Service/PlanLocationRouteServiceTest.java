package com.planner.planner.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.planner.planner.Common.Coordinate;
import com.planner.planner.Dto.PlanLocationRouteDto;
import com.planner.planner.Mapper.PlanLocationRouteMapper;
import com.planner.planner.Service.Impl.PlanLocationRouteServiceImpl;

@ExtendWith(MockitoExtension.class)
class PlanLocationRouteServiceTest {

	@InjectMocks
	private PlanLocationRouteServiceImpl planLocationRouteService;

	@Mock
	private PlanLocationRouteMapper planLocationRouteMapper;

	private PlanLocationRouteDto testDto;

	@BeforeEach
	void setUp() {
		List<Coordinate> routeList = List.of(new Coordinate(0.00, 0.00), new Coordinate(1.11, 1.11), new Coordinate(2.22, 2.22));

		testDto = PlanLocationRouteDto.builder().id(1).planId(1).routeList(routeList).build();
	}

	@DisplayName("일정 경로 생성")
	@Test
	void createPlanLocationRoute() {
		when(planLocationRouteMapper.createPlanLocationRoute(any(PlanLocationRouteDto.class))).thenReturn(1);

		int id = planLocationRouteService.createPlanLocationRoute(testDto);

		assertEquals(testDto.getId(), id);
	}

	@DisplayName("일정 경로 가져오기 - 아이디")
	@Test
	void findPlanLocationRouteById() {
		PlanLocationRouteDto testDto = PlanLocationRouteDto.builder()
				.id(1)
				.planId(1)
				.startIndex(0)
				.endIndex(1)
				.routeList(List.of(new Coordinate(0.00, 0.00),new Coordinate(1.11, 1.11), new Coordinate(2.22, 2.22)))
				.routeWKT("LINESTRING(0 0,1.11 1.11,2.22 2.22)")
				.build();

		when(planLocationRouteMapper.findPlanLocationRouteById(1)).thenReturn(testDto);

		PlanLocationRouteDto result = planLocationRouteService.findPlanLocationRouteById(1);

		assertThat(result).usingRecursiveComparison()
				.isEqualTo(testDto);
	}
	
	@DisplayName("일정 경로 리스트 가져오기 - 플랜 아이디")
	@Test
	void findPlanLocationRouteListByPlanId() {
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

		when(planLocationRouteMapper.findPlanLocationRouteListByPlanId(1)).thenReturn(testList);

		List<PlanLocationRouteDto> resultList = planLocationRouteService.findPlanLocationRouteListByPlanId(1);

		assertThat(resultList).usingRecursiveComparison()
				.isEqualTo(testList);
	}

	@DisplayName("일정 경로 수정 - 아이디")
	@Test
	void updatePlanLocationRouteById() {
		PlanLocationRouteDto testDto = PlanLocationRouteDto.builder()
				.id(1)
				.routeList(List.of(new Coordinate(5.55, 5.55), new Coordinate(6.66, 6.66), new Coordinate(7.77, 7.77)))
				.build();
		
		when(planLocationRouteMapper.updatePlanLocationRouteById(any(PlanLocationRouteDto.class))).thenReturn(1);

		boolean result = planLocationRouteService.updatePlanLocationRouteById(testDto);

		assertTrue(result);
	}

	@DisplayName("일정 경로 삭제 - 아이디")
	@Test
	void deletePlanLocationRouteById() {
		when(planLocationRouteMapper.deletePlanLocationRouteById(1)).thenReturn(1);

		boolean result = planLocationRouteService.deletePlanLocationRouteById(1);

		assertTrue(result);
	}

}
