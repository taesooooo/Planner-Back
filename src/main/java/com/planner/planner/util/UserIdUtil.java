package com.planner.planner.util;

import javax.servlet.http.HttpServletRequest;

public class UserIdUtil {
	public static int getUserId(HttpServletRequest req) {
		return Integer.parseInt(req.getAttribute("userId").toString());
	}
}
