package com.planner.planner.Util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.springframework.stereotype.Component;

@Component
public class RandomCode {
	private char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
			'V', 'W', 'X', 'Y', 'Z' };
	
	public RandomCode() {
	}
	
	public String createCode() {
		try {
			SecureRandom rnd = SecureRandom.getInstanceStrong();
			
			StringBuilder sb = new StringBuilder();
			
			for(int i=0; i < 6; i++) {
				int num = rnd.nextInt(10);
				sb.append(num);
			}
			
			return sb.toString();
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			
			return null;
		}
		
	}
	
	public String createStrCode(int limit, boolean encryption) {
		try {
			SecureRandom rnd = SecureRandom.getInstanceStrong();
			
			StringBuilder sb = new StringBuilder();
			int len = charSet.length;
			
			for(int i=0; i < limit; i++) {
				int num = rnd.nextInt(len);
				sb.append(charSet[num]);
			}
			
			if(encryption) {
				StringBuilder encodeSb = new StringBuilder();
				
				MessageDigest md = MessageDigest.getInstance("SHA-256");
				md.update((sb.toString() + System.currentTimeMillis()).getBytes());
				
				for(byte item : md.digest()) {
					encodeSb.append(String.format("%02x", item));
				}
				
				String encodeStr = encodeSb.toString();
				
				return encodeStr;
			}
			else {
				return sb.toString();				
			}
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			
			return null;
		}
		
	}
}
