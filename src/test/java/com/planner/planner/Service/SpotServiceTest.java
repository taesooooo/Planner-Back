package com.planner.planner.Service;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.planner.planner.Config.RootAppContext;
import com.planner.planner.Dao.SpotDaoImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RootAppContext.class})
@Transactional
public class SpotServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(SpotServiceTest.class);

	@Mock
	private SpotDaoImpl spotDao;

	@InjectMocks
	private SpotServiceImpl service;

	@Before
	public void setup() {
		MockitoAnnotations.openMocks(this);
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
