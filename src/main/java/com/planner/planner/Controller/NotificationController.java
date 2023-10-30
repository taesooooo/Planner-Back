package com.planner.planner.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.planner.planner.Service.NotificationService;
import com.planner.planner.Util.ResponseMessage;

@Controller
@RequestMapping(value = "/api/notifications")
public class NotificationController {
	private NotificationService notificationService;
	
	public NotificationController(NotificationService notificationService) {
		this.notificationService = notificationService;
	}
	
	@PostMapping(value ="/{notificationId}/read")
	public ResponseEntity<Object> readNotification(HttpServletRequest req, @PathVariable int notificationId) throws Exception {
		notificationService.notificationRead(notificationId);

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, ""));
	}
	
	@DeleteMapping(value ="/{notificationId}")
	public ResponseEntity<Object> deleteNotification(HttpServletRequest req, @PathVariable int notificationId) throws Exception {
		notificationService.deleteNotification(notificationId);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, ""));
	}
}
