package com.planner.planner.Handler;

import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.planner.planner.Exception.AuthCheckFail;
import com.planner.planner.Exception.AuthenticationCodeExpireException;
import com.planner.planner.Exception.DataNotFoundException;
import com.planner.planner.Exception.DuplicateLikeException;
import com.planner.planner.Exception.ForbiddenException;
import com.planner.planner.Exception.NoValidArgumentException;
import com.planner.planner.Exception.NotFoundAuthenticationCodeException;
import com.planner.planner.Exception.NotFoundPasswordResetKeyException;
import com.planner.planner.Exception.NotFoundPlanner;
import com.planner.planner.Exception.NotFoundReviewException;
import com.planner.planner.Exception.NotFoundToken;
import com.planner.planner.Exception.NotFoundUserException;
import com.planner.planner.Util.ResponseMessage;

@RestControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> Exception(Exception e) {
		e.printStackTrace();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage(false, "다시 시도하세요."));
	}

	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<Object> EmptyResult(Exception e) {
		e.printStackTrace();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(false, "데이터를 찾지 못헀습니다"));
	}

	@ExceptionHandler(ForbiddenException.class)
	public ResponseEntity<Object> ForbiddenUser(Exception e) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseMessage(false, e.getMessage()));
	}

	@ExceptionHandler(value = { 
			DataNotFoundException.class, 
			NotFoundUserException.class, 
			NotFoundPlanner.class,
			NotFoundReviewException.class, 
			NotFoundPasswordResetKeyException.class,
			NotFoundAuthenticationCodeException.class })
	public ResponseEntity<Object> notFoundUser(Exception e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(false, e.getMessage()));
	}

	@ExceptionHandler(AuthCheckFail.class)
	public ResponseEntity<Object> authCheckfail(Exception e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseMessage(false, e.getMessage()));
	}

	@ExceptionHandler(NotFoundToken.class)
	public ResponseEntity<Object> notFoundTokenEx(Exception e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseMessage(false, e.getMessage()));
	}

	@ExceptionHandler(DuplicateLikeException.class)
	public ResponseEntity<Object> duplicateLike(Exception e) {
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(false, e.getMessage()));
	}

	@ExceptionHandler(value = {
		NoValidArgumentException.class,
		AuthenticationCodeExpireException.class })
	public ResponseEntity<Object> noValid(Exception e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(false, e.getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> requestBodyValidFail(Exception e, BindingResult result) {
		Map<String, String> errors = new HashMap<String, String>();
		result.getFieldErrors().forEach(error -> {
			errors.put(error.getField(), error.getDefaultMessage());
		});
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(false, errors));
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> requestParamValidFail(Exception e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(false, e.getMessage()));
	}

	@ExceptionHandler(MailException.class)
	public ResponseEntity<Object> mailException(Exception e) {
		e.printStackTrace();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage(false, "메일 전송 실패"));
	}
}
