package com.planner.planner.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.planner.planner.Common.ValidationGroups.LoginGroup;
import com.planner.planner.Common.ValidationGroups.RegisterGroup;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.AuthenticationCodeDto;
import com.planner.planner.Service.AuthService;
import com.planner.planner.Service.AuthenticationCodeService;
import com.planner.planner.Service.PasswordResetKeyService;
import com.planner.planner.Util.JwtUtil;
import com.planner.planner.Util.ResponseMessage;

@RestController
@Validated
@RequestMapping("/api/auth")
public class AuthController {
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	private AuthService authService;
	private AuthenticationCodeService authenticationCodeService;
	private PasswordResetKeyService passwordResetKeyService;

	private JwtUtil jwtUtil;

	private BCryptPasswordEncoder passwordEncoder;

	public AuthController(AuthService authService, AuthenticationCodeService authenticationCodeService, PasswordResetKeyService passwordResetKeyService, BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
		this.authService = authService;
		this.authenticationCodeService = authenticationCodeService;
		this.passwordResetKeyService = passwordResetKeyService;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
	}

	@PostMapping(value = "/register")
	public ResponseEntity<Object> register(HttpServletRequest req, @RequestBody @Validated(RegisterGroup.class) AccountDto accountDto) {
		String pwEncode = passwordEncoder.encode(accountDto.getPassword());
		AccountDto userDto = new AccountDto.Builder().setEmail(accountDto.getEmail()).setPassword(pwEncode)
				.setUsername(accountDto.getUsername()).setNickname(accountDto.getNickname())
				.setImage(accountDto.getImage()).setPhone(accountDto.getPhone()).build();
		try {
			boolean result = authService.register(userDto);

			if (result) {
				return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(true, "회원 가입 성공"));
			}
		} catch (DuplicateKeyException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseMessage(false, "중복된 아이디 입니다."));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(false, "회원 가입 실패"));
	}

	@PostMapping(value = "/login")
	public ResponseEntity<Object> login(HttpServletRequest req, @RequestBody @Validated(LoginGroup.class) AccountDto accountDto) throws Exception {
		AccountDto user = authService.login(accountDto);
		if (passwordEncoder.matches(accountDto.getPassword(), user.getPassword())) {
			String token = jwtUtil.createToken(user.getAccountId());
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "로그인 성공", user, token));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseMessage(false, "아이디 또는 비밀번호를 잘 못입력헀습니다."));
		}
	}


	@GetMapping(value = "/logout")
	public ResponseEntity<Object> logout(HttpServletRequest req) {
		return ResponseEntity.ok(new ResponseMessage(true, "로그아웃 되었습니다."));
	}
	
	@PostMapping(value = "/authentication-code/send")
	public ResponseEntity<Object> authenticationCode(@RequestParam @NotBlank(message = "휴대폰 번호는 필수 입니다.") @Size(min = 11, max=11, message = "휴대폰 번호를 정확히 입력해주세요.") String phone) throws Exception {
		boolean isSent = authenticationCodeService.codeSend(phone);
		if (!isSent) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseMessage(isSent, "인증번호 전송 실패"));
		}

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(isSent, ""));
	}
	
	@PostMapping(value = "/authentication-code/check")
	public ResponseEntity<Object> authenticationCodeCheck(@RequestBody @Valid AuthenticationCodeDto authenticationCodeDto) {
		boolean isConfirmed = authenticationCodeService.codeCheck(authenticationCodeDto);
		if(!isConfirmed) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(isConfirmed, "인증번호가 다릅니다."));
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(isConfirmed, ""));
	}
}
