package com.planner.planner.Mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.ActiveProfiles;

import com.planner.planner.Dto.PlanMemoDto;

@ActiveProfiles("test")
@MybatisTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class PlanMemoMapperTest {
	@Autowired
	private PlanMemoMapper mapper;
	
	@DisplayName("일정 메모 생성")
	@Test
	public void insertPlanMemo() {
		PlanMemoDto testMemo = PlanMemoDto.builder()
				.title("메모 생성 테스트")
				.content("메모 생성 테스트 내용")
				.build();
		
		mapper.insertPlanMemo(1, testMemo);
		
		assertThat(testMemo.getMemoId()).isEqualTo(5);
	}
	
	@DisplayName("플래너 아이디로 모든 일정 메모 가져오기")
	@Test
	public void findPlanMemoListByPlannerId() {
		List<PlanMemoDto> testMemos = Arrays.asList(
				PlanMemoDto.builder()
				.memoId(1)
				.title("메모")
				.content("메모내용")
				.build(),
				PlanMemoDto.builder()
				.memoId(2)
				.title("메모2")
				.content("메모내용2")
				.build()
				);
		
		List<PlanMemoDto> findMemos = mapper.findPlanMemoListByPlannerId(1);
		
		assertThat(findMemos).usingRecursiveComparison()
				.ignoringFields("createDate", "updateDate")
				.isEqualTo(testMemos);
	}
	
	@DisplayName("메모 수정")
	@Test
	public void updatePlanMemo() {
		PlanMemoDto testMemo = PlanMemoDto.builder()
				.title("메모 수정 테스트")
				.content("메모 수정 테스트")
				.build();
		
		int result = mapper.updatePlanMemo(1, testMemo);
		
		assertThat(result).isEqualTo(1);
	}
	
	@DisplayName("메모 삭제")
	@Test
	public void deletePlanMemo() {
		int result = mapper.deletePlanMemo(1);
		
		assertThat(result).isEqualTo(1);
	}
}
