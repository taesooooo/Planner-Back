package com.planner.planner.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.LikeDto;
import com.planner.planner.Entity.Account;
import com.planner.planner.Service.AccountService;
import com.planner.planner.util.ResponseMessage;

@RestController
@RequestMapping(value="/api/users")
public class AccountController {

	@Autowired
	private AccountService accountService;
	
	@GetMapping(value="/{email}/likes")
	public ResponseEntity<Object> likes(HttpServletRequest req, @PathVariable String email) {
		HttpSession session = req.getSession(false);
		if(session == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseMessage(false, "로그인이 필요합니다."));
		
		AccountDto user = (AccountDto)session.getAttribute(session.getId());
		if(user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseMessage(false, "로그인이 필요합니다."));
		if(!user.getEmail().equals(email)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseMessage(false, "접근 권한이 없습니다."));
		
		LikeDto likes =  accountService.getLikes(user.getAccountId());
		
		return ResponseEntity.ok(new ResponseMessage(true, "",likes));
	}
}
