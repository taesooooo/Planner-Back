package com.planner.planner.Handler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientResponseException;

import com.planner.planner.Exception.AuthenticationCodeExpireException;
import com.planner.planner.Exception.DuplicateLikeException;
import com.planner.planner.Exception.DuplicatePlanMemberException;
import com.planner.planner.Exception.ForbiddenException;
import com.planner.planner.Exception.InValidTokenException;
import com.planner.planner.Exception.NoValidArgumentException;
import com.planner.planner.Exception.AuthenticationCodeNotFoundException;
import com.planner.planner.Exception.DataNotFoundException;
import com.planner.planner.Exception.PasswordResetKeyNotFoundException;
import com.planner.planner.Exception.PlannerNotFoundException;
import com.planner.planner.Exception.ReviewNotFoundException;
import com.planner.planner.Exception.TokenNotFoundException;
import com.planner.planner.Exception.UserNotFoundException;
import com.planner.planner.Exception.PasswordCheckFailException;
import com.planner.planner.Exception.TokenCheckFailException;
import com.planner.planner.Exception.TokenExpiredException;
import com.planner.planner.Util.ResponseMessage;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

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

	@ExceptionHandler({
		ForbiddenException.class, 
		AccessDeniedException.class})
	public ResponseEntity<Object> ForbiddenUser(Exception e) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseMessage(false, "접근 권한이 없습니다."));
	}

	@ExceptionHandler(value = { 
			DataNotFoundException.class,
			UserNotFoundException.class,
			PlannerNotFoundException.class,
			ReviewNotFoundException.class,
			PasswordResetKeyNotFoundException.class,
			AuthenticationCodeNotFoundException.class })
	public ResponseEntity<Object> notFoundUser(Exception e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(false, e.getMessage()));
	}

	@ExceptionHandler({TokenCheckFailException.class, TokenExpiredException.class, InValidTokenException.class})
	public ResponseEntity<Object> authCheckfail(Exception e) {
		log.error(e.getMessage(), e);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseMessage(false, e.getMessage()));
	}

	@ExceptionHandler(TokenNotFoundException.class)
	public ResponseEntity<Object> notFoundTokenEx(Exception e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseMessage(false, e.getMessage()));
	}

	@ExceptionHandler(value = {
			DuplicateKeyException.class,
			DuplicateLikeException.class,
			DuplicatePlanMemberException.class })
	public ResponseEntity<Object> duplicateLike(Exception e) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseMessage(false, e.getMessage()));
	}

	@ExceptionHandler(value = { 
			NoValidArgumentException.class,
			AuthenticationCodeExpireException.class,
			PasswordCheckFailException.class })
	public ResponseEntity<Object> noValid(Exception e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(false, e.getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> requestBodyValidFail(Exception e, BindingResult result) {
		e.printStackTrace();
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
	
	@ExceptionHandler(BindException.class)
	public ResponseEntity<Object> requestBindFail(Exception e, BindingResult result) {
		Map<String, String> errors = new HashMap<String, String>();
		result.getFieldErrors().forEach(error -> {
			errors.put(error.getField(), "잘못된 형식으로 요청했습니다. 다시 확인해주세요.");
		});
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(false, errors));
	}

	@ExceptionHandler(MailException.class)
	public ResponseEntity<Object> mailException(Exception e) {
		e.printStackTrace();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage(false, "메일 전송 실패"));
	}
	
	@ExceptionHandler(RestClientResponseException.class)
	public ResponseEntity<Object> RestClientResponseException(RestClientResponseException e){
		log.error("내부 API 서버 호출 실패 ", e.getMessage(), e);
		e.printStackTrace();
		int errorCode = e.getStatusCode().value();
		
		if(e.getStatusCode().is4xxClientError()) {
			return ResponseEntity.status(errorCode).body(new ResponseMessage(false, "잘못된 요청입니다. 다시 확인해주시요."));
		}

		return ResponseEntity.status(errorCode).body(new ResponseMessage(false, "문제가 발생했습니다. 다시 시도하십시오. 문제가 계속된다면 문의하여 주시기 바랍니다."));			
	}
}
