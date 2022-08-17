package com.planner.planner.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planner.planner.Dto.SpotDto;
import com.planner.planner.Service.SpotService;
import com.planner.planner.util.ResponseMessage;

@RestController
@RequestMapping(value = "api/spot")
public class SpotController {
	private static final Logger logger = LoggerFactory.getLogger(SpotController.class);
	
	@Autowired
	private SpotService spotService;
	
	@GetMapping
	public ResponseEntity<Object> getSpots(HttpServletRequest req) {
		List<SpotDto> spots = spotService.getAllSpot();
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true,"", spots));
	}
	
	@PutMapping(value = "/{spotId}/like")
	public ResponseEntity<Object> spotLike(HttpServletRequest req) {
		
		return null;
	}
	
	@GetMapping
	public ResponseEntity<Object> getSpotLikes(HttpServletRequest req) {
		
		return null;
	}
}
