package com.planner.planner.Service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.planner.planner.Config.RootAppContext;
import com.planner.planner.Dto.OpenApi.CommonListDto;
import com.planner.planner.Dto.OpenApi.OpenApiDto;
import com.planner.planner.Service.Impl.OpenAPIServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RootAppContext.class })
public class OpenAPIServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(OpenAPIServiceTest.class);
	
	@Autowired
	private OpenAPIServiceImpl apiServiceImpl;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void OpenAPI_지역코드리스트_가져오기() throws Exception {
		OpenApiDto param = new OpenApiDto.Builder()
				.setAreaCode(1)
				.setContentTypeId(12)
				.setPageNo(1)
				.build();
		
		CommonListDto list = apiServiceImpl.getAreaList(param);
		
		assertNotNull(list.getItems());
		assertFalse(list.getItems().isEmpty());
	}
	
	@Test
	public void OpenAPI_지역기반리스트_가져오기() throws Exception {
		OpenApiDto param = new OpenApiDto.Builder()
				.setAreaCode(1)
				.setContentTypeId(12)
				.setPageNo(1)
				.build();
		
		CommonListDto list = apiServiceImpl.getAreaList(param);
			
		assertNotNull(list.getItems());
		assertFalse(list.getItems().isEmpty());
		assertTrue(list.getNumOfRows() > 0);
		assertTrue(list.getPageNo() > 0);
		assertTrue(list.getTotalCount() > 0);
	}
}
