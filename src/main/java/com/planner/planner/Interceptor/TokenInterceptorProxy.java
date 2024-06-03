package com.planner.planner.Interceptor;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TokenInterceptorProxy implements HandlerInterceptor {
	private PathList pathList;
	private PathList eitherPathList;
	private HandlerInterceptor handlerInterceptor;
	
	public TokenInterceptorProxy(HandlerInterceptor handlerInterceptor) {
		this.pathList = new PathList();
		this.eitherPathList = new PathList();
		this.handlerInterceptor = handlerInterceptor;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String path = request.getServletPath();
		RequestMethod method = RequestMethod.valueOf(request.getMethod());
		
		// false -> 인터셉터 , true -> 통과
		
		boolean isPass = pathList.isPass(path, method);	
		boolean eitherIsPass = eitherPathList.isPass(path, method);
		
		if(isPass && !eitherIsPass) {
			if(request.getHeader("Authorization") != null) {
				handlerInterceptor.preHandle(request, response, handler);
			}
			else {
				return true;
			}
			
		}
		
		if(isPass && eitherIsPass) {
			return true;
		}
		
		return handlerInterceptor.preHandle(request, response, handler);
	}

	public TokenInterceptorProxy addPath(String path, RequestMethod method) {
		this.pathList.addPath(path, method);
		return this;
	}
	
	public TokenInterceptorProxy excludePath(String path, RequestMethod method) {
		this.pathList.excludePath(path, method);
		return this;
	}
	
	public TokenInterceptorProxy eitherAddPath(String path, RequestMethod method) {
		this.eitherPathList.addPath(path, method);
		return this;
	}
}
