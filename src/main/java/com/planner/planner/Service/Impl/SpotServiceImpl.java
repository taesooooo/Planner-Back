package com.planner.planner.Service.Impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planner.planner.Common.Page;
import com.planner.planner.Common.PageInfo;
import com.planner.planner.Dao.SpotDao;
import com.planner.planner.Dto.CommonRequestParamDto;
import com.planner.planner.Dto.SpotDetailDto;
import com.planner.planner.Dto.SpotDto;
import com.planner.planner.Dto.SpotLikeCountDto;
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
	public SpotListDto<SpotDto> getAreaList(Integer accountId, OpenApiDto openApiDto) throws Exception {
		CommonListDto<CommonBasedDto> apiData = apiService.getAreaList(openApiDto);
		
		List<Integer> contentIdList = apiData.getItems().stream()
				.map(item -> Integer.parseInt(item.getContentId()))
				.collect(Collectors.toList());
		
		List<SpotLikeDto> spotLikeList = spotDao.selectSpotLikeByContentIdList(accountId, contentIdList);
		List<SpotLikeCountDto> spotLikeCountList = spotDao.selectSpotLikeCountByContentIdList(contentIdList);

		List<SpotDto> spotList = apiData.getItems().stream()
				.map(item -> {
					boolean likeState = spotLikeList.stream()
							.anyMatch(likeItem -> likeItem.getContentId() == Integer.parseInt(item.getContentId()));

					Optional<SpotLikeCountDto> likeCountDto = spotLikeCountList.stream()
							.filter(likeCountItem -> likeCountItem.getContentId() == Integer.parseInt(item.getContentId()))
							.findFirst();
					
					int likeCount = 0;
					if(likeCountDto.isPresent()) {
						likeCount = likeCountDto.get().getCount();
					}

					SpotDto spot = new SpotDto.Builder()
							.setBasedSpot(item)
							.setLikeCount(likeCount)
							.setLikeState(likeState)
							.build();

					return spot;
		})
		.collect(Collectors.toList());
		
		SpotListDto<SpotDto> list = new SpotListDto<SpotDto>(spotList, apiData.getTotalCount());
				
		return list;
	}

	@Override
	public SpotListDto<SpotDto> getLocationBasedList(Integer accountId,OpenApiDto openApiDto) throws Exception {
		CommonListDto<CommonBasedDto> apiData = apiService.getLocationBasedList(openApiDto);

		List<Integer> contentIdList = apiData.getItems().stream()
				.map(item -> Integer.parseInt(item.getContentId()))
				.collect(Collectors.toList());
		
		List<SpotLikeDto> spotLikeList = spotDao.selectSpotLikeByContentIdList(accountId, contentIdList);
		List<SpotLikeCountDto> spotLikeCountList = spotDao.selectSpotLikeCountByContentIdList(contentIdList);

		List<SpotDto> spotList = apiData.getItems().stream()
				.map(item -> {
				boolean likeState = spotLikeList.stream()
						.anyMatch(likeItem -> likeItem.getContentId() == Integer.parseInt(item.getContentId()));
	
				Optional<SpotLikeCountDto> likeCountDto = spotLikeCountList.stream()
						.filter(likeCountItem -> likeCountItem.getContentId() == Integer.parseInt(item.getContentId()))
						.findFirst();
	
				int likeCount = 0;
				if (likeCountDto.isPresent()) {
					likeCount = likeCountDto.get().getCount();
				}
	
				SpotDto spot = new SpotDto.Builder()
						.setBasedSpot(item)
						.setLikeCount(likeCount)
						.setLikeState(likeState)
						.build();
	
				return spot;
		}).collect(Collectors.toList());
		
		SpotListDto<SpotDto> list = new SpotListDto<SpotDto>(spotList, apiData.getTotalCount());
				
		return list;
	}

	@Override
	public SpotListDto<SpotDto> getKeyword(Integer accountId, OpenApiDto openApiDto) throws Exception {
		CommonListDto<CommonBasedDto> apiData = apiService.getKeyword(openApiDto);
		
		List<Integer> contentIdList = apiData.getItems().stream()
				.map(item -> Integer.parseInt(item.getContentId()))
				.collect(Collectors.toList());
		
		List<SpotLikeDto> spotLikeList = spotDao.selectSpotLikeByContentIdList(accountId, contentIdList);
		List<SpotLikeCountDto> spotLikeCountList = spotDao.selectSpotLikeCountByContentIdList(contentIdList);
			
		List<SpotDto> spotList = apiData.getItems().stream()
				.map(item -> {
					boolean likeState = spotLikeList.stream()
							.anyMatch(likeItem -> likeItem.getContentId() == Integer.parseInt(item.getContentId()));
					
					Optional<SpotLikeCountDto> likeCountDto = spotLikeCountList.stream()
							.filter(likeCountItem -> likeCountItem.getContentId() == Integer.parseInt(item.getContentId()))
							.findFirst();
		
					int likeCount = 0;
					if (likeCountDto.isPresent()) {
						likeCount = likeCountDto.get().getCount();
					}

					SpotDto spot = new SpotDto.Builder()
							.setBasedSpot(item)
							.setLikeCount(likeCount)
							.setLikeState(likeState)
							.build();

					return spot;
		})
		.collect(Collectors.toList());
		
		SpotListDto<SpotDto> list = new SpotListDto<SpotDto>(spotList, apiData.getTotalCount());
				
		return list;
	}

	@Override
	public SpotDetailDto getDetail(Integer accountId, int contentId) throws Exception {
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
	public boolean addSpotLike(int accountId, SpotLikeDto spotLikeDto) throws Exception {
		return spotDao.insertSpotLike(accountId, spotLikeDto);
	}

	@Override
	public boolean removeSpotLike(int accountId, int contentId) throws Exception {
		return spotDao.deleteSpotLike(accountId, contentId);
	}

	@Override
	public Page<SpotLikeDto> getSpotLikeList(int accountId, CommonRequestParamDto commonRequestParamDto) throws Exception {
		PageInfo pageInfo = new PageInfo.Builder()
				.setPageNum(commonRequestParamDto.getPageNum())
				.setPageItemCount(commonRequestParamDto.getItemCount())
				.build();
		
		List<SpotLikeDto> spotLikelist = spotDao.selectSpotLikeList(accountId, commonRequestParamDto.getSortCriteria(), commonRequestParamDto.getKeyword(), pageInfo);
		
		int totalCount = 0;
		String keyword = commonRequestParamDto.getKeyword();
		
		if(keyword != null && !keyword.isEmpty()) {
			totalCount = spotDao.getTotalCountByAccountId(accountId, keyword);
		}
		else {
			totalCount = spotDao.getTotalCountByAccountId(accountId);
		}
		
		Page<SpotLikeDto> spotLikePage = new Page.Builder<SpotLikeDto>()
				.setList(spotLikelist)
				.setPageInfo(pageInfo)
				.setTotalCount(totalCount)
				.build();

		return spotLikePage;
	}
}
