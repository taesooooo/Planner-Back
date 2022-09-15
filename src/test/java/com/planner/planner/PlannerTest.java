package com.planner.planner;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.planner.planner.Dto.PlannerDto;
import com.planner.planner.Service.PlannerService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:root-context.xml","classpath:servlet-context.xml"})
public class PlannerTest {
	private static final Logger logger = LoggerFactory.getLogger(PlannerTest.class);
	
	@Autowired
	private PlannerService pService;
	
	@Test
	public void getAllPlannersTest() {
		List<PlannerDto> planners = pService.getAllPlanners();
		logger.info(planners.toString());
	}
	
	@Test
	public void getPlannersByIdTest() {
		PlannerDto planners = pService.read(0);
		logger.info(planners.toString());
	}
		
	@Test
	public void likePlannerTest() {
		assertTrue(pService.like(2, 2));
	}
	
	@Test
	public void likeCancelPlannerTest() {
		boolean result = pService.likeCancel(1, 3);
		assertTrue(result);
	}
	
}
