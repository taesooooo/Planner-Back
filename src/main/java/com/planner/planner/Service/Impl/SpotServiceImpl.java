package com.planner.planner.Service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planner.planner.Dao.SpotDao;
import com.planner.planner.Dto.SpotDetailDto;
import com.planner.planner.Dto.SpotDto;
import com.planner.planner.Dto.SpotLikeDto;
import com.planner.planner.Dto.SpotListDto;
import com.planner.planner.Dto.OpenApi.AreaCodeDto;
import com.planner.planner.Dto.OpenApi.CommonBasedDto;
import com.planner.planner.Dto.OpenApi.CommonDetailDto;
import com.planner.planner.Dto.OpenApi.CommonListDto;
import com.planner.planner.Dto.OpenApi.OpenApiDto;
import com.planner.planner.Service.OpenAPIService;
import com.planner.planner.Service.SpotService;

@Service
@Transactional
public class SpotServiceImpl implements SpotService {

	private OpenAPIService apiService;
	private SpotDao spotDao;

	public SpotServiceImpl(SpotDao spotDao, OpenAPIService apiService) {
		this.spotDao = spotDao;
		this.apiService = apiService;
	}

	@Override
	public SpotListDto<AreaCodeDto> getAreaNum() throws Exception {
		CommonListDto<AreaCodeDto> apiData = apiService.getAreaNum();
		SpotListDto<AreaCodeDto> list = new SpotListDto<AreaCodeDto>(apiData.getItems(),apiData.getTotalCount());
		return list;
	}

	@Override
	public SpotListDto<SpotDto> getAreaList(int accountId, OpenApiDto openApiDto) throws Exception {
		CommonListDto<CommonBasedDto> apiData = apiService.getAreaList(openApiDto);
		
		List<Integer> contentIdList = apiData.getItems().stream()
				.map(item -> Integer.parseInt(item.getContentid()))
				.collect(Collectors.toList());
		
		List<SpotLikeDto> spotLikeList = spotDao.selectSpotLikeByContentIdList(accountId, contentIdList);

		List<SpotDto> spotList = apiData.getItems().stream()
				.map(item -> {
					boolean likeState = spotLikeList.stream()
							.anyMatch(likeItem -> likeItem.getContentId() == Integer.parseInt(item.getContentid()));

					SpotDto spot = new SpotDto.Builder()
							.setBasedSpot(item)
							.setLikeState(likeState)
							.build();

					return spot;
		})
		.collect(Collectors.toList());
		
		SpotListDto<SpotDto> list = new SpotListDto<SpotDto>(spotList, apiData.getTotalCount());
				
		return list;
	}

	@Override
	public SpotListDto<SpotDto> getLocationBasedList(int accountId,OpenApiDto openApiDto) throws Exception {
		CommonListDto<CommonBasedDto> apiData = apiService.getLocationBasedList(openApiDto);

		List<Integer> contentIdList = apiData.getItems().stream()
				.map(item -> Integer.parseInt(item.getContentid()))
				.collect(Collectors.toList());
		
		List<SpotLikeDto> spotLikeList = spotDao.selectSpotLikeByContentIdList(accountId, contentIdList);

		List<SpotDto> spotList = apiData.getItems().stream()
				.map(item -> {
					boolean likeState = spotLikeList.stream()
							.anyMatch(likeItem -> likeItem.getContentId() == Integer.parseInt(item.getContentid()));

					SpotDto spot = new SpotDto.Builder().setBasedSpot(item).setLikeState(likeState).build();

					return spot;
		})
		.collect(Collectors.toList());
		
		SpotListDto<SpotDto> list = new SpotListDto<SpotDto>(spotList, apiData.getTotalCount());
				
		return list;
	}

	@Override
	public SpotListDto<SpotDto> getKeyword(int accountId, OpenApiDto openApiDto) throws Exception {
		CommonListDto<CommonBasedDto> apiData = apiService.getKeyword(openApiDto);
		
		List<Integer> contentIdList = apiData.getItems().stream()
				.map(item -> Integer.parseInt(item.getContentid()))
				.collect(Collectors.toList());
		
		List<SpotLikeDto> spotLikeList = spotDao.selectSpotLikeByContentIdList(accountId, contentIdList);
		
		List<SpotDto> spotList = apiData.getItems().stream()
				.map(item -> {
					boolean likeState = spotLikeList.stream()
							.anyMatch(likeItem -> likeItem.getContentId() == Integer.parseInt(item.getContentid()));

					SpotDto spot = new SpotDto.Builder().setBasedSpot(item).setLikeState(likeState).build();

					return spot;
		})
		.collect(Collectors.toList());
		
		SpotListDto<SpotDto> list = new SpotListDto<SpotDto>(spotList, apiData.getTotalCount());
				
		return list;
	}

	@Override
	public SpotDetailDto getDetail(int accountId, int contentId) throws Exception {
		CommonDetailDto detail = apiService.getDetail(contentId);
		
		int likeCount = spotDao.selectSpotLikeCountByContentId(contentId);
		boolean likeState = spotDao.selectSpotLikeByContentId(accountId, contentId);

		SpotDetailDto spotDetail = new SpotDetailDto.Builder()
				.setDetail(detail)
				.setLikeCount(likeCount)
				.setLikeState(likeState)
				.build();
			
		return spotDetail;
	}

	@Override
	public boolean addSpotLike(int accountId, int contentId) throws Exception {
		return spotDao.insertSpotLike(accountId, contentId);
	}

	@Override
	public boolean removeSpotLike(int accountId, int contentId) throws Exception {
		return spotDao.deleteSpotLike(accountId, contentId);
	}
}
