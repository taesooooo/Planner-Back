package com.planner.planner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.joda.LocalDateTimeParser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.planner.planner.Config.RootAppContext;
import com.planner.planner.Dao.PlanDao;
import com.planner.planner.Dao.PlanLocationDao;
import com.planner.planner.Dao.PlanMemberDao;
import com.planner.planner.Dao.PlannerDao;
import com.planner.planner.Dto.PlanDto;
import com.planner.planner.Dto.PlanLocationDto;
import com.planner.planner.Dto.PlannerDto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RootAppContext.class })
public class PlannerTest {
	private static final Logger logger = LoggerFactory.getLogger(PlannerTest.class);

	@Autowired
	private PlannerDao pDao;
	@Autowired
	private PlanDao planDao;
	@Autowired
	private PlanMemberDao planMemberDao;
	@Autowired
	private PlanLocationDao planLDao;
	
	private ObjectMapper om = new ObjectMapper();
	
	@Before
	public void setup() {
		om.registerModule(new JavaTimeModule());
	}

	@Test
	public void plannerTest() throws JsonProcessingException {
		PlannerDto p = pDao.findPlannerByPlannerIda(1);
		logger.info(om.writeValueAsString(p));
	}
	
	@Test
	public void planTest() {
		PlanDto p = new PlanDto.Builder().setPlanLocations(null).setPlanDate(LocalDateTime.of(2023, 01,13,00,00)).setPlannerId(1).build();
		int a = pDao.insertPlan(p);
		logger.info("id:"+a);
	}
	
	@Test
	public void planLocationTest() {
		PlanLocationDto p = new PlanLocationDto.Builder().setLocationContetntId(1).setLocationImage("").setLocationTransportation(1).setPlanId(2).build();
		int a = pDao.insertPlanLocation(p);
		logger.info("id:"+a);
	}
	
	@Test
	public void plannerTest2() throws JsonProcessingException {
		PlannerDto p = pDao.findPlannerByPlannerId(1);
		List<String> members = planMemberDao.findPlanMembers(1);
		List<PlanDto> plan = planDao.findPlansByPlannerId(1);
		List<List<PlanLocationDto>> loc = new ArrayList<List<PlanLocationDto>>();
		for(PlanDto pp : plan) {
			List<PlanLocationDto> l = planLDao.findPlanLocationsByPlanId(pp.getPlanId());
			pp.getPlanLocation().addAll(l);
		}
		
		PlannerDto planner = new PlannerDto.Builder()
				.setPlannerId(p.getPlannerId())
				.setAccountId(p.getAccountId())
				.setTitle(p.getTitle())
				.setPlanDateStart(p.getPlanDateStart())
				.setPlanDateEnd(p.getPlanDateEnd())
				.setPlans(plan)
				.setPlanMemberEmails(members)
				.setCreateDate(p.getCreateDate())
				.setUpdateDate(p.getUpdateDate())
				.build();
		logger.info(om.writeValueAsString(planner));
	}
}
