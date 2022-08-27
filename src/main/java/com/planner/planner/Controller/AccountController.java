package com.planner.planner.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.LikeDto;
import com.planner.planner.Entity.Account;
import com.planner.planner.Exception.NotFoundToken;
import com.planner.planner.Service.AccountService;
import com.planner.planner.util.ResponseMessage;

@RestController
@RequestMapping(value="/api/users")
public class AccountController {

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
