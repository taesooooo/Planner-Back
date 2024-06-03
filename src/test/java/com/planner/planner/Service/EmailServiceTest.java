package com.planner.planner.Service;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import com.planner.planner.Service.Impl.EmailServiceImpl;
import com.planner.planner.Util.RandomCode;

import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {
	
	@Mock
	private JavaMailSender javaMailSender;
	
	@Mock
	private RandomCode randomCode;

//	@InjectMocks
	private EmailServiceImpl emailServiceImpl;
	
	@BeforeEach
	public void setup() {
		emailServiceImpl = new EmailServiceImpl(javaMailSender);
	}
	
	@Test
	public void 이메일_전송() throws MessagingException {
		String email = "test@test.com";
		
		when(javaMailSender.createMimeMessage()).thenReturn(new MimeMessage((Session)null));
		when(randomCode.createStrCode(anyInt(), anyBoolean())).thenReturn("123456");
		
		String resetKey = randomCode.createStrCode(6, true);
		emailServiceImpl.sendPasswordResetEmail(email, resetKey);
	}
}
