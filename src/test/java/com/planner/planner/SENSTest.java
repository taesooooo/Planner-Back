package com.planner.planner;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

import com.planner.planner.Config.Properites.CommonProperties;
import com.planner.planner.Service.SENSService;
import com.planner.planner.Service.Impl.SENSServiceImpl;
import com.planner.planner.Util.RandomCode;

@SpringBootTest(classes = {SENSServiceImpl.class, CommonProperties.class})
@EnableConfigurationProperties
public class SENSTest {
	
	@Autowired
	private SENSService sensService;
	
	@Autowired
	private RandomCode randomCode;

	@BeforeEach
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
