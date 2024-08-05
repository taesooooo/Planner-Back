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

@ActiveProfiles("test")
@MybatisTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class PlannerLikeMapperTest {
	@Autowired
	private PlannerLikeMapper mapper;
	
	@DisplayName("플래너 좋아요")
	@Test
	public void plannerLike() {
		int result = mapper.plannerLike(1, 6);
		
		assertThat(result).isEqualTo(1);
	}
	
	@DisplayName("플래너 좋아요 취소")
	@Test
	public void plannerUnLike() {
		int result = mapper.plannerUnLike(1, 1);
		
		assertThat(result).isEqualTo(1);
	}
	
	@DisplayName("좋아요 확인")
	@Test
	public void isLike() {
		boolean result = mapper.isLike(1, 1);
		
		assertThat(result).isTrue();
	}
	
	@DisplayName("좋아요 확인 - False")
	@Test
	public void isLikeFalse() {
		boolean result = mapper.isLike(1, 6);
		
		assertThat(result).isFalse();
	}
	
	@DisplayName("플래너 리스트중 계정에 좋아요 되어있는 리스트 가져오기")
	@Test
	public void findLikePlannerIdList() {
		List<Integer> testPlannerIdList = Arrays.asList(1, 2, 3, 4);
		
		List<Integer> findLikePlannerIdList = mapper.findLikePlannerIdList(1, testPlannerIdList);
		
		assertThat(findLikePlannerIdList).hasSameElementsAs(testPlannerIdList);
	}
}
