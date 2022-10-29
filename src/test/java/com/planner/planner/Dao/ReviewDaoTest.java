package com.planner.planner.Dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import com.planner.planner.Config.RootAppContext;

@WebAppConfiguration
@ContextConfiguration(classes = {RootAppContext.class})
public class ReviewDaoTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
