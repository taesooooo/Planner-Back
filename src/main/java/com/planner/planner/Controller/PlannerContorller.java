package com.planner.planner.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planner.planner.Dto.PlannerDto;
import com.planner.planner.Service.PlannerService;
import com.planner.planner.util.ResponseMessage;

@RestController
@RequestMapping(value="/api/planners")
public class PlannerContorller {
	private static final Logger logger = LoggerFactory.getLogger(PlannerContorller.class);
	@Autowired
	private PlannerService plannerService;
	
	@PostMapping
	public ResponseEntity<Object> createPlanner(HttpServletRequest req, @RequestBody PlannerDto plannerDto) {
		logger.info(plannerDto.toString());
		HttpSession session = req.getSession(false);
		if(session == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseMessage(false, "로그인이 필요합니다."));
		}
		
		boolean result = plannerService.create(plannerDto);
		if(result) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, ""));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(false, ""));
	}
	
	@GetMapping(value="/{plannerId}")
	public ResponseEntity<Object> readPlanner(HttpServletRequest req, @PathVariable int plannerId) {
		HttpSession session = req.getSession(false);
		if(session == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseMessage(false, "로그인이 필요합니다."));
		}
		
		PlannerDto planner = plannerService.read(plannerId);
		if(planner == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(false, "일정을 가져오지 못헀습니다."));
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "",planner));
	}
	
	@PutMapping(value="/{plannerId}")
	public ResponseEntity<Object> updatePlanner(HttpServletRequest req, PlannerDto plannerDto) {
		HttpSession session = req.getSession(false);
		if(session == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseMessage(false, "로그인이 필요합니다."));
		}
		
		if(plannerService.update(plannerDto)) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "",plannerDto));
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(false, "변경하지 못헀습니다."));
	}
	
	@DeleteMapping(value="/{plannerId}")
	public ResponseEntity<Object> deletePlanner(HttpServletRequest req, @PathVariable int plannerId) {
		HttpSession session = req.getSession(false);
		if(session == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseMessage(false, "로그인이 필요합니다."));
		}
		
		if(plannerService.delete(plannerId)) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "플래너 삭제가 완료되었습니다."));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(false, "삭제를 실패했습니다."));
	}
}
