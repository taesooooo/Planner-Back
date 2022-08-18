package com.planner.planner.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planner.planner.Dao.SpotDao;
import com.planner.planner.Dto.SpotDto;
import com.planner.planner.Entity.Spot;

@Service
@Transactional
public class SpotServiceImpl implements SpotService {

	@Autowired
	private SpotDao spotDao;
	
	@Override
	public List<SpotDto> getAllSpot() {
		return spotDao.getAllSpot().stream().map((s) -> {
			return new SpotDto.Builder()
					.setSpotId(s.getSpotId())
					.setSpotName(s.getSpotName())
					.setSpotImage(s.getSpotImage())
					.setContryName(s.getContryName())
					.setCityName(s.getCityName())
					.setDetail(s.getDetail())
					.setLikeCount(s.getLikeCount())
					.build();
		}).collect(Collectors.toList());
	}

	@Override
	public boolean spotLike(int accountId, int spotId) {
		boolean result =  spotDao.spotLike(spotId);
		result = spotDao.spotLikeAdd(accountId, spotId);
		
		return result;
	}

	@Override
	public boolean spotLikeCancel(int accountId, int spotId) {
		boolean result = spotDao.spotLikeCancel(spotId);
		result = spotDao.spotLikeDelete(accountId, spotId);

		return result;
	}

}
