package com.planner.planner.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.planner.planner.Dto.RouteResultDto;
import com.planner.planner.Service.MapRouteService;
import com.planner.planner.Util.ResponseMessage;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/routes")	
@RequiredArgsConstructor
public class MapRouteController {
	private final MapRouteService mapRouteService;
	
	@GetMapping("/find")
	public ResponseEntity<Object> findPath(@RequestParam("coordinates") List<String> list) {
		List<RouteResultDto> result = mapRouteService.findPath(list);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "", result));
	}
}
