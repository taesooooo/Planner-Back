package com.planner.planner.Controller;

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

import com.planner.planner.Dto.PlanLocationRouteDto;
import com.planner.planner.Service.PlanLocationRouteService;
import com.planner.planner.Util.ResponseMessage;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/api/planners/{plannerId}/plans/{planId}")
public class PlanLocationRouteController {
	
	private static final Logger log = LoggerFactory.getLogger(PlanLocationRouteController.class);

	private final PlanLocationRouteService planLocationRouteService;
	
	@GetMapping(value="/location-routes/{locationRouteId}")
	public ResponseEntity<Object> locationRoutes(@PathVariable int planId, @PathVariable int locationRouteId) {
		PlanLocationRouteDto locationRouteDto = planLocationRouteService.findPlanLocationRouteById(locationRouteId);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "", locationRouteDto));
	}
	
	@PostMapping(value="/location-routes")
	public ResponseEntity<Object> newLocarionRoutes(@PathVariable int planId, @RequestBody PlanLocationRouteDto locationRouteDto) {
		int createId = planLocationRouteService.createPlanLocationRoute(locationRouteDto);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "", createId));
	}
	
	@PatchMapping(value="/location-routes/{locationRouteId}")
	public ResponseEntity<Object> updateLocationRoutes(@PathVariable int planId, @PathVariable int locationRouteId) {
		PlanLocationRouteDto locationRouteDto = planLocationRouteService.findPlanLocationRouteById(locationRouteId);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, locationRouteDto));
	}
	
	@DeleteMapping(value="/location-routes/{locationRouteId}")
	public ResponseEntity<Object> deleteLocationRoutes(@PathVariable int planId, @PathVariable int locationRouteId) {
		planLocationRouteService.deletePlanLocationRouteById(locationRouteId);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, ""));
	}
}
