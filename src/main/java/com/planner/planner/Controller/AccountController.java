package com.planner.planner.Controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.planner.planner.Common.Page;
import com.planner.planner.Common.ValidationGroups.AccountUpdateGroup;
import com.planner.planner.Common.PostType;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.PlannerDto;
import com.planner.planner.Exception.ForbiddenException;
import com.planner.planner.Service.AccountService;
import com.planner.planner.util.ResponseMessage;
import com.planner.planner.util.UserIdUtil;

@RestController
@RequestMapping(value = "/api/users")
public class AccountController {
	private final static Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

	private AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
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

	@PatchMapping(value = "/images/{accountId}")
	public ResponseEntity<Object> accountImageUpdate(@PathVariable int accountId, @RequestPart(value = "image") MultipartFile image) throws Exception {
		if (accountService.accountImageUpdate(accountId, image)) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "계정 이미지 변경을 성공헀습니다."));
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseMessage(false, "계정 이미지 변경을 실패헀습니다."));
		}
	}
	
	@GetMapping(value = "/{accountId}/planners")
	public ResponseEntity<Object> likePlanners(HttpServletRequest req, @RequestParam(value="page") int page, @PathVariable int accountId) throws Exception {
		checkAuth(req, accountId);
		
		Page<PlannerDto> list = accountService.myPlanners(page, accountId);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "", list));
	}

	@GetMapping(value = "/{accountId}/likes")
	public ResponseEntity<Object> likes(HttpServletRequest req, @PathVariable int accountId,  @RequestParam(value="page") int page, @RequestParam("type") PostType postType) throws Exception {
		checkAuth(req, accountId);
		Page<?> list = null;
		if(postType == PostType.PLANNER) {
			list = accountService.likePlanners(page, accountId);
		}
		else if(postType == PostType.SPOT) {
			list = accountService.likeSpots(accountId, page);
		}

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "", list));
	}

	@GetMapping(value="/search-member")
	public ResponseEntity<Object> searchMembers(HttpServletRequest req, @RequestParam(value="searchString") String searchString) throws Exception {
		boolean emailCheck = accountService.searchEmail(searchString);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true, "", emailCheck));
	}
	
	private void checkAuth(HttpServletRequest req, int accountId) throws Exception {
		int id = UserIdUtil.getUserId(req);
		AccountDto user = accountService.findById(accountId);
		if (id != user.getAccountId()) {
			throw new ForbiddenException("접근 권한이 없습니다.");
		}
	}
}
