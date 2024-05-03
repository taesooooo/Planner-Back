package com.planner.planner.Service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

import com.planner.planner.Config.Properites.CommonProperties;
import com.planner.planner.Dto.OpenApi.AreaCodeDto;
import com.planner.planner.Dto.OpenApi.CommonBasedDto;
import com.planner.planner.Dto.OpenApi.CommonListDto;
import com.planner.planner.Dto.OpenApi.OpenApiDto;
import com.planner.planner.Service.Impl.OpenAPIServiceImpl;

@SpringBootTest(classes = { OpenAPIServiceImpl.class, CommonProperties.class })
@EnableConfigurationProperties
public class OpenAPIServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(OpenAPIServiceTest.class);
	
	@Autowired
	private OpenAPIService apiServiceImpl;
	
	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void OpenAPI_지역코드리스트_가져오기() throws Exception {
		CommonListDto<AreaCodeDto> list = apiServiceImpl.getAreaNum();
		
		assertThat(list.getItems()).isNotNull();
		assertThat(list.getItems()).isNotEmpty();
	}
	
	@Test
	public void OpenAPI_지역기반리스트_가져오기() throws Exception {
		OpenApiDto param = OpenApiDto.builder()
				.areaCode(1)
				.contentTypeId(12)
				.pageNo(1)
				.build();
		
		CommonListDto<CommonBasedDto> list = apiServiceImpl.getAreaList(param);
			
		assertThat(list.getItems())
				.isNotNull()
				.isNotEmpty();
		
		assertThat(list.getNumOfRows())
				.isGreaterThan(0);
		assertThat(list.getPageNo())
				.isGreaterThan(0);
		assertThat(list.getTotalCount())
				.isGreaterThan(0);
	}
}
