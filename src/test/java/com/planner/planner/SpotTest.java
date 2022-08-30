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

import com.planner.planner.Dto.SpotDto;
import com.planner.planner.Service.SpotService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:root-context.xml","classpath:servlet-context.xml"})
public class SpotTest {
	private static final Logger logger = LoggerFactory.getLogger(SpotTest.class);
	
	@Autowired
	private SpotService service;
	
	@Test
	public void getSpots() {
		List<SpotDto> spots = service.getAllSpot();
		logger.info(spots.toString());
	}
	
	@Test
	public void SpotCancel() {
		assertTrue(service.spotLikeCancel(2, 2));
	}
}
