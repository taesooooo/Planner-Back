package com.planner.planner.Config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@PropertySource("classpath:config/config.properties")
public class MailSenderConfig {
	
	@Value("${mail.host}")
	private String mailHost;
	@Value("${mail.port}")
	private String mailPort;
	@Value("${mail.username}")
	private String mailUsername;
	@Value("${mail.password}")
	private String mailPassword;
	
	@Value("${mail.transport.protocol}")
	private String mailProtocol;
	@Value("${mail.smtp.auth}")
	private String mailAuth;
	@Value("${mail.smtp.starttls.enable}")
	private String mailTls;
	@Value("${mail.debug}")
	private String mailDebug;
	
	@Bean
	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(mailHost);
		mailSender.setPort(Integer.parseInt(mailPort));
		mailSender.setUsername(mailUsername);
		mailSender.setPassword(mailPassword);

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", mailProtocol);
		props.put("mail.smtp.auth", mailAuth);
		props.put("mail.smtp.starttls.enable", mailTls);
		props.put("mail.debug", mailDebug);

		return mailSender;
	}

}
