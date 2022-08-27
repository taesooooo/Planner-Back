package com.planner.planner.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planner.planner.Dto.SpotDto;
import com.planner.planner.Entity.Account;
import com.planner.planner.Service.SpotService;
import com.planner.planner.util.ResponseMessage;

@RestController
@RequestMapping(value = "api/spots")
public class SpotController {
	private static final Logger logger = LoggerFactory.getLogger(SpotController.class);
	
	@Autowired
	private SpotService spotService;
	
	@GetMapping
	public ResponseEntity<Object> spots(HttpServletRequest req) {
		List<SpotDto> spots = spotService.getAllSpot();
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true,"", spots));
	}
	
	@PostMapping(value = "/{spotId}/likes")
	public ResponseEntity<Object> spotLike(HttpServletRequest req, @PathVariable int spotId) {
		int id = Integer.parseInt(req.getAttribute("userId").toString());
		boolean result = spotService.spotLike(id, spotId);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(result,""));
	}
	
	@DeleteMapping(value = "/{spotId}/likes")
	public ResponseEntity<Object> spotCancelLike(HttpServletRequest req, @PathVariable int spotId) {
		int id = Integer.parseInt(req.getAttribute("userId").toString());
		boolean result = spotService.spotLike(id, spotId);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(result,""));
	}
	
}
