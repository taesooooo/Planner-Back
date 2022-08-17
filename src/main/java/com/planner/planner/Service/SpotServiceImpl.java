package com.planner.planner.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planner.planner.Dao.SpotDao;
import com.planner.planner.Dto.SpotDto;
import com.planner.planner.Entity.Spot;

@Service
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
	public List<SpotDto> getSpotLikesByAccountId(int accountId) {
		return spotDao.getSpotLikesByAccountId(accountId).stream().map((s) -> {
			return new SpotDto.Builder()
					.setSpotId(s.getSpotId())
					.setSpotName(s.getSpotName())
					.setSpotImage(s.getSpotImage())
					.setContryName(s.getContryName())
					.setCityName(s.getCityName())
					.build();
		}).collect(Collectors.toList());
	}

	@Override
	public boolean spotLike(int accountId) {
		return spotDao.spotLike(accountId);
	}

}
