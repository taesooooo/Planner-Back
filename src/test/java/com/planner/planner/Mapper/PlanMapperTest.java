package com.planner.planner.Mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.ActiveProfiles;

import com.planner.planner.Dto.PlanDto;

@ActiveProfiles("test")
@MybatisTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class PlanMapperTest {
	@Autowired
	private PlanMapper mapper;
	
	@DisplayName("플랜 생성")
	@Test
	public void insertPlan() {
		PlanDto testCreatePlan = PlanDto.builder()
				.planDate(LocalDate.now())
				.planIndex(0)
				.build();
		
		mapper.insertPlan(1, testCreatePlan);
		
		assertThat(testCreatePlan.getPlanId()).isEqualTo(7);
	}
	
	@DisplayName("플래너 아이디로 플랜 가져오기")
	@Test
	public void findByPlannerId() {
		PlanDto plan1 = PlanDto.builder()
				.planId(1)
				.planDate(LocalDate.parse("2023-04-11"))
				.plannerId(1)
				.planIndex(1024)
				.build();
		PlanDto plan2 = PlanDto.builder()
				.planId(3)
				.planDate(LocalDate.parse("2023-04-11"))
				.plannerId(1)
				.planIndex(2048)
				.build();
		PlanDto plan3 = PlanDto.builder()
				.planId(2)
				.planDate(LocalDate.parse("2023-04-11"))
				.plannerId(1)
				.planIndex(3072)
				.build();
		
		List<PlanDto> testPlans = Arrays.asList(plan1, plan2, plan3); 
		
		List<PlanDto> findPlans = mapper.findPlanListByPlannerId(1);
		
		assertThat(findPlans).usingRecursiveComparison()
				.isEqualTo(testPlans);
	}
	
	@DisplayName("플랜 수정")
	@Test
	public void updatePlan() {
		PlanDto plan = PlanDto.builder()
				.planDate(LocalDate.parse("2023-04-11"))
				.planIndex(4048)
				.build();
		
		int result = mapper.updatePlan(1, plan);
		
		assertThat(result).isEqualTo(1);
	}
	
	@DisplayName("플랜 삭제")
	@Test
	public void deletePlan() {
		int result = mapper.deletePlan(1);
		
		assertThat(result).isEqualTo(1);
	}
}
