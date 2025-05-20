package com.planner.planner.Service;

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
		List<Coordinate> routeList = Arrays.asList(new Coordinate(0.00, 0.00), new Coordinate(1.11, 1.11), new Coordinate(2.22, 2.22));

		testDto = PlanLocationRouteDto.builder().id(1).planId(1).routeList(routeList).build();
	}

	@DisplayName("일정 경로 생성")
	@Test
	void createPlanLocationRoute() {
		when(planLocationRouteMapper.createPlanLocationRoute(any(), anyString())).thenReturn(1);

		int id = planLocationRouteService.createPlanLocationRoute(testDto);

		assertEquals(testDto.getId(), id);
		verify(planLocationRouteMapper).createPlanLocationRoute(eq(testDto), anyString());
	}

	@DisplayName("일정 경로 가져오기 - 아이디")
	@Test
	void findPlanLocationRouteById() {
		String wkt = "LINESTRING(0.00 0.00,1.11 1.11,2.22 2.22)";
		PlanLocationRouteDto dtoWithWkt = PlanLocationRouteDto.builder().id(1).routeWKT(wkt).build();

		when(planLocationRouteMapper.findPlanLocationRouteById(1)).thenReturn(dtoWithWkt);

		PlanLocationRouteDto result = planLocationRouteService.findPlanLocationRouteById(1);

		assertEquals(3, result.getRouteList().size());
		assertEquals(0, result.getRouteList().get(0).getLongitude());
	}
	
	@DisplayName("일정 경로 리스트 가져오기 - 플랜 아이디")
	@Test
	void findPlanLocationRouteListByPlanId() {
		String wkt = "LINESTRING(0.00 0.00,1.11 1.11,2.22 2.22)";
		List<PlanLocationRouteDto> mockList = List.of(PlanLocationRouteDto.builder().id(1).routeWKT(wkt).build());

		when(planLocationRouteMapper.findPlanLocationRouteListByPlanId(1)).thenReturn(mockList);

		List<PlanLocationRouteDto> resultList = planLocationRouteService.findPlanLocationRouteListByPlanId(1);

		assertEquals(1, resultList.size());
		assertEquals(3, resultList.get(0).getRouteList().size());
	}

	@DisplayName("일정 경로 수정 - 아이디")
	@Test
	void updatePlanLocationRouteById() {
		when(planLocationRouteMapper.updatePlanLocationRouteById(eq(1), anyString())).thenReturn(1);

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
