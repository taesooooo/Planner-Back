package com.planner.planner.Controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Service.AuthService;
import com.planner.planner.util.JwtUtil;
import com.planner.planner.util.ResponseMessage;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	private AuthService authService;

	private JwtUtil jwtUtil;

	private BCryptPasswordEncoder passwordEncoder;

	public AuthController(AuthService authService, BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
		this.authService = authService;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
	}

	@PostMapping(value = "/register")
	public ResponseEntity<Object> register(HttpServletRequest req, @RequestBody AccountDto accountDto) {
		String pwEncode = passwordEncoder.encode(accountDto.getPassword());
		AccountDto userDto = new AccountDto.Builder().setEmail(accountDto.getEmail()).setPassword(pwEncode).setUserName(accountDto.getUserName())
				.setNickName(accountDto.getNickName()).setImage(accountDto.getImage()).setPhone(accountDto.getPhone()).build();
		try {
			boolean result = authService.register(userDto);

			if (result) {
				return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(true, "회원 가입 성공"));
			}
		}
		catch (DuplicateKeyException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseMessage(false, "중복된 아이디 입니다."));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(false, "회원 가입 실패"));
	}

	@PostMapping(value = "/login")
	public ResponseEntity<Object> login(HttpServletRequest req, @RequestBody AccountDto accountDto) {
		try {
			AccountDto user = authService.login(accountDto);
			if (passwordEncoder.matches(accountDto.getPassword(), user.getPassword())) {
				String token = jwtUtil.createToken(user.getAccountId());
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "로그인 성공", user,token));
			}
			else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(false, "아이디 또는 비밀번호를 잘 못입력헀습니다."));
			}
		}
		catch (EmptyResultDataAccessException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(false, "아이디 또는 비밀번호를 잘 못입력헀습니다."));
		}
	}

	@GetMapping(value = "/logout")
	public ResponseEntity<Object> logout(HttpServletRequest req) {
		return ResponseEntity.ok(new ResponseMessage(true, "로그아웃 되었습니다."));
	}
}
