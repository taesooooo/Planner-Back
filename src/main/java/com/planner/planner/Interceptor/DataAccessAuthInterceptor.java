package com.planner.planner.Interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import com.planner.planner.Dto.InvitationDto;
import com.planner.planner.Dto.NotificationDto;
import com.planner.planner.Dto.PlannerDto;
import com.planner.planner.Dto.ReviewDto;
import com.planner.planner.Exception.ForbiddenException;
import com.planner.planner.Exception.NotificationNotFoundException;
import com.planner.planner.Service.InvitationService;
import com.planner.planner.Service.NotificationService;
import com.planner.planner.Service.PlannerService;
import com.planner.planner.Service.ReviewService;
import com.planner.planner.Util.UserIdUtil;

@Component
public class DataAccessAuthInterceptor implements HandlerInterceptor {
	
	private PlannerService plannerService;
	private ReviewService reviewService;
	private InvitationService invitationService;
	private NotificationService notificationService;
	
	
	public DataAccessAuthInterceptor(PlannerService plannerService, ReviewService reviewService, InvitationService invitationService, NotificationService notificationService) {
		this.plannerService = plannerService;
		this.reviewService = reviewService;
		this.invitationService = invitationService;
		this.notificationService = notificationService;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Integer userId = UserIdUtil.getUserId(request);
		Map<String, String> pathVariables = (Map<String,String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		
		if(pathVariables.get("plannerId") != null) {
			int plannerId = Integer.parseInt(pathVariables.get("plannerId"));
			PlannerDto planner = plannerService.findPlannerByPlannerId(userId, plannerId);
			
			return accessCheck(userId, planner.getAccountId());
		}
		else if(pathVariables.get("reviewId") != null) {
			int reviewId = Integer.parseInt(pathVariables.get("reviewId"));
			ReviewDto review = reviewService.findReview(reviewId);
			
			return accessCheck(userId, review.getWriterId());
		}
		else if(pathVariables.get("inviteId") != null) {
			int inviteId = Integer.parseInt(pathVariables.get("inviteId"));
			InvitationDto invitation = invitationService.findById(inviteId);
			if(invitation == null) {
				throw new RuntimeException("데이터가 없습니다.");
			}
			
			return accessCheck(userId, invitation.getAccountId());
		}
		else if(pathVariables.get("notificationId") != null) {
			int notificationId = Integer.parseInt(pathVariables.get("notificationId"));
			NotificationDto notification = notificationService.findById(notificationId);
			
			if(notification == null) {
				throw new NotificationNotFoundException("해당하는 알림을 찾을 수 없습니다.");
			}
			
			return accessCheck(userId, notification.getAccountId());
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
