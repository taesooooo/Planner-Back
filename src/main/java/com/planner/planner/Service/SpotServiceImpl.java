package com.planner.planner.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planner.planner.Dao.SpotDao;
import com.planner.planner.Dto.SpotDto;
import com.planner.planner.Dto.SpotLikeDto;
import com.planner.planner.Entity.Spot;
import com.planner.planner.Entity.SpotLike;

@Service
@Transactional
public class SpotServiceImpl implements SpotService {

	private SpotDao spotDao;
	
	public SpotServiceImpl(SpotDao spotDao) {
		this.spotDao = spotDao;
	}

	@Override
	public List<SpotLikeDto> spotLikesFindByAccountId(int accountId) {
		return spotDao.spotLikesByAccountId(accountId);
	}

	@Override
	public boolean spotLike(int accountId, int contentId) {
		//boolean result =  spotDao.spotLike(contentId);
		//result = spotDao.spotLikeAdd(contentId, accountId);
		
		return spotDao.spotLikeAdd(accountId, contentId);
	}

	@Override
	public boolean spotLikeCancel(int accountId, int contentId) {
//		boolean result = spotDao.spotLikeCancel(contentId);
//		result = spotDao.spotLikeDelete(accountId, contentId);

		return spotDao.spotLikeDelete(accountId, contentId);
	}

}
