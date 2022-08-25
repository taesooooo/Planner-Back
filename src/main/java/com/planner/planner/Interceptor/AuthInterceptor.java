package com.planner.planner.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.planner.planner.util.JwtUtil;

public class AuthInterceptor implements HandlerInterceptor {

	@Autowired
	private JwtUtil jwtUtil;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 토큰확인
		String token = jwtUtil.getToken(request);
		if(jwtUtil.verifyToken(token)) {
			return true;
		}
		else {
			// 에러 발생 시키기
			return false;
		}
	}
	
}
