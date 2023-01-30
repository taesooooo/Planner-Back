package com.planner.planner.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planner.planner.Dto.PlanDto;
import com.planner.planner.Dto.PlanLocationDto;
import com.planner.planner.Dto.PlanMemberDto;
import com.planner.planner.Dto.PlannerDto;
import com.planner.planner.Service.PlannerService;
import com.planner.planner.util.ResponseMessage;

@RestController
@RequestMapping(value="/api/planners")
public class PlannerContorller {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlannerContorller.class);

	private PlannerService plannerService;
	
	public PlannerContorller(PlannerService plannerService) {
		this.plannerService = plannerService;
	}

	@PostMapping
	public ResponseEntity<Object> newPlanner(HttpServletRequest req, @RequestBody PlannerDto plannerDto) throws Exception {
		plannerService.newPlanner(plannerDto);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, ""));
	}

	@GetMapping
	public ResponseEntity<Object> plannerList(HttpServletRequest req) throws Exception {
		List<PlannerDto> planners = plannerService.findPlannerAll();
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "", planners));
	}

	@GetMapping(value="/{plannerId}")
	public ResponseEntity<Object> findPlannersById(HttpServletRequest req, @PathVariable int plannerId) throws Exception {
		int id = Integer.parseInt(req.getAttribute("userId").toString());
		List<PlanMemberDto> planMembers = plannerService.findMembersByPlannerId(plannerId);
	
		boolean match = planMembers.stream().anyMatch((planMember) -> planMember.getAccountId() == id);
		if(!match) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseMessage(false, "접근 권한이 없습니다."));
		}

		PlannerDto planner = plannerService.findPlannerByPlannerId(plannerId);

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "",planner));
	}
	
	@PatchMapping(value="/{plannerId}")
	public ResponseEntity<Object> modifyPlanner(HttpServletRequest req, @PathVariable int plannerId, @RequestBody PlannerDto plannerDto) throws Exception {
		int id = Integer.parseInt(req.getAttribute("userId").toString());
		PlannerDto planner = plannerService.findPlannerByPlannerId(plannerId);
		
		if(planner.getAccountId() != id) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseMessage(false, "접근 권한이 없습니다."));
		}
		
		plannerService.modifyPlanner(plannerDto);

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, ""));
	}
	
	@DeleteMapping(value="/{plannerId}")
	public ResponseEntity<Object> deletePlanner(HttpServletRequest req, @PathVariable int plannerId) throws Exception {
		int id = Integer.parseInt(req.getAttribute("userId").toString());
		PlannerDto planner = plannerService.findPlannerByPlannerId(plannerId);
		
		if(planner.getAccountId() != id) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseMessage(false, "접근 권한이 없습니다."));
		}
		
		plannerService.deletePlanner(plannerId);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, ""));
	}
	
	@PostMapping(value="/{plannerId}/plans")
	public ResponseEntity<Object> newPlan(HttpServletRequest req, @PathVariable int plannerId, @RequestBody PlanDto planDto) throws Exception {
		int id = Integer.parseInt(req.getAttribute("userId").toString());
		PlannerDto planner = plannerService.findPlannerByPlannerId(plannerId);
		
		if(planner.getAccountId() != id) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseMessage(false, "접근 권한이 없습니다."));
		}
		
		int planId = plannerService.newPlan(planDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(true, "", planId));
	}
	
	@DeleteMapping(value="/{plannerId}/plans/{planId}")
	public ResponseEntity<Object> deletePlan(HttpServletRequest req, @PathVariable int plannerId, @PathVariable int planId) throws Exception {
		int id = Integer.parseInt(req.getAttribute("userId").toString());
		PlannerDto planner = plannerService.findPlannerByPlannerId(plannerId);
		
		if(planner.getAccountId() != id) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseMessage(false, "접근 권한이 없습니다."));
		}
		
		plannerService.deletePlan(plannerId, planId);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, ""));
	}
	
	@PostMapping(value="/{plannerId}/plans/{planId}/plan-locations")
	public ResponseEntity<Object> newPlanLocation(HttpServletRequest req, @PathVariable int plannerId, @RequestBody PlanLocationDto planLocationDto) throws Exception {
		int id = Integer.parseInt(req.getAttribute("userId").toString());
		PlannerDto planner = plannerService.findPlannerByPlannerId(plannerId);
		
		if(planner.getAccountId() != id) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseMessage(false, "접근 권한이 없습니다."));
		}
		
		int planLocationId = plannerService.newPlanLocation(planLocationDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(true, "", planLocationId));
	}
	
	@DeleteMapping(value="/{plannerId}/plans/{planId}/plan-locations/{planLocationId}")
	public ResponseEntity<Object> deletePlanLocation(HttpServletRequest req, @PathVariable int plannerId, @PathVariable int planId, @PathVariable int planLocationId) throws Exception {
		int id = Integer.parseInt(req.getAttribute("userId").toString());
		PlannerDto planner = plannerService.findPlannerByPlannerId(plannerId);
		
		if(planner.getAccountId() != id) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseMessage(false, "접근 권한이 없습니다."));
		}
		
		plannerService.deletePlanLocation(planId, planLocationId);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, ""));
	}
	
 }
