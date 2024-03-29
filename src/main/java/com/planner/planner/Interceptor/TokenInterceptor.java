package com.planner.planner.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.planner.planner.Dao.AccountDao;
import com.planner.planner.Dao.RefreshTokenDao;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Exception.NotFoundToken;
import com.planner.planner.Exception.NotFoundUserException;
import com.planner.planner.Exception.TokenCheckFailException;
import com.planner.planner.Util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;

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
			throw new NotFoundToken("로그인이 필요합니다.");
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
			throw new NotFoundToken("로그인이 필요합니다.");
		}
	}

}
