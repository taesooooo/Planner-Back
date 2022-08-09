package com.planner.planner.Controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles requests for the application home page.
 */
@RestController
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@GetMapping(value = "/")
	@ResponseBody
	public String home(HttpServletRequest req) {
		logger.info("Main");
	
		
		return "테스트";
	}
	
	@PostMapping(value="/login", produces= "application/json; charset=UTF-8")
	public String login(HttpServletRequest req, @RequestParam String id, @RequestParam String pw) {
	
		if(id.equals("aaaa")) {
			HttpSession session = req.getSession();
			session.setAttribute(session.getId(), "aaaa");
		}
		else
		{
			return "아이디 또는 비밀번호가 틀립니다.";
		}
		
		return "/";
	}
	
	@PostMapping(value="/test", produces = "application/json; charset=UTF-8")
	public String test(HttpServletRequest req) {
		
		HttpSession session = req.getSession(false);
		if(session == null) {
			return "/";
		}
		
		String test = (String)session.getAttribute(session.getId());
		
		if(test == null)
		{
			return "로그인 해주세요.";
		}
		
		return "성공 - " + test;
	}
	
}
