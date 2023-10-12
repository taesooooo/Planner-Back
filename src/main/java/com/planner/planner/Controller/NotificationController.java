package com.planner.planner.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.planner.planner.Dto.NotificationDto;
import com.planner.planner.Service.NotificationService;
import com.planner.planner.Util.ResponseMessage;
import com.planner.planner.Util.UserIdUtil;

@Controller
@RequestMapping(value = "/api/notifications")
public class NotificationController {
	private NotificationService notificationService;
	
	public NotificationController(NotificationService notificationService) {
		this.notificationService = notificationService;
	}
	
	@PostMapping(value ="/{notificationId}/read")
	public ResponseEntity<Object> readNotification(HttpServletRequest req, @PathVariable int notificationId) throws Exception {
		Integer userId = UserIdUtil.getUserId(req);
		
		notificationService.notificationRead(userId, notificationId);

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, ""));
	}
}
