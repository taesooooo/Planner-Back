package com.planner.planner.Service.Impl;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.planner.planner.Common.Image;
import com.planner.planner.Common.Page;
import com.planner.planner.Dao.AccountDao;
import com.planner.planner.Dao.PlannerDao;
import com.planner.planner.Dao.Impl.AccountDaoImpl;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.CommonRequestParamDto;
import com.planner.planner.Dto.PlannerDto;
import com.planner.planner.Dto.SpotLikeDto;
import com.planner.planner.Exception.NotFoundUserException;
import com.planner.planner.Service.AccountService;
import com.planner.planner.Service.PlannerService;
import com.planner.planner.Service.SpotService;
import com.planner.planner.util.FileStore;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountDaoImpl.class);

	private AccountDao accountDao;
	private PlannerService plannerService;
	private PlannerDao plannerDao;
	private SpotService spotService;

	private FileStore fileStore;

	public AccountServiceImpl(AccountDao accountDao, PlannerService plannerService, PlannerDao plannerDao, SpotService spotService, FileStore fileStore) {
		this.accountDao = accountDao;
		this.plannerService = plannerService;
		this.plannerDao = plannerDao;
		this.spotService = spotService;
		this.fileStore = fileStore;
	}

	@Override
	public AccountDto findById(int accountId) throws Exception {
		AccountDto user = accountDao.findById(accountId);
		if(user == null) {
			throw new NotFoundUserException();
		}
		return user;
	}

	@Override
	public boolean accountUpdate(AccountDto accountDto) throws Exception {
		return accountDao.update(accountDto);
	}

	@Override
	public boolean accountImageUpdate(int accountId, MultipartFile image) throws Exception {
		// 이미지 경로 생성
		Image path = fileStore.createFilePath(image, "Account");

		// 기존 이미지 확인 후 삭제
		File previousImage = fileStore.getFile(fileStore.getBaseLocation() + path);
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
		return accountDao.accountImageUpdate(accountId, path.getPath());
	}

	@Override
	public boolean passwordUpdate(AccountDto accountDto) {
		return accountDao.passwordUpdate(accountDto);
	}

	@Override
	public void acceptInvite(int plannerId, int accountId) {
		plannerDao.acceptInvitation(plannerId, accountId);
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
		AccountDto user = accountDao.searchEmail(searchEmail);
		if(user == null) {
			throw new NotFoundUserException(searchEmail + "는 존재하지 않습니다. 확인 후 다시 시도하세요.");
		}
		return true;
	}
}
