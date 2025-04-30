package com.planner.planner.Service.Impl;

import java.net.URI;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.planner.planner.Dto.RouteDto;
import com.planner.planner.Dto.RouteResultDto;
import com.planner.planner.Service.MapRouteService;

@Service
public class MapRouteServiceImpl implements MapRouteService {
	
	private static final Logger log = LoggerFactory.getLogger(MapRouteServiceImpl.class);

	private RestClient restClient = RestClient.create();
	
	@Value("${plnnaer.path-finder.uri}")
	private String pathFinderUri;
	
	@Override
	public RouteResultDto findPath(RouteDto routeDto) {
		URI uri = UriComponentsBuilder
			    .fromHttpUrl(pathFinderUri)
			    .queryParam("start", routeDto.getStart())
			    .queryParam("end", routeDto.getEnd())
			    .build()
			    .toUri();
		
		ResponseEntity<RouteResultDto> response = restClient.get()
					.uri(uri)
					.accept(MediaType.APPLICATION_JSON)
					.retrieve()
					.toEntity(RouteResultDto.class);

		return response.getBody();
	}

}
