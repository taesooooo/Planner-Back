package com.planner.planner.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;

public class RequestMethodProxyInterceptor implements HandlerInterceptor {
	private PathList pathList;
	private HandlerInterceptor handlerInterceptor;
	
	public RequestMethodProxyInterceptor(HandlerInterceptor handlerInterceptor) {
		this.pathList = new PathList();
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
		else {
			return handlerInterceptor.preHandle(request, response, handler);
		}
	}
	
	public RequestMethodProxyInterceptor addPath(String path, RequestMethod method) {
		this.pathList.addPath(path, method);
		return this;
	}
	
	public RequestMethodProxyInterceptor excludePath(String path, RequestMethod method) {
		this.pathList.excludePath(path, method);
		return this;
	}
}
