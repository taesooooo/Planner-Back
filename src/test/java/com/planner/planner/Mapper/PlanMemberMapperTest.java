package com.planner.planner.Mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.ActiveProfiles;

import com.planner.planner.Dto.PlanMemberDto;

@ActiveProfiles("test")
@MybatisTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class PlanMemberMapperTest {
	@Autowired
	private PlanMemberMapper mapper;
	
	@DisplayName("일정 멤버 추가")
	@Test
	public void insertPlanMember() throws Exception {
		int result = mapper.insertPlanMember(1, 1);
		
		assertThat(result).isEqualTo(1);
	}
	
	@DisplayName("플래너 아이디로 모든 일정 멤버 가져오기")
	@Test
	public void findPlanMemberListByPlannerId() throws Exception {
		List<PlanMemberDto> testMembers = Arrays.asList(
				PlanMemberDto.builder()
				.planMemberId(1)
				.plannerId(1)
				.accountId(1)
				.build(),
				PlanMemberDto.builder()
				.planMemberId(2)
				.plannerId(1)
				.accountId(2)
				.build()
				);
		
		List<PlanMemberDto> findMembers = mapper.findPlanMemberListByPlannerId(1);
		
		assertThat(findMembers).usingRecursiveComparison()
				.isEqualTo(testMembers);
	}
	
	@DisplayName("일정 멤버 삭제")
	@Test
	public void deletePlanMember() throws Exception {
		int result = mapper.deletePlanMember(1, 1);
		
		assertThat(result).isEqualTo(1);
	}
}
