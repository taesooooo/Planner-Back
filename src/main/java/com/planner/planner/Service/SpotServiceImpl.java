package com.planner.planner.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.planner.planner.Dao.SpotDao;
import com.planner.planner.Dto.SpotDto;
import com.planner.planner.Dto.SpotLikeCountDto;
import com.planner.planner.Entity.SpotLikeCount;
import com.planner.planner.util.OpenAPIUtil;

@Service
@Transactional
public class SpotServiceImpl implements SpotService {
	private SpotDao spotDao;
	
	public SpotServiceImpl(SpotDao spotDao) {
		this.spotDao = spotDao;
	}
	
	@Override
	public boolean spotLike(int accountId, int contentId) {
		// boolean result = spotDao.spotLike(contentId);
		// result = spotDao.spotLikeAdd(contentId, accountId);

		return spotDao.spotLikeAdd(accountId, contentId);
	}

	@Override
	public boolean spotLikeCancel(int accountId, int contentId) {
//		boolean result = spotDao.spotLikeCancel(contentId);
//		result = spotDao.spotLikeDelete(accountId, contentId);

		return spotDao.spotLikeDelete(accountId, contentId);
	}

	@Override
	public List<SpotLikeCountDto> spotLikeCount(List<String> contentIds) {
		String ids = contentIds.stream().collect(Collectors.joining(","));
		return spotDao.spotLikeCount(ids);
	}

}
