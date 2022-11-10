package com.planner.planner.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planner.planner.Dto.ReviewDto;
import com.planner.planner.Service.ReviewService;
import com.planner.planner.util.ResponseMessage;

@RestController
@RequestMapping(value="/api")
public class ReviewController {
	private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);
	
	private ReviewService reviewService;
	
	public ReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}
	
	@PostMapping(value="/reviews")
	public ResponseEntity<Object> writeReview(@RequestBody ReviewDto reviewDto) {
		boolean result = reviewService.insertReview(reviewDto);

		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(result, ""));
	}
	
	@GetMapping(value="/reviews")
	public ResponseEntity<Object> getReviews(@RequestBody int index) {
		List<ReviewDto> reviews = reviewService.findAllReview(0);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "",reviews));
	}
	
	@GetMapping(value="/reviews/{reviewId}")
	public ResponseEntity<Object> getReview(@PathVariable int reviewId) {
		ReviewDto review = reviewService.findReview(reviewId);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "",review));
	}
	
	@PatchMapping(value="/reviews/{reviewId}")
	public ResponseEntity<Object> updateReivew(HttpServletRequest req, @PathVariable int reviewId, @RequestBody ReviewDto reviewDto) {
		ReviewDto review = reviewService.findReview(reviewId);
		if(review == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(false,"게시글이 존재하지 않습니다."));
		}
		
		if(Integer.parseInt(req.getAttribute("userId").toString()) != review.getWriter()) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseMessage(false,"접근 권한이 없습니다."));
		}
		boolean result = reviewService.updateReview(reviewDto);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseMessage(result, ""));
	}
	
	@DeleteMapping(value="/reviews/{reviewId}")
	public ResponseEntity<Object> deleteReview(HttpServletRequest req, @PathVariable int reviewId) {
		ReviewDto review = reviewService.findReview(reviewId);
		if(review == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(false,"게시글이 존재하지 않습니다."));
		}
		
		if(Integer.parseInt(req.getAttribute("userId").toString()) != review.getWriter()) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseMessage(false,"접근 권한이 없습니다."));
		}
		boolean result = reviewService.deleteReview(reviewId);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseMessage(result, ""));
	}
}
