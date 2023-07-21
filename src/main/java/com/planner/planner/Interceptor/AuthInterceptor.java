package com.planner.planner.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.planner.planner.Exception.AuthCheckFail;
import com.planner.planner.Exception.NotFoundToken;
import com.planner.planner.Util.JwtUtil;

public class AuthInterceptor implements HandlerInterceptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthInterceptor.class);

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 토큰확인
		String token = jwtUtil.seperateToken(request.getHeader("Authorization"));
		if (token != null) {
			if (jwtUtil.verifyToken(token)) {
				request.setAttribute("userId", jwtUtil.getUserId(token));
				return true;
			} else {
				throw new AuthCheckFail("검증에 실패헀습니다.");
			}
		}
		else {
			throw new NotFoundToken("로그인이 필요합니다.");
		}
	}

}
