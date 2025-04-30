package com.planner.planner.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planner.planner.Dto.RouteDto;
import com.planner.planner.Dto.RouteResultDto;
import com.planner.planner.Service.MapRouteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/routes")	
@RequiredArgsConstructor
public class MapRouteController {
	private final MapRouteService mapRouteService;
	
	@GetMapping
	public ResponseEntity<Object> findPath(RouteDto coordinates) {
		RouteResultDto result = mapRouteService.findPath(coordinates);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}
