package com.planner.planner.Service.Impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.planner.planner.Dao.AuthenticationCodeDao;
import com.planner.planner.Service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {
	
	@Value("${front.passwordResetUrl}")
	private String passwordResetUrl;
	
	private JavaMailSender mailSender;

	public EmailServiceImpl(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Override
	public boolean sendAuthenticationCode(String to, String code) throws MessagingException {
		StringBuilder sb = new StringBuilder();
		sb.append("<h2>한국다봄</h2>");
		sb.append("<p>이메일 인증 코드: %s</p>");
		sb.append("<p>해당 인증 코드를 공유하지 마시기 바랍니다.</p>");

		MimeMessage message = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
		helper.setTo(to);
		helper.setSubject("한국다봄 이메일 인증");
		helper.setText(String.format(sb.toString(), code), true);
		mailSender.send(message);
		
		return true;
	}

	@Override
	public void sendPasswordResetEmail(String to, String resetKey) throws MessagingException {
		StringBuilder sb = new StringBuilder();
		sb.append("<h2>한국다봄</h2>");
		sb.append("<p>비밀번호 재설정 주소:<a href='%s'>%s</a></p>");
		sb.append("<p>해당 주소를 공유하지 마시기 바랍니다.</p>");

		String resetUrl = passwordResetUrl + "?key=" + resetKey;

		MimeMessage message = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
		helper.setTo(to);
		helper.setSubject("한국다봄 비밀번호 재설정");
		helper.setText(String.format(sb.toString(), resetUrl, resetUrl), true);
		mailSender.send(message);
	}
}
