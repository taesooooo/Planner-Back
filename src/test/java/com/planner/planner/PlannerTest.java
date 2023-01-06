package com.planner.planner;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.planner.planner.Config.RootAppContext;
import com.planner.planner.Dao.PlannerDao;
import com.planner.planner.Dto.PlannerDto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RootAppContext.class })
public class PlannerTest {
	private static final Logger logger = LoggerFactory.getLogger(PlannerTest.class);

	@Autowired
	private PlannerDao pDao;

	@Test
	public void plannerTest() {
		PlannerDto p = pDao.findPlannerByPlannerId(1);
		logger.debug(p.toString());
	}
	
	@Test
	public void plannersTest() {
		List<PlannerDto> p = pDao.findPlannersAll();
		logger.debug(p.toString());
	}
}
