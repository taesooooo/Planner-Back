package com.planner.planner.Service;

import javax.mail.MessagingException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.planner.planner.Config.MailSenderConfig;
import com.planner.planner.Service.Impl.EmailServiceImpl;
import com.planner.planner.Util.RandomCode;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MailSenderConfig.class, RandomCode.class })
@TestPropertySource("classpath:/config/config.properties")
public class EmailServiceTest {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private RandomCode randomCode;

	
	private EmailServiceImpl emailServiceImpl;
	
	@Before
	public void setup() {
		emailServiceImpl = new EmailServiceImpl(javaMailSender);
	}
	
	@Test
	public void 이메일_전송() throws MessagingException {
		String email = "";
		String resetKey = randomCode.createStrCode(6, true);
		emailServiceImpl.sendPasswordResetEmail(email, resetKey);
	}
}
