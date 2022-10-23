package com.planner.planner.Controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.planner.planner.Dto.SpotDto;
import com.planner.planner.Service.OpenAPIService;
import com.planner.planner.util.ResponseMessage;

@RestController
@RequestMapping(value= "/api/tours")
public class OpenAPIController {
	private static final Logger logger = LoggerFactory.getLogger(OpenAPIController.class);

	private OpenAPIService oService;
	
	public OpenAPIController(OpenAPIService oService) {
		this.oService = oService;
	}

	@GetMapping(value= "/area-codes")
	public ResponseEntity<Object> areaNum() throws Exception {
		ObjectNode data = oService.getAreaNum();
		if(data.get("error") != null) {
			return ResponseEntity.status(data.get("error").get("code").asInt()).body(new ResponseMessage(false, data.get("error").get("message").asText()));
		}

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "", data));
	}

	@GetMapping(value= "/lists-area")
	public ResponseEntity<Object> getAreaList(@RequestParam int areaCode, @RequestParam int contentTypeId, @RequestParam int index) throws Exception {
		List<SpotDto> data = oService.getAreaList(areaCode, contentTypeId, index);
		if(data == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(false, "데이터를 가져오지 못했습니다."));
		}

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "", data));
	}

	@GetMapping(value="/list-location")
	public ResponseEntity<Object> getLocationList(@RequestParam double mapX, @RequestParam double mapY, @RequestParam int radius, @RequestParam int index) throws Exception {
		ObjectNode data = oService.getLocationBasedList(mapX, mapY, radius, index);
		if(data.get("error") != null) {
			return ResponseEntity.status(data.get("error").get("code").asInt()).body(new ResponseMessage(false, data.get("error").get("message").asText()));
		}

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "", data));
	}

	@GetMapping(value= "/lists-keyword")
	public ResponseEntity<Object> getKeyword(@RequestParam int areaCode, @RequestParam int contentTypeId,@RequestParam String keyword, @RequestParam int index) throws Exception {
		ObjectNode data = oService.getKeyword(areaCode, contentTypeId, keyword, index);
		if(data.get("error") != null) {
			return ResponseEntity.status(data.get("error").get("code").asInt()).body(new ResponseMessage(false, data.get("error").get("message").asText()));
		}

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "", data));
	}

	@GetMapping(value= "/lists/{contentId}")
	public ResponseEntity<Object> getDetail(@PathVariable int contentId) throws Exception {
		ObjectNode data = oService.getDetail(contentId);
		if(data.get("error") != null) {
			return ResponseEntity.status(data.get("error").get("code").asInt()).body(new ResponseMessage(false, data.get("error").get("message").asText()));
		}

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "", data));
	}
}
