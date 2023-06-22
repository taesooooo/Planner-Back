package com.planner.planner.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.planner.planner.Common.Page;
import com.planner.planner.Dto.CommonRequestParamDto;
import com.planner.planner.Dto.ReviewDto;
import com.planner.planner.Service.ReviewService;
import com.planner.planner.util.ResponseMessage;

@RestController
@RequestMapping(value="/api/reviews")
public class ReviewController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReviewController.class);
	
	private ReviewService reviewService;
	
	public ReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}
	
	@PostMapping
	public ResponseEntity<Object> writeReview(HttpServletRequest req, @RequestBody @Valid ReviewDto reviewDto) throws Exception {
		int reviewId = reviewService.insertReview(Integer.parseInt(req.getAttribute("userId").toString()), reviewDto);

		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(true, "", reviewId));
	}
	
	@GetMapping
	public ResponseEntity<Object> reviews(@RequestBody CommonRequestParamDto commonRequestParamDto) throws Exception {
		Page<ReviewDto> reviews = reviewService.findAllReview(commonRequestParamDto);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "",reviews));
	}
	
	@GetMapping(value="/{reviewId}")
	public ResponseEntity<Object> review(@PathVariable int reviewId) {
		ReviewDto review = reviewService.findReview(reviewId);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "",review));
	}
	
	@PatchMapping(value="/{reviewId}")
	public ResponseEntity<Object> updateReivew(HttpServletRequest req, @PathVariable int reviewId, @RequestBody @Valid ReviewDto reviewDto) {
		ReviewDto review = reviewService.findReview(reviewId);
		if(review == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(false,"게시글이 존재하지 않습니다."));
		}
		
		if(Integer.parseInt(req.getAttribute("userId").toString()) != review.getWriterId()) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseMessage(false,"접근 권한이 없습니다."));
		}
		
		reviewService.updateReview(reviewDto);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, ""));
	}
	
	@DeleteMapping(value="/{reviewId}")
	public ResponseEntity<Object> deleteReview(HttpServletRequest req, @PathVariable int reviewId) {
		ReviewDto review = reviewService.findReview(reviewId);
		if(review == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(false,"게시글이 존재하지 않습니다."));
		}
		
		if(Integer.parseInt(req.getAttribute("userId").toString()) != review.getWriterId()) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseMessage(false,"접근 권한이 없습니다."));
		}
		reviewService.deleteReview(reviewId);

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, ""));
	}
}
