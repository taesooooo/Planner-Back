package com.planner.planner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.planner.planner.Config.JwtContext;
import com.planner.planner.Config.RootAppContext;
import com.planner.planner.Config.SecurityContext;
import com.planner.planner.Config.ServletAppContext;
import com.planner.planner.Dto.SpotDto;
import com.planner.planner.Dto.SpotLikeDto;
import com.planner.planner.Service.SpotService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RootAppContext.class})
@Transactional
public class SpotServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(SpotServiceTest.class);
	
	@Autowired
	private SpotService service;
	
	@Test
	public void getSpotLikes() {
		List<SpotLikeDto> likes = service.spotLikesFindByAccountId(1);
		assertNotNull(likes);
		for(int i=0;i<likes.size();i++) {
			assertEquals(1, likes.get(i).getAccountId());			
		}
		logger.info(likes.toString());
	}
	
	@Test
	public void spotLike() {
		assertTrue(service.spotLike(1, 0));
	}
	
	@Test
	public void SpotCancel() {
		assertTrue(service.spotLikeCancel(1, 2));
	}
}
