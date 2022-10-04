package com.planner.planner.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.LikeDto;
import com.planner.planner.Dto.SpotLikeStateDto;
import com.planner.planner.Entity.Account;
import com.planner.planner.Exception.NotFoundToken;
import com.planner.planner.Service.AccountService;
import com.planner.planner.Service.SpotService;
import com.planner.planner.util.FileStore;
import com.planner.planner.util.JwtUtil;
import com.planner.planner.util.ResponseMessage;

@RestController
@RequestMapping(value="/api/users")
public class AccountController {
	private final static Logger logger = LoggerFactory.getLogger(AccountController.class);

	private AccountService accountService;
	
	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@GetMapping(value="/{accountId}")
	public ResponseEntity<Object> account(HttpServletRequest req, @PathVariable int accountId) throws Exception {
		int id = Integer.parseInt(req.getAttribute("userId").toString());
		if (id != accountId) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseMessage(false, "접근 권한이 없습니다."));
		}
		
		AccountDto user = accountService.findById(accountId);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "", user));
	}
	
	@PatchMapping(value="/{accountId}")
	public ResponseEntity<Object> accountUpdate(@PathVariable int accountId, @RequestBody AccountDto accountDto) throws Exception {
		if(accountService.accountUpdate(accountDto)) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "계정 정보 변경을 성공헀습니다."));
		}
		
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseMessage(true, "계정 정보 변경을 실패했습니다."));
	}
	
	@PatchMapping(value="/images/{accountId}")
	public ResponseEntity<Object> accountImageUpdate(@PathVariable int accountId, @RequestPart(value="image") MultipartFile image) throws Exception {
		if(accountService.accountImageUpdate(accountId, image)) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "계정 이미지 변경을 성공헀습니다."));
		}
		else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseMessage(false, "계정 이미지 변경을 실패헀습니다."));
		}
	}
	
	@GetMapping(value="/likes/{accountId}")
	public ResponseEntity<Object> likes(HttpServletRequest req, @PathVariable int accountId) {
		int id = Integer.parseInt(req.getAttribute("userId").toString());
		if (id != accountId) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseMessage(false, "접근 권한이 없습니다."));
		}
		
		LikeDto likes =  accountService.allLikes(accountId);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "",likes));
	}
	
//	@GetMapping(value ="/likes/{accountId}/check")
//	public ResponseEntity<Object> spotLikeState(HttpServletRequest req, @PathVariable int accountId, @RequestBody List<Integer> contentIds) {
//
//		List<SpotLikeStateDto> stateList = accountService.spotLikeStateCheck(accountId, contentIds);
//		
//		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "", stateList));
//	}
	
}
