package com.planner.planner.Handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.planner.planner.Exception.AuthCheckFail;
import com.planner.planner.Exception.NotFoundToken;
import com.planner.planner.util.ResponseMessage;

@RestControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> Exception(Exception e) {
		e.printStackTrace();
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseMessage(false, "다시 시도하세요."));
	}

	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<Object> EmptyResult(Exception e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(false, "데이터를 찾지 못헀습니다"));
	}

	@ExceptionHandler(AuthCheckFail.class)
	public ResponseEntity<Object> authCheckfail(Exception e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseMessage(false, e.getMessage()));
	}

	@ExceptionHandler(NotFoundToken.class)
	public ResponseEntity<Object> notFoundTokenEx(Exception e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseMessage(false, e.getMessage()));
	}
}