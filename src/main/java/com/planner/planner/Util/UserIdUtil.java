package com.planner.planner.Util;

import jakarta.servlet.http.HttpServletRequest;

public class UserIdUtil {
	public static Integer getUserId(HttpServletRequest req) {
		Object userId = req.getAttribute("userId");
		if(userId == null) {
			return null;
		}
		return Integer.valueOf(req.getAttribute("userId").toString());
	}
}
