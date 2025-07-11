package com.planner.planner.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planner.planner.Dto.SpotDetailDto;
import com.planner.planner.Dto.SpotDto;
import com.planner.planner.Dto.SpotLikeDto;
import com.planner.planner.Dto.SpotListDto;
import com.planner.planner.Dto.OpenApi.AreaCodeDto;
import com.planner.planner.Dto.OpenApi.OpenApiDto;
import com.planner.planner.Service.SpotService;
import com.planner.planner.Util.ResponseMessage;
import com.planner.planner.Util.UserIdUtil;

import jakarta.servlet.http.HttpServletRequest;

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
		SpotListDto<AreaCodeDto> area = spotService.getAreaNum();
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "", area));
	}

	@GetMapping(value= "/lists-area")
	public ResponseEntity<Object> spotAreaList(HttpServletRequest req, OpenApiDto openApiDto) throws Exception {
		Integer userId = UserIdUtil.getUserId(req);
		
		SpotListDto<SpotDto> spotList = spotService.getAreaList(userId, openApiDto);

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "", spotList));
	}

//	@GetMapping(value="/list-location")
//	public ResponseEntity<Object> spotLocationList(@RequestParam double mapX, @RequestParam double mapY, @RequestParam int radius, @RequestParam int rowCount, @RequestParam int index) throws Exception {
//		List<SpotDto> spotList = spotService.getLocationBasedList(mapX, mapY, radius, rowCount, index);
//		
//		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "", spotList));
//	}

	@GetMapping(value= "/lists-keyword")
	public ResponseEntity<Object> spotKeyword(HttpServletRequest req, OpenApiDto openApiDto) throws Exception {
		Integer userId = UserIdUtil.getUserId(req);
		
		SpotListDto<SpotDto> spotList = spotService.getKeyword(userId, openApiDto);

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "",spotList));
	}

	@GetMapping(value= "/lists/{contentId}")
	public ResponseEntity<Object> spotDetail(HttpServletRequest req, @PathVariable int contentId) throws Exception {
		Integer userId = UserIdUtil.getUserId(req);
		
		SpotDetailDto info = spotService.getDetail(userId, contentId);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "", info));
	}

	@PostMapping(value = "/likes")
	public ResponseEntity<Object> spotLike(HttpServletRequest req, @RequestBody SpotLikeDto spotLikeDto) throws Exception {
		Integer userId = UserIdUtil.getUserId(req);
		
		boolean result = spotService.addSpotLike(userId, spotLikeDto);
		if(result == false) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseMessage(result,"좋아요 실패"));
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true,"좋아요 성공"));
	}

	@DeleteMapping(value = "/likes/{contentId}")
	public ResponseEntity<Object> spotLikeCancel(HttpServletRequest req, @PathVariable int contentId) throws Exception {
		Integer userId = UserIdUtil.getUserId(req);
		
		boolean result = spotService.removeSpotLike(userId, contentId);
		if(result == false) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseMessage(false, "좋아요 기록이 없습니다."));
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "좋아요 취소 성공"));
	}
}
