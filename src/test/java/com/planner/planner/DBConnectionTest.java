package com.planner.planner;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DBConnectionTest {
	private static final Logger logger = LoggerFactory.getLogger(DBConnectionTest.class);

	@Autowired
	private DataSource dataSource;

	@Test
	public void connection() {
		try {
			Connection con = dataSource.getConnection();
			logger.info(con.getSchema());
			
			assertThat(con).isNotNull();
		}
		catch (Exception e) {
			logger.info(e.getMessage());
		}
	}

}
