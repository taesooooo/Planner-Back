package com.planner.planner.Interceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.bind.annotation.RequestMethod;

@Deprecated
public class PathList {
	private PathMatcher pathMatcher;
	private Map<String, ArrayList<RequestMethod>> addPathList;
	private Map<String, ArrayList<RequestMethod>> excludePathList;
	
	public PathList() {
		this.pathMatcher = new AntPathMatcher();
		this.addPathList = new HashMap<String, ArrayList<RequestMethod>>();
		this.excludePathList = new HashMap<String, ArrayList<RequestMethod>>();
	}
	
	public Boolean isPass(String path, RequestMethod pathMethod) {
		// 제외 URL 먼저 검사		
		boolean hasExcludePath = excludePathList.entrySet().stream()
				.anyMatch(t -> pathMatch(t.getKey(), t.getValue(), path, pathMethod) );
		
		if(hasExcludePath) {
			return true;
		}
		
		boolean hasAddPath = addPathList.entrySet().stream()
				.anyMatch(t -> pathMatch(t.getKey(), t.getValue(), path, pathMethod) );
		
		if(hasAddPath) {
			return false;
		}

		return true;
	}
	
	private boolean pathMatch(String path,  ArrayList<RequestMethod> pathMethods, String targetPath, RequestMethod targetPathMethod) {
		boolean match = pathMethods.stream().anyMatch(item -> item == null ? true: item.equals(targetPathMethod));
		boolean pathMatch = pathMatcher.match(path, targetPath) && (targetPathMethod == null ? true : pathMethods.isEmpty() ? true : match);
		
		return pathMatch;
	}
	
	public void addPath(String path, RequestMethod pathMethod) {
		ArrayList<RequestMethod> list = addPathList.get(path);
		if(list == null) {
			list = new ArrayList<RequestMethod>();
			list.add(pathMethod);
		}
		else {
			list.add(pathMethod);
		}
		
		this.addPathList.put(path, list);
	}
	
	public void excludePath(String path, RequestMethod pathMethod) {
		ArrayList<RequestMethod> list = excludePathList.get(path);
		if(list == null) {
			list = new ArrayList<RequestMethod>();
			list.add(pathMethod);
		}
		else {
			list.add(pathMethod);
		}
		this.excludePathList.put(path, list);
	}
}
