package com.planner.planner.Interceptor;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import com.planner.planner.Dao.FileUploadDao;
import com.planner.planner.Dao.SpotDao;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.FileInfoDto;
import com.planner.planner.Dto.InvitationDto;
import com.planner.planner.Dto.NotificationDto;
import com.planner.planner.Dto.PlannerDto;
import com.planner.planner.Dto.ReviewDto;
import com.planner.planner.Dto.SpotLikeDto;
import com.planner.planner.Exception.ForbiddenException;
import com.planner.planner.Exception.DataNotFoundException;
import com.planner.planner.Exception.NotificationNotFoundException;
import com.planner.planner.Service.AccountService;
import com.planner.planner.Service.InvitationService;
import com.planner.planner.Service.NotificationService;
import com.planner.planner.Service.PlannerService;
import com.planner.planner.Service.ReviewService;
import com.planner.planner.Util.UserIdUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class DataAccessAuthInterceptor implements HandlerInterceptor {
	
	private AccountService accountService;
	private SpotDao spotDao;
	private PlannerService plannerService;
	private ReviewService reviewService;
	private InvitationService invitationService;
	private NotificationService notificationService;
	private FileUploadDao fileUploadDao;
	
	
	public DataAccessAuthInterceptor(AccountService accountService, SpotDao spotDao, PlannerService plannerService, ReviewService reviewService, InvitationService invitationService, NotificationService notificationService, FileUploadDao fileUploadDao) {
		this.accountService = accountService;
		this.spotDao = spotDao;
		this.plannerService = plannerService;
		this.reviewService = reviewService;
		this.invitationService = invitationService;
		this.notificationService = notificationService;
		this.fileUploadDao = fileUploadDao;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Integer userId = UserIdUtil.getUserId(request);
		Map<String, String> pathVariables = (Map<String,String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		if(pathVariables.get("accountId") != null) {
			int accountId = Integer.parseInt(pathVariables.get("accountId"));
			AccountDto account = accountService.findById(accountId);
			
			if(account == null) {
				throw new DataNotFoundException("해당하는 계정 정보를 찾지 못했습니다.");
			}
			
			return accessCheck(userId, account.getAccountId());
		}
		else if(pathVariables.get("contentId") != null) {
			int contentId = Integer.parseInt(pathVariables.get("contentId"));
			SpotLikeDto likeDto = spotDao.findSpotLikeByContentId(userId, contentId);
			
			if(likeDto == null) {
				throw new DataNotFoundException("해당하는 좋아요 정보를 찾지 못했습니다.");
			}
			
			return accessCheck(userId, likeDto.getAccountId());
		}
		else if(pathVariables.get("plannerId") != null) {
			int plannerId = Integer.parseInt(pathVariables.get("plannerId"));
			PlannerDto planner = plannerService.findPlannerByPlannerId(userId, plannerId);
			
			if(planner == null) {
				throw new DataNotFoundException("해당하는 플래너를 찾지 못했습니다.");
			}
			
			return accessCheck(userId, planner.getAccountId());
		}
		else if(pathVariables.get("reviewId") != null) {
			int reviewId = Integer.parseInt(pathVariables.get("reviewId"));
			ReviewDto review = reviewService.findReview(reviewId);
			
			if(review == null) {
				throw new DataNotFoundException("해당하는 리뷰를 찾지 못했습니다.");
			}
			
			return accessCheck(userId, review.getWriterId());
		}
		else if(pathVariables.get("inviteId") != null) {
			int inviteId = Integer.parseInt(pathVariables.get("inviteId"));
			InvitationDto invitation = invitationService.findById(inviteId);
			if(invitation == null) {
				throw new DataNotFoundException("초대 정보를 찾지 못했습니다.");
			}
			
			return accessCheck(userId, invitation.getAccountId());
		}
		else if(pathVariables.get("notificationId") != null) {
			int notificationId = Integer.parseInt(pathVariables.get("notificationId"));
			NotificationDto notification = notificationService.findById(notificationId);
			
			if(notification == null) {
				throw new NotificationNotFoundException("해당하는 알림 정보를 찾지 못했습니다.");
			}
			
			return accessCheck(userId, notification.getAccountId());
		}
		else if(pathVariables.get("fileName") != null) {
			String fileName = pathVariables.get("fileName");
			FileInfoDto fileInfoDto = fileUploadDao.getFileInfo(fileName);
			
			if(fileInfoDto == null) {
				throw new DataNotFoundException("해당하는 파일 정보를 찾지 못했습니다.");
			}
			
			return accessCheck(userId, fileInfoDto.getFileWriterId());
		}
		else {
			throw new ForbiddenException("접근 권한이 없습니다.");
		}
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
