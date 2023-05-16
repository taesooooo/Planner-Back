package com.planner.planner;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.planner.planner.Config.JwtContext;
import com.planner.planner.util.JwtUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JwtContext.class)
public class JwtTest {
	private static final Logger logger = LoggerFactory.getLogger(JwtTest.class);

	@Autowired
	private JwtUtil util;
	
	public JwtTest(JwtUtil util) {
		this.util = util;
	}

	@Test
	public void jwtCreateTest() {
		logger.info(util.createToken(1000));
	}

	@Test
	public void jwtVerifyTest() {
		//"eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2NjE0Mjg0MDMsImV4cCI6MTY2MjAzMzIwMywidXNlcklkIjoiMTAwMCJ9.ANY3F5Q0EUB1qBlcqLLq5muxYJTgirR4so-xwEK0O3Q"
		//assertTrue(util.verifyToken("eiJ9.eyJpYXQiOjE2NjE0MjcCI6MTY2MjAzMzIwMywidXNlcklkIjoiMTAwMCJ9.ANY3F5Q0EUB1qBlcqLLq5muxYJTgirR4so-xwEK0O3Q"));
		assertTrue(util.verifyToken("eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2NjE0Mjg0MDMsImV4cCI6MTY2MjAzMzIwMywidXNlcklkIjoiMTAwMCJ9.ANY3F5Q0EUB1qBlcqLLq5muxYJTgirR4so-xwEK0O3Q"));
	}
}
