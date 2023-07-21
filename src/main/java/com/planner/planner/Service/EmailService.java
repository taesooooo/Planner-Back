package com.planner.planner.Service;

import javax.mail.MessagingException;

public interface EmailService {
	public void sendPasswordResetEmail(String to, String content) throws MessagingException;
}
