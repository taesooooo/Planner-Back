package com.planner.planner.Interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import com.planner.planner.Dto.PlannerDto;
import com.planner.planner.Dto.ReviewDto;
import com.planner.planner.Exception.ForbiddenException;
import com.planner.planner.Service.PlannerService;
import com.planner.planner.Service.ReviewService;
import com.planner.planner.Util.UserIdUtil;

@Component
public class DataAccessAuthInterceptor implements HandlerInterceptor {
	
	private PlannerService plannerService;
	private ReviewService reviewService;
	
	
	public DataAccessAuthInterceptor(PlannerService plannerService, ReviewService reviewService) {
		this.plannerService = plannerService;
		this.reviewService = reviewService;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		int userId = UserIdUtil.getUserId(request);
		Map<String, String> pathVariables = (Map<String,String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		
		if(pathVariables.get("plannerId") != null) {
			int plannerId = Integer.parseInt(pathVariables.get("plannerId"));
			PlannerDto planner = plannerService.findPlannerByPlannerId(plannerId);
			
			return accessCheck(userId, planner.getAccountId());
		}
		else if(pathVariables.get("reviewId") != null) {
			int reviewId = Integer.parseInt(pathVariables.get("reviewId"));
			ReviewDto review = reviewService.findReview(reviewId);
			
			return accessCheck(userId, review.getWriterId());
		}
		
		return false;
	}

	private boolean accessCheck(int userId, int dataId) throws ForbiddenException {
		if(userId != dataId) {
			throw new ForbiddenException("접근 권한이 없습니다.");
		}
		else {
			return true;
		}
	}

}
