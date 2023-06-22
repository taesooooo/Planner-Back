package com.planner.planner.Config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.planner.planner.util.FileStore;

@Configuration
@Profile("dev")
@ComponentScan(basePackages = {"com.planner.planner.Service", "com.planner.planner.Dao"})
@PropertySource("classpath:config/config.properties")
@EnableTransactionManagement
public class RootAppDevContext {
	@Value("${dev.jdbc.driver}")
	private String driver;
	@Value("${dev.jdbc.url}")
	private String url;
	@Value("${dev.jdbc.username}")
	private String username;
	@Value("${dev.jdbc.password}")
	private String password;
	@Value("${upload.path}")
	private String baseLocation;
	
	@Bean
	public DataSource dataSource() {
		DataSource dataSource = new DataSource();
		dataSource.setDriverClassName(driver);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}

	@Bean
	public FileStore fileStore() {
		return new FileStore(baseLocation);
	}

	@Bean
	public DataSourceTransactionManager transactionalManager() {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
		transactionManager.setDataSource(dataSource());
		return transactionManager;
	}

	@Bean
	public JdbcTemplate jdbcTemplate() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(dataSource());
		return jdbcTemplate;
	}
}
