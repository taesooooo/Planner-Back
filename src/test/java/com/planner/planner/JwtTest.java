package com.planner.planner;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.planner.planner.Util.JwtUtil;

public class JwtTest {
	private static final Logger logger = LoggerFactory.getLogger(JwtTest.class);

	private JwtUtil jwtUtil;
	
	@Before
	public void setUp() throws Exception {
		this.jwtUtil = new JwtUtil("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
	}

	@Test
	public void jwtCreateTest() {
		logger.info(jwtUtil.createAccessToken(1000));
	}

	@Test
	public void jwtVerifyTest() {
		String token = jwtUtil.createAccessToken(1);
		
		assertThat(jwtUtil.verifyToken(token)).isTrue();
	}
}
