package com.planner.planner.Controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.planner.planner.Dto.FindEmailDto;
import com.planner.planner.Dto.FindPasswordDto;
import com.planner.planner.Dto.NotificationDto;
import com.planner.planner.Dto.PasswordDto;
import com.planner.planner.Dto.PasswordResetkeyDto;
import com.planner.planner.Dto.PlannerDto;
import com.planner.planner.Dto.UploadFileDto;
import com.planner.planner.Service.AccountService;
import com.planner.planner.Service.AuthenticationCodeService;
import com.planner.planner.Service.EmailService;
import com.planner.planner.Service.FileService;
import com.planner.planner.Service.NotificationService;
import com.planner.planner.Service.PasswordResetKeyService;
import com.planner.planner.Service.SENSService;
import com.planner.planner.Util.RandomCode;
import com.planner.planner.Util.ResponseMessage;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/users")
public class AccountController {
	private final static Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

	private AccountService accountService;
	private NotificationService notificationService;
	private AuthenticationCodeService authenticationCodeService;
	private SENSService sensService;
	private PasswordResetKeyService passwordResetKeyService;
	private EmailService mailService;
	private RandomCode randomCode;
	private FileService fileService;

	public AccountController(AccountService accountService, NotificationService notificationService, AuthenticationCodeService authenticationCodeService, SENSService sensService,
			PasswordResetKeyService passwordResetKeyService, EmailService mailService, RandomCode randomCode, FileService fileService) {
		this.accountService = accountService;
		this.notificationService = notificationService;
		this.authenticationCodeService = authenticationCodeService;
		this.sensService = sensService;
		this.passwordResetKeyService = passwordResetKeyService;
		this.mailService = mailService;
		this.randomCode = randomCode;
		this.fileService = fileService;
	}

	@GetMapping(value = "/{accountId}")
	public ResponseEntity<Object> account(HttpServletRequest req, @PathVariable int accountId) throws Exception {
		AccountDto user = accountService.findById(accountId);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "", user));
	}

	@PatchMapping(value = "/{accountId}")
	public ResponseEntity<Object> accountUpdate(@PathVariable int accountId, @RequestBody @Validated(AccountUpdateGroup.class) AccountDto accountDto)
			throws Exception {
		if (accountService.accountUpdate(accountId, accountDto.getNickname(), accountDto.getPhone())) {
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
	
	@GetMapping(value = "/{accountId}/images")
	public ResponseEntity<byte[]> accountImage(@PathVariable int accountId) throws Exception {
		AccountDto user = accountService.findById(accountId);
		UploadFileDto file = fileService.loadLocalFile(user.getImage());

		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.parseMediaType(file.getType())).body(file.getData());
	}
	
	@GetMapping(value = "/{accountId}/planners")
	public ResponseEntity<Object> myPlanners(HttpServletRequest req, @PathVariable int accountId, CommonRequestParamDto commonRequestParamDto) throws Exception {
		Page<PlannerDto> list = accountService.myPlanners(accountId, commonRequestParamDto);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "", list));
	}

	@GetMapping(value = "/{accountId}/likes")
	public ResponseEntity<Object> likes(HttpServletRequest req, @PathVariable int accountId, CommonRequestParamDto commonRequestParamDto) throws Exception {
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
	public ResponseEntity<Object> findEmail(HttpServletRequest req, @RequestBody @Valid FindEmailDto findEmailDto) throws Exception {		
		if(findEmailDto.getCode() == null) {
			AccountDto user = accountService.findByNameAndPhone(findEmailDto.getUserName(), findEmailDto.getPhone());
			
			String code = randomCode.createCode();
			
			if(authenticationCodeService.createPhoneAuthenticationCode(findEmailDto.getPhone(), code)) {
				sensService.authenticationCodeSMSSend(findEmailDto.getPhone(), code);
				
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "인증 코드를 보냈습니다."));
			}
		}
		else {
			AuthenticationCodeDto authCode = AuthenticationCodeDto.builder()
					.phone(findEmailDto.getPhone())
					.code(findEmailDto.getCode())
					.build();
			boolean check = authenticationCodeService.codeCheck(authCode);
			if(!check) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(false, "인증되지 않았습니다. 다시 시도해 주세요."));
			}
		}
		
		List<String> foundEmails = accountService.findEmailByPhone(findEmailDto.getPhone());
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "", foundEmails));
	}
	
	
	@PostMapping(value = "/find-password")
	public ResponseEntity<Object> findPassword(@RequestBody @Valid FindPasswordDto findPasswordDto) throws Exception {
		AccountDto user = accountService.findByEmail(findPasswordDto.getEmail());
		
		String resetKey = randomCode.createStrCode(6, true);
		
		passwordResetKeyService.createPasswordResetKey(resetKey, user);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "비밀번호 재설정 메일이 전송 되었습니다."));
	}
	
	@PostMapping(value = "/change-password")
	public ResponseEntity<Object> changePassword(@RequestBody @Valid PasswordDto passwordDto) throws Exception {
		boolean check = passwordResetKeyService.validatePasswordResetKey(passwordDto.getKey());
		if(check) {
			PasswordResetkeyDto resetKey = passwordResetKeyService.findBykey(passwordDto.getKey());
			
			accountService.passwordUpdate(resetKey.getAccountId(), passwordDto.getNewPassword(), passwordDto.getKey());
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(true, "만료되었습니다. 비밀번호 재설정을 다시 시도 하세요."));
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "비밀번호가 재설정 되었습니다."));
	}
}
