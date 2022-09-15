package com.planner.planner.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.planner.planner.Common.Image;
import com.planner.planner.Dao.AccountDao;
import com.planner.planner.Dao.AccountDaoImpl;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.LikeDto;
import com.planner.planner.Dto.PlannerDto;
import com.planner.planner.Dto.SpotDto;
import com.planner.planner.Entity.Account;
import com.planner.planner.Entity.Like;
import com.planner.planner.util.FileStore;
import com.planner.planner.util.FileStore.FileLocation;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
	private static final Logger logger = LoggerFactory.getLogger(AccountDaoImpl.class);
	
	@Autowired
	private AccountDao accountDao;
	
	private FileStore fileStore;
	
	public AccountServiceImpl(FileStore fileStore) {
		this.fileStore = fileStore;
	}

	@Override
	public AccountDto findById(int accountId) {
		Account user = accountDao.findById(accountId);
		return user.toDto();
	}

	@Override
	@Transactional
	public boolean register(AccountDto accountDto) {
		return accountDao.create(accountDto.toEntity());
	}

	@Override
	public AccountDto login(AccountDto accountDto) {
		Account user = accountDao.read(accountDto.toEntity());
		return user.toDto();
	}
	
	@Override
	public AccountDto accountUpdate(AccountDto accountDto, MultipartFile image) throws Exception {
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
		
		accountDto.setImage(path.getPath());
		
		image.transferTo(file);
		
		// DB 업데이트
		accountDao.update(accountDto.toEntity());
		
		// 변경된 DB 가져오기
		AccountDto account = accountDao.read(accountDto.toEntity()).toDto();
		
		return account;
	}

	@Override
	public boolean passwordUpdate(AccountDto accountDto) {
		return accountDao.passwordUpdate(accountDto.toEntity());
	}

	@Override
	public LikeDto getLikes(int accountId) {
		List<PlannerDto> likeP = accountDao.likePlanners(accountId).stream().map((p) -> p.toDto()).collect(Collectors.toList());
		List<SpotDto> likeS = accountDao.likeSpots(accountId).stream().map((s) -> {
			return new SpotDto.Builder()
					.setSpotId(s.getSpotId())
					.setSpotName(s.getSpotName())
					.setSpotImage(s.getSpotImage())
					.setContryName(s.getContryName())
					.setCityName(s.getCityName())
					.build();
		}).collect(Collectors.toList());
		return new LikeDto.Builder().setLikePlanners(likeP).setLikeSpots(likeS).build();
	}

}
