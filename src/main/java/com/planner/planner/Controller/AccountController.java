package com.planner.planner.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planner.planner.Entity.Account;
import com.planner.planner.Service.AccountService;

@RestController
@RequestMapping(value="/users")
public class AccountController {

	@Autowired
	private AccountService accountService;
	
	@GetMapping(value="/{email}/likes")
	public ResponseEntity<Object> likeData(HttpServletRequest req, @PathVariable String email) {
		HttpSession session = req.getSession(false);
		if(session == null) return null;
		
		Account user = (Account)session.getAttribute(session.getId());
		if(user == null) return null;
		if(!user.getEmail().equals(email)) return null;
		
		//accountService.getLike(email);
		
		return null;
	}
}
