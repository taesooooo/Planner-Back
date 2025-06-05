package com.planner.planner.Service.Impl;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.planner.planner.Dto.RouteResultDto;
import com.planner.planner.Service.MapRouteService;

@Service
public class MapRouteServiceImpl implements MapRouteService {
	
	private static final Logger log = LoggerFactory.getLogger(MapRouteServiceImpl.class);

	private RestClient restClient = RestClient.create();
	
	@Value("${plnnaer.path-finder.uri}")
	private String pathFinderUri;
	
	@Override
	public List<RouteResultDto> findPath(List<String> list) {
		URI uri = UriComponentsBuilder
			    .fromHttpUrl(pathFinderUri)
			    .queryParam("coordinates", list)
			    .build()
			    .toUri();
		log.info("경로 탐색 요청 시작");
		ResponseEntity<List<RouteResultDto>> response = restClient.get()
					.uri(uri)
					.accept(MediaType.APPLICATION_JSON)
					.retrieve()
					.toEntity(new ParameterizedTypeReference<List<RouteResultDto>>(){});

		return response.getBody();
	}
}
