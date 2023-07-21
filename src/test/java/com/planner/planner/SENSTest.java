package com.planner.planner;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.planner.planner.Config.RootAppContext;
import com.planner.planner.Service.Impl.SENSServiceImpl;
import com.planner.planner.Util.RandomCode;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RootAppContext.class})
@TestPropertySource("classpath:/config/config.properties")
public class SENSTest {
	
	@Autowired
	private SENSServiceImpl sensService;
	
	@Autowired
	private RandomCode randomCode;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void sms_send_test() throws Exception {
		String testPhone = "";
		String code = randomCode.createCode();
		
		boolean result = sensService.authenticationCodeSMSSend(testPhone, code);
		
		assertThat(result).isTrue();
	}
}
