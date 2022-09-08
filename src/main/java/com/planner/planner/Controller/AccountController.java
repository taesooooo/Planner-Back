package com.planner.planner.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.LikeDto;
import com.planner.planner.Entity.Account;
import com.planner.planner.Exception.NotFoundToken;
import com.planner.planner.Service.AccountService;
import com.planner.planner.util.FileStore;
import com.planner.planner.util.FileStore.FileLocation;
import com.planner.planner.util.ResponseMessage;

@RestController
@RequestMapping(value="/api/users")
public class AccountController {
	private final static Logger logger = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private AccountService accountService;

	
	@GetMapping(value="/{accountId}")
	public ResponseEntity<Object> account(HttpServletRequest req, @PathVariable int accountId) throws Exception {
		int id = Integer.parseInt(req.getAttribute("userId").toString());
		if (id != accountId) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseMessage(false, "접근 권한이 없습니다."));
		}
		
		AccountDto user = accountService.findById(accountId);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "", user));
	}
	
	@PutMapping(value="/{accountId}")
	public ResponseEntity<Object> accountUpdate(@PathVariable int accountId, @RequestPart(value="data") AccountDto accountDto, @RequestPart(value="image") MultipartFile images) throws Exception {
		AccountDto account = accountService.accountUpdate(accountDto, images);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "",account));
	}
	
	@GetMapping(value="/{accountId}/likes")
	public ResponseEntity<Object> likes(HttpServletRequest req, @PathVariable int accountId) {
		int id = Integer.parseInt(req.getAttribute("userId").toString());
		if (id != accountId) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseMessage(false, "접근 권한이 없습니다."));
		}
		
		LikeDto likes =  accountService.getLikes(accountId);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "",likes));
	}
	
}
