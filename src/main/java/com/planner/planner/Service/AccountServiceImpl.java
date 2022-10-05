package com.planner.planner.Service;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.planner.planner.Common.Image;
import com.planner.planner.Dao.AccountDao;
import com.planner.planner.Dao.AccountDaoImpl;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.LikeDto;
import com.planner.planner.Dto.PlannerDto;
import com.planner.planner.Dto.SpotLikeDto;
import com.planner.planner.Dto.SpotLikeStateDto;
import com.planner.planner.Entity.Account;
import com.planner.planner.util.FileStore;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
	private static final Logger logger = LoggerFactory.getLogger(AccountDaoImpl.class);

	private AccountDao accountDao;

	private FileStore fileStore;

	public AccountServiceImpl(AccountDao accountDao,FileStore fileStore) {
		this.accountDao = accountDao;
		this.fileStore = fileStore;
	}

	@Override
	public AccountDto findById(int accountId) {
		Account user = accountDao.findById(accountId);
		return user.toDto();
	}

	@Override
	public boolean accountUpdate(AccountDto accountDto) throws Exception {
		return accountDao.update(accountDto.toEntity());
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
		return accountDao.passwordUpdate(accountDto.toEntity());
	}

	@Override
	public LikeDto allLikes(int accountId) {
		List<PlannerDto> likeP = accountDao.likePlanners(accountId);
		List<SpotLikeDto> likeS = accountDao.likeSpots(accountId);

		return new LikeDto.Builder().setLikePlanners(likeP).setLikeSpots(likeS).build();
	}

	@Override
	public List<SpotLikeDto> spotLikesByAccountId(int accountId) {
		return accountDao.spotLikesByAccountId(accountId);
	}

	@Override
	public List<SpotLikeStateDto> spotLikeStateCheck(int accountId, List<Integer> contentList) {
		List<SpotLikeDto> list = accountDao.spotLikesByContentIds(accountId, contentList);

		List<SpotLikeStateDto> likeStates = contentList.stream().map((i) -> {
			SpotLikeStateDto item;
			if (list.stream().anyMatch(like -> like.getContentId() == i)) {
				item = new SpotLikeStateDto(i, true);
			} else {
				item = new SpotLikeStateDto(i, false);
			}
			return item;
		}).collect(Collectors.toList());

		return likeStates;
	}
}
