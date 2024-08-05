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

import com.planner.planner.Dto.PlanLocationDto;

@ActiveProfiles("test")
@MybatisTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class PlanLocationMapperTest {
	@Autowired
	private PlanLocationMapper mapper;
	
	@DisplayName("일정 여행지 생성")
	@Test
	public void insertPlanLocation() {
		PlanLocationDto testLocation = PlanLocationDto.builder()
				.locationName("생성 테스트")
				.locationContentId(0000)
				.locationImage("test image")
				.locationAddr("test addr")
				.locationMapx(0000.0000)
				.locationMapy(0000.0000)
				.locationTransportation(1)
				.build();
		
		mapper.insertPlanLocation(1, testLocation);
		
		assertThat(testLocation.getLocationId()).isEqualTo(7);
	}

	@DisplayName("일정아이디로 모든 일정 여행지 가져오기")
	@Test
	public void findPlanLocationListByPlanId() {
		List<PlanLocationDto> testLocationList = Arrays.asList(
				PlanLocationDto.builder()
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
				.build());
		
		List<PlanLocationDto> findLocationList = mapper.findPlanLocationListByPlanId(1);
		
		assertThat(findLocationList).usingRecursiveComparison()
				.isEqualTo(testLocationList);
	}

	@DisplayName("일정 여행지 수정")
	@Test
	public void updatePlanLocation() {
		PlanLocationDto testLocation = PlanLocationDto.builder()
				.locationName("수정 테스트")
				.locationContentId(1111)
				.locationImage("update image")
				.locationAddr("update addr")
				.locationMapx(1111.1111)
				.locationMapy(1111.1111)
				.locationTransportation(1)
				.build();
		
		int result = mapper.updatePlanLocation(1, testLocation);
		
		assertThat(result).isEqualTo(1);
	}
	
	@DisplayName("일정 여행지 삭제")
	@Test
	public void deletePlanLocation() {
		int result = mapper.deletePlanLocation(1);
		
		assertThat(result).isEqualTo(1);
	}
}
