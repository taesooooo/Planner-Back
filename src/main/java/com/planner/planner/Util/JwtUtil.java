package com.planner.planner.Util;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.planner.planner.Config.Properites.CommonProperties;
import com.planner.planner.Exception.InValidTokenException;
import com.planner.planner.Exception.TokenExpiredException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Component
public class JwtUtil {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
	
	private final CommonProperties commonProperties;

	private Key key;
	private int AccessExpirationTime = 60 * 60 * 24 * 1000; // 액세스 토큰 만료기간 1일
	private int refreshExpirationTime = 60 * 60 * 24 * 14 * 1000; // 리플래시 토큰 만료기간 14일

	public JwtUtil(CommonProperties commonProperties) {
		this.commonProperties = commonProperties;
		this.key = Keys.hmacShaKeyFor(commonProperties.getJwt().getSecretKey().getBytes());
	}
	
	public int getAccessExpirationTime() {
		return AccessExpirationTime;
	}

	public int getRefreshExpirationTime() {
		return refreshExpirationTime;
	}

	public String createAccessToken(int userId) {
		Date date = new Date();
		String token = Jwts.builder()
				.setIssuer("planner")
				.setIssuedAt(date)
				.setExpiration(new Date(System.currentTimeMillis() + AccessExpirationTime))
				.claim("userId", userId)
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
		return token;
	}
	
	public String createRefreshToken() {
		Date date = new Date();
		String token = Jwts.builder()
				.setIssuer("planner")
				.setIssuedAt(date)
				.setExpiration(new Date(System.currentTimeMillis() + refreshExpirationTime))
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
		return token;
	}
	
	public String seperateToken(String authorizationToken) {
		if(authorizationToken == null || authorizationToken.isEmpty()) {
			return null;
		}
		
		return authorizationToken.substring("Bearer".length());
	}

	public void verifyToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
		} 
		catch (ExpiredJwtException e) {
			throw new TokenExpiredException("토큰이 만료되었습니다.", e);
		}
		catch(JwtException e) {
			throw new InValidTokenException("잘못된 토큰입니다.", e);
		}
	}

	public int getUserId(String token) throws JsonMappingException, JsonProcessingException {
		Base64.Decoder decoder = Base64.getUrlDecoder();
		ObjectMapper mapper = new ObjectMapper();
		
		String[] split = token.split("\\.");
		String payloadJson = new String(decoder.decode(split[1]));
		
		Map<String, Object> payload = mapper.readValue(payloadJson, Map.class);
		if(payload.isEmpty() || payload.get("userId") == null) {
			throw new InValidTokenException("토큰에 필요한 정보가 없습니다.");
		}
		
		int id = (int) payload.get("userId");
		
		return id;
	}
}
