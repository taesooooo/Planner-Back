package com.planner.planner.Service;

import javax.mail.MessagingException;

public interface EmailService {
	public boolean sendAuthenticationCode(String to, String code) throws Exception;
	public void sendPasswordResetEmail(String to, String content) throws MessagingException;
}
