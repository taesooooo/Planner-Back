package com.planner.planner.util;

import java.security.Key;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@PropertySource("classpath:config/config.properties")
public class JwtUtil {
	private String AuthHeader = "Authorization";
	
	private Key key;
	private int expiationTime = 60*60*24*7; // 만료기간 7일
	private Claims claims;
	
	public JwtUtil(String secretKey) {
		key = Keys.hmacShaKeyFor(secretKey.getBytes());
	}
	
	public String getToken(HttpServletRequest req) {
		String token = req.getHeader(AuthHeader);
		return token;
	}
	
	public String createToken(String userId) {
		Date date = new Date();
		String token = Jwts.builder()
				.setIssuedAt(date)
				.setExpiration(new Date(date.getTime() + expiationTime))
				.claim("userId", userId)
				.signWith(key,SignatureAlgorithm.HS256)
				.compact();
		return token;
	}
	
	public Boolean verifyToken(String token) {
		try {
			claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
			return true;
		} catch (SignatureException e) {
			return false;
		} catch (ExpiredJwtException e) {
			return false;
		} catch (UnsupportedJwtException e) {
			return false;
		} catch (MalformedJwtException e) {
			return false;
		}
	}
	
	public String getUserId() {
		if(claims != null) {
			return claims.get("userId").toString();
		}
		else {
			return null;
		}
	}
}
