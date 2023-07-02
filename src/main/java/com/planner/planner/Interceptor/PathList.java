package com.planner.planner.Interceptor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.bind.annotation.RequestMethod;

public class PathList {
	private PathMatcher pathMatcher;
	private Map<String, RequestMethod> addPathList;
	private Map<String, RequestMethod> excludePathList;
	
	public PathList() {
		this.pathMatcher = new AntPathMatcher();
		this.addPathList = new HashMap<String, RequestMethod>();
		this.excludePathList = new HashMap<String, RequestMethod>();
	}
	
	public Boolean isPass(String path, RequestMethod pathMethod) {		
		boolean hasExcludePath = excludePathList.entrySet().stream()
				.anyMatch(t -> pathMatch(t.getKey(), t.getValue(), path, pathMethod) );
		
		boolean hasAddPath = addPathList.entrySet().stream()
				.anyMatch(t -> pathMatch(t.getKey(), t.getValue(), path, pathMethod) );
		
		if(hasExcludePath) {
			return true;
		}
		
		if(hasAddPath) {
			return false;
		}
		else 
		{
			return false;
		}
	}
	
	private boolean pathMatch(String path, RequestMethod pathMethod, String targetPath, RequestMethod targetPathMethod) {
		return pathMatcher.match(path, targetPath) && (targetPathMethod == null ? true : pathMethod == null ? true : pathMethod.equals(targetPathMethod));
	}
	
	public void addPath(String path, RequestMethod pathMethod) {
		this.addPathList.put(path, pathMethod);
	}
	
	public void excludePath(String path, RequestMethod pathMethod) {
		this.excludePathList.put(path, pathMethod);
	}
}
