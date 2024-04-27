package com.planner.planner;

import java.sql.Connection;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:root-context.xml")
public class DBConnectionTest {
	private static final Logger logger = LoggerFactory.getLogger(DBConnectionTest.class);

	@Autowired
	private DataSource dataSource;

	@Test
	public void connection() {
		try {
			Connection con = dataSource.getConnection();
			logger.info(con.getSchema());
		}
		catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
		}
	}

}
