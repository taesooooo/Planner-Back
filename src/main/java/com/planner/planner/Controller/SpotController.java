package com.planner.planner.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.planner.planner.Dto.SpotDto;
import com.planner.planner.Dto.OpenApi.AreaCodeDto;
import com.planner.planner.Dto.OpenApi.DetailCommonDto;
import com.planner.planner.Service.SpotService;
import com.planner.planner.util.ResponseMessage;

@RestController
@RequestMapping(value = "api/spots")
public class SpotController {
	private static final Logger logger = LoggerFactory.getLogger(SpotController.class);

	private SpotService spotService;

	public SpotController(SpotService spotService) {
		this.spotService = spotService;
	}
	
	@GetMapping(value= "/area-codes")
	public ResponseEntity<Object> spotAreaNum() throws Exception {
		List<AreaCodeDto> area = spotService.getAreaNum();
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "", area));
	}

	@GetMapping(value= "/lists-area")
	public ResponseEntity<Object> spotAreaList(@RequestParam int areaCode, @RequestParam int contentTypeId, @RequestParam int index) throws Exception {
		List<SpotDto> spotList = spotService.getAreaList(areaCode, contentTypeId, index);

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "", spotList));
	}

//	@GetMapping(value="/list-location")
//	public ResponseEntity<Object> spotLocationList(@RequestParam double mapX, @RequestParam double mapY, @RequestParam int radius, @RequestParam int index) throws Exception {
//		List<SpotDto> spotList = spotService.getLocationBasedList(mapX, mapY, radius, index);
//		
//		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "", spotList));
//	}

	@GetMapping(value= "/lists-keyword")
	public ResponseEntity<Object> spotKeyword(@RequestParam int areaCode, @RequestParam int contentTypeId,@RequestParam String keyword, @RequestParam int index) throws Exception {
		List<SpotDto> spotList = spotService.getKeyword(areaCode, contentTypeId, keyword, index);

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "",spotList));
	}

	@GetMapping(value= "/lists/{contentId}")
	public ResponseEntity<Object> spotDetail(@PathVariable int contentId) throws Exception {
		DetailCommonDto info = spotService.getDetail(contentId);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "", info));
	}

	@PostMapping(value = "/likes/{contentId}")
	public ResponseEntity<Object> spotLike(HttpServletRequest req, @PathVariable int contentId) {
		int id = Integer.parseInt(req.getAttribute("userId").toString());
		boolean result = spotService.spotLike(id, contentId);
		if(result == false) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseMessage(result,"좋아요 실패"));
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(result,"좋아요 성공"));
	}

	@DeleteMapping(value = "/likes/{contentId}")
	public ResponseEntity<Object> spotLikeCancel(HttpServletRequest req, @PathVariable int contentId) {
		int id = Integer.parseInt(req.getAttribute("userId").toString());
		boolean result = spotService.spotLikeCancel(id, contentId);
		if(result == false) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseMessage(result,"좋아요 취소 실패"));
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(result,"좋아요 취소 성공"));
	}

}
