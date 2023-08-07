package com.planner.planner.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptorProxy implements HandlerInterceptor {
	private PathList pathList;
	private PathList eitherPathList;
	private HandlerInterceptor handlerInterceptor;
	
	public AuthInterceptorProxy(HandlerInterceptor handlerInterceptor) {
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
		if(pathList.isPass(path, method)) {
			return true;
		}
		
		if(!eitherPathList.isPass(path, method)) {			
			if(request.getHeader("Authorization") != null) {
				handlerInterceptor.preHandle(request, response, handler);
			}
			else {
				return true;
			}
		}
		
		return handlerInterceptor.preHandle(request, response, handler);
	}

	public AuthInterceptorProxy addPath(String path, RequestMethod method) {
		this.pathList.addPath(path, method);
		return this;
	}
	
	public AuthInterceptorProxy excludePath(String path, RequestMethod method) {
		this.pathList.excludePath(path, method);
		return this;
	}
	
	public AuthInterceptorProxy eitherAddPath(String path, RequestMethod method) {
		this.eitherPathList.addPath(path, method);
		return this;
	}
}
