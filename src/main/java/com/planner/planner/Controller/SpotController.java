package com.planner.planner.Controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planner.planner.Service.SpotService;
import com.planner.planner.util.ResponseMessage;

@RestController
@RequestMapping(value = "api/spots")
public class SpotController {
	private static final Logger logger = LoggerFactory.getLogger(SpotController.class);

	private SpotService spotService;

	public SpotController(SpotService spotService) {
		this.spotService = spotService;
	}

	@PostMapping(value = "/likes/{contentId}")
	public ResponseEntity<Object> spotLike(HttpServletRequest req, @PathVariable int contentId) {
		int id = Integer.parseInt(req.getAttribute("userId").toString());
		boolean result = spotService.spotLike(id, contentId);

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(result,""));
	}

	@DeleteMapping(value = "/likes/{contentId}")
	public ResponseEntity<Object> spotCancelLike(HttpServletRequest req, @PathVariable int contentId) {
		int id = Integer.parseInt(req.getAttribute("userId").toString());
		boolean result = spotService.spotLikeCancel(id, contentId);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(result,""));
	}

}
