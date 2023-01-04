package com.planner.planner.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
	public ResponseEntity<Object> newPlanner(HttpServletRequest req, @RequestBody PlannerDto plannerDto) {
		boolean result = plannerService.add(plannerDto);
		if(result) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, ""));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(false, ""));
	}

	@GetMapping
	public ResponseEntity<Object> getPlanner(HttpServletRequest req) {
		try {
			List<PlannerDto> planners = plannerService.findPlannerAll();
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(false, "", planners));
		}
		catch (EmptyResultDataAccessException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(false, "가져오지 못헀습니다."));
		}
	}

	@GetMapping(value="/{plannerId}")
	public ResponseEntity<Object> getPlannersById(HttpServletRequest req, @PathVariable int plannerId) {
		int id = Integer.parseInt(req.getAttribute("userId").toString());
		if(id != plannerId) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseMessage(false, "접근 권한이 없습니다."));
		}

		PlannerDto planner = plannerService.findPlannerByPlannerId(plannerId);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "",planner));
	}
}
