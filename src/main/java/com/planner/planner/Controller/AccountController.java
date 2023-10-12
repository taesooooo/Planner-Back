package com.planner.planner.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.planner.planner.Common.Page;
import com.planner.planner.Common.PostType;
import com.planner.planner.Common.ValidationGroups.AccountUpdateGroup;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.AuthenticationCodeDto;
import com.planner.planner.Dto.CommonRequestParamDto;
import com.planner.planner.Dto.FindPasswordDto;
import com.planner.planner.Dto.NotificationDto;
import com.planner.planner.Dto.PasswordDto;
import com.planner.planner.Dto.PasswordResetkeyDto;
import com.planner.planner.Dto.PlannerDto;
import com.planner.planner.Exception.ForbiddenException;
import com.planner.planner.Service.AccountService;
import com.planner.planner.Service.AuthenticationCodeService;
import com.planner.planner.Service.EmailService;
import com.planner.planner.Service.NotificationService;
import com.planner.planner.Service.PasswordResetKeyService;
import com.planner.planner.Util.RandomCode;
import com.planner.planner.Util.ResponseMessage;
import com.planner.planner.Util.UserIdUtil;

@RestController
@RequestMapping(value = "/api/users")
public class AccountController {
	private final static Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

	private AccountService accountService;
	private NotificationService notificationService;
	private AuthenticationCodeService authenticationCodeService;
	private PasswordResetKeyService passwordResetKeyService;
	private EmailService mailService;
	private RandomCode randomCode;

	public AccountController(AccountService accountService, NotificationService notificationService, AuthenticationCodeService authenticationCodeService,
			PasswordResetKeyService passwordResetKeyService, EmailService mailService, RandomCode randomCode) {
		this.accountService = accountService;
		this.notificationService = notificationService;
		this.authenticationCodeService = authenticationCodeService;
		this.passwordResetKeyService = passwordResetKeyService;
		this.mailService = mailService;
		this.randomCode = randomCode;
	}

	@GetMapping(value = "/{accountId}")
	public ResponseEntity<Object> account(HttpServletRequest req, @PathVariable int accountId) throws Exception {
		int id = Integer.parseInt(req.getAttribute("userId").toString());
		if (id != accountId) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseMessage(false, "접근 권한이 없습니다."));
		}

		AccountDto user = accountService.findById(accountId);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "", user));
	}

	@PatchMapping(value = "/{accountId}")
	public ResponseEntity<Object> accountUpdate(@PathVariable int accountId, @RequestBody @Validated(AccountUpdateGroup.class) AccountDto accountDto)
			throws Exception {
		if (accountService.accountUpdate(accountDto)) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "계정 정보 변경을 성공헀습니다."));
		}

		return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseMessage(true, "계정 정보 변경을 실패했습니다."));
	}

	@PatchMapping(value = "/{accountId}/images")
	public ResponseEntity<Object> accountImageUpdate(@PathVariable int accountId, @RequestPart(value = "image") MultipartFile image) throws Exception {
		if (accountService.accountImageUpdate(accountId, image)) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "계정 이미지 변경을 성공헀습니다."));
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseMessage(false, "계정 이미지 변경을 실패헀습니다."));
		}
	}
	
	@GetMapping(value = "/{accountId}/planners")
	public ResponseEntity<Object> myPlanners(HttpServletRequest req, @PathVariable int accountId, CommonRequestParamDto commonRequestParamDto) throws Exception {
		checkAuth(req, accountId);
		
		Page<PlannerDto> list = accountService.myPlanners(accountId, commonRequestParamDto);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "", list));
	}

	@GetMapping(value = "/{accountId}/likes")
	public ResponseEntity<Object> likes(HttpServletRequest req, @PathVariable int accountId, CommonRequestParamDto commonRequestParamDto) throws Exception {
		checkAuth(req, accountId);
		Page<?> list = null;
		PostType postType = commonRequestParamDto.getPostType();
		
		if(postType == PostType.PLANNER) {
			list = accountService.likePlanners(accountId, commonRequestParamDto);
		}
		else if(postType == PostType.SPOT) {
			list = accountService.likeSpots(accountId, commonRequestParamDto);
		}

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "", list));
	}
	
	@GetMapping(value = "/{accountId}/notifications")
	public ResponseEntity<Object> notification(@PathVariable int accountId) throws Exception {
		List<NotificationDto> list = notificationService.findAllByAccountId(accountId);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "", list));
	}

	@GetMapping(value="/search-member")
	public ResponseEntity<Object> searchMembers(HttpServletRequest req, @RequestParam(value="searchString") String searchString) throws Exception {
		boolean emailCheck = accountService.searchEmail(searchString);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "", emailCheck));
	}
	
	@PostMapping(value = "/find-email")
	public ResponseEntity<Object> findEmail(HttpServletRequest req, @RequestBody @Valid AuthenticationCodeDto authenticationCodeDto) throws Exception {
		if(authenticationCodeDto.getEmail() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(false, "", "휴대폰 번호를 입력해주세요."));
		}
		
		AuthenticationCodeDto authCode = authenticationCodeService.findByPhone(authenticationCodeDto.getEmail());
		if(!authCode.isConfirm()) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(false, "", "인증되지 않았습니다. 다시 시도해 주세요."));
		}
		
		List<String> foundEmails = accountService.findEmailByPhone(authenticationCodeDto.getPhone());
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "", foundEmails));
	}
	
	
	@PostMapping(value = "/find-password")
	public ResponseEntity<Object> findPassword(@RequestBody @Valid FindPasswordDto findPasswordDto) throws Exception {
		String mail = findPasswordDto.getEmail();
		
		AccountDto user = accountService.findByEmail(mail);
		
		String resetKey = randomCode.createStrCode(6, true);
		
		passwordResetKeyService.createPasswordResetKey(resetKey, user.getAccountId());
		
		mailService.sendPasswordResetEmail(mail,resetKey);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "비밀번호 재설정 메일이 전송 되었습니다."));
	}
	
	@PostMapping(value = "/change-password")
	public ResponseEntity<Object> changePassword(@RequestBody @Valid PasswordDto passwordDto) throws Exception {
		boolean check = passwordResetKeyService.validatePasswordResetKey(passwordDto.getKey());
		if(check) {
			PasswordResetkeyDto resetKey = passwordResetKeyService.findBykey(passwordDto.getKey());
			
			accountService.passwordUpdate(resetKey.getAccountId(), passwordDto.getNewPassword());
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(true, "만료되었습니다. 비밀번호 재설정을 다시 시도 하세요."));
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "비밀번호가 재설정 되었습니다."));
	}
	
	private void checkAuth(HttpServletRequest req, int accountId) throws Exception {
		int id = UserIdUtil.getUserId(req);
		AccountDto user = accountService.findById(accountId);
		if (id != user.getAccountId()) {
			throw new ForbiddenException("접근 권한이 없습니다.");
		}
	}
}
