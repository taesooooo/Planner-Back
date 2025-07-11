package com.planner.planner.Service.Impl;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.planner.planner.Common.FileInfo;
import com.planner.planner.Common.Page;
import com.planner.planner.Common.Notification.NotificationMessage;
import com.planner.planner.Common.Notification.NotificationType;
import com.planner.planner.Dao.Impl.AccountDaoImpl;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.CommonRequestParamDto;
import com.planner.planner.Dto.FindEmailDto;
import com.planner.planner.Dto.NotificationDto;
import com.planner.planner.Dto.PlannerDto;
import com.planner.planner.Dto.SpotLikeDto;
import com.planner.planner.Exception.UserNotFoundException;
import com.planner.planner.Mapper.AccountMapper;
import com.planner.planner.Mapper.NotificationMapper;
import com.planner.planner.Mapper.PasswordResetKeyMapper;
import com.planner.planner.Mapper.PlanMemberMapper;
import com.planner.planner.Mapper.PlannerMapper;
import com.planner.planner.Service.AccountService;
import com.planner.planner.Service.PlannerService;
import com.planner.planner.Service.SpotService;
import com.planner.planner.Util.FileStore;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountDaoImpl.class);

	private final AccountMapper accountMapper;
	private final PlannerService plannerService;
	private final PlannerMapper plannerMapper;
	private final PlanMemberMapper planMemberMapper;
	private final SpotService spotService;
	private final NotificationMapper notificationMapper;

	private final FileStore fileStore;
	private final BCryptPasswordEncoder passwordEncoder;
	private final PasswordResetKeyMapper passwordResetKeyMapper;

	@Override
	public AccountDto findById(int accountId) throws Exception {
		AccountDto user = accountMapper.findById(accountId);
		if(user == null) {
			throw new UserNotFoundException();
		}
		return user;
	}

	@Override
	public List<String> findEmailByPhone(String phone) throws Exception {
		List<String> emails = accountMapper.findEmailByPhone(phone);
		if(emails.isEmpty()) {
			throw new UserNotFoundException("해당 정보로 가입된 계정이 없습니다.");
		}
		return emails;
	}

	@Override
	public AccountDto findByEmail(String email) throws Exception {
		AccountDto user = accountMapper.findByEmail(email);
		if(user == null) {
			throw new UserNotFoundException();
		}
		return user;
	}

	@Override
	public AccountDto findByNameAndPhone(String name, String phone) throws Exception {
		List<AccountDto> users = accountMapper.findByNameAndPhone(name, phone);
		if(users.isEmpty()) {
			throw new UserNotFoundException("해당하는 정보로 가입된 아이디가 존재하지 않습니다.");
		}
		
		return users.get(0);
	}

	@Override
	public boolean accountUpdate(int accountId, String nickname, String phone) throws Exception {
		return accountMapper.update(accountId, nickname, phone);
	}
	
	@Override
	public boolean accountImageUpdate(int accountId, MultipartFile image) throws Exception {
		// 이미지 경로 생성
		FileInfo path = fileStore.createFilePath(image, "Account");

		// 기존 이미지 확인 후 삭제
		File previousImage = fileStore.getFile(fileStore.getBaseLocation() + path.getAbsolutePath());
		if(previousImage != null) {
			previousImage.delete();
		}

		// 이미지 저장
		File file = new File(path.getAbsolutePath());
		if(!file.getParentFile().exists()) {
			file.getParentFile().mkdir();
		}

		image.transferTo(file);

		// DB 업데이트
		return accountMapper.accountImageUpdate(accountId, path.getPath());
	}

	@Override
	public boolean passwordUpdate(int accountId, String password, String key) throws Exception {
		String newPassword = passwordEncoder.encode(password);
		
		accountMapper.passwordUpdate(accountId, newPassword);
		
		passwordResetKeyMapper.deleteByResetKey(key);
		
		NotificationDto notification = NotificationDto.builder()
				.accountId(accountId)
				.content(NotificationMessage.PASSWORD_UPDATE)
				.notificationType(NotificationType.ACCOUNT)
				.build();
		
		notificationMapper.insertNotification(accountId, notification);
		
		return true;
	}

	@Override
	public Page<PlannerDto> myPlanners(int accountId, CommonRequestParamDto commonRequestParamDto) throws Exception {
		return plannerService.findPlannersByAccountId(accountId, commonRequestParamDto);
	}

	@Override
	public Page<PlannerDto> likePlanners(int accountId, CommonRequestParamDto commonRequestParamDto) throws Exception {
		return plannerService.getLikePlannerList(accountId, commonRequestParamDto);
	}

	@Override
	public Page<SpotLikeDto> likeSpots(int accountId, CommonRequestParamDto commonRequestParamDto) throws Exception {
		return spotService.getSpotLikeList(accountId, commonRequestParamDto);
	}

	@Override
	public boolean searchEmail(String searchEmail) throws Exception {
		AccountDto user = accountMapper.findByEmail(searchEmail);
		if(user == null) {
			throw new UserNotFoundException(searchEmail + "는 존재하지 않습니다. 확인 후 다시 시도하세요.");
		}
		return true;
	}

	@Override
	public List<String> findId(FindEmailDto findEmailDto) throws Exception {
		List<AccountDto> users = accountMapper.findByNameAndPhone(findEmailDto.getUserName(), findEmailDto.getPhone());
		if(users.isEmpty()) {
			throw new UserNotFoundException("해당하는 정보로 가입된 아이디가 존재하지 않습니다.");
		}
		
		List<String> userEmails = users.stream().map((item) -> item.getEmail()).collect(Collectors.toList());
		
		return userEmails;
	}
	
	
}
