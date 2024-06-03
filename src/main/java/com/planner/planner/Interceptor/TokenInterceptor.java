package com.planner.planner.Interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.planner.planner.Exception.TokenNotFoundException;
import com.planner.planner.Exception.TokenCheckFailException;
import com.planner.planner.Util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {
	private static final Logger LOGGER = LoggerFactory.getLogger(TokenInterceptor.class);

	private JwtUtil jwtUtil;
	
	public TokenInterceptor(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 토큰확인
		String bearerToken = request.getHeader("Authorization");
		if(bearerToken == null || bearerToken.isEmpty()) {
			throw new TokenNotFoundException("로그인이 필요합니다.");
		}
		
		String token = jwtUtil.seperateToken(bearerToken);
		if (token != null) {
			try {
				if (jwtUtil.verifyToken(token)) {
					request.setAttribute("userId", jwtUtil.getUserId(token));
					return true;
				}
				else {
					throw new TokenCheckFailException("검증에 실패헀습니다.");
				}
			}
			catch (ExpiredJwtException e) {
				throw new TokenCheckFailException("유효기간이 만료되었습니다.");
			}
		}
		else {
			throw new TokenNotFoundException("로그인이 필요합니다.");
		}
	}

}
