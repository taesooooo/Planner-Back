package com.planner.planner.Service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planner.planner.Dao.SpotDao;
import com.planner.planner.Dto.SpotDetailDto;
import com.planner.planner.Dto.SpotDto;
import com.planner.planner.Dto.SpotLikeCountDto;
import com.planner.planner.Dto.SpotListDto;
import com.planner.planner.Dto.OpenApi.AreaCodeDto;
import com.planner.planner.Dto.OpenApi.CommonBasedDto;
import com.planner.planner.Dto.OpenApi.CommonDetailDto;
import com.planner.planner.Dto.OpenApi.CommonListDto;
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
	public SpotListDto<SpotDto> getAreaList(int areaCode, int contentTypeId, int index) throws Exception {
		CommonListDto<CommonBasedDto> apiData = apiService.getAreaList(areaCode, contentTypeId, index);

		List<SpotDto> spotList = new ArrayList<SpotDto>();

		for (CommonBasedDto spot : apiData.getItems()) {
		

			SpotDto item = new SpotDto.Builder()
					.setAddr1(spot.getAddr1())
					.setAddr2(spot.getAddr2())
					.setAreaCode(spot.getAreaCode())
					.setBookTour(spot.getBookTour())
					.setCat1(spot.getCat1())
					.setCat2(spot.getCat2())
					.setCat3(spot.getCat3())
					.setContentid(spot.getContentid())
					.setContenttypeid(spot.getContenttypeid())
					.setCreatedtime(spot.getCreatedtime())
					.setFirstimage(spot.getFirstimage())
					.setFirstimage2(spot.getFirstimage2())
					//.setLikeCount(likeCount)
					.setMapx(spot.getMapx())
					.setMapy(spot.getMapy())
					.setMlevel(spot.getMlevel())
					.setModifiedtime(spot.getModifiedtime())
					.setReadCount(spot.getReadCount())
					.setSigunguCode(spot.getSigunguCode())
					.setTel(spot.getTel())
					.setTitle(spot.getTitle())
					.build();
			spotList.add(item);
		}
		
		SpotListDto<SpotDto> list = new SpotListDto<SpotDto>(spotList,apiData.getTotalCount());
				
		return list;
	}

	@Override
	public SpotListDto<SpotDto> getLocationBasedList(double mapX, double mapY, int radius, int index) throws Exception {
		CommonListDto<CommonBasedDto> apiData = apiService.getLocationBasedList(mapX, mapY, radius, index);

		List<SpotDto> spotList = new ArrayList<SpotDto>();

		for (CommonBasedDto spot : apiData.getItems()) {
			SpotDto item = new SpotDto.Builder()
					.setAddr1(spot.getAddr1())
					.setAddr2(spot.getAddr2())
					.setAreaCode(spot.getAreaCode())
					.setBookTour(spot.getBookTour())
					.setCat1(spot.getCat1())
					.setCat2(spot.getCat2())
					.setCat3(spot.getCat3())
					.setContentid(spot.getContentid())
					.setContenttypeid(spot.getContenttypeid())
					.setCreatedtime(spot.getCreatedtime())
					.setFirstimage(spot.getFirstimage())
					.setFirstimage2(spot.getFirstimage2())
					//.setLikeCount(likeCount)
					.setMapx(spot.getMapx())
					.setMapy(spot.getMapy())
					.setMlevel(spot.getMlevel())
					.setModifiedtime(spot.getModifiedtime())
					.setReadCount(spot.getReadCount())
					.setSigunguCode(spot.getSigunguCode())
					.setTel(spot.getTel())
					.setTitle(spot.getTitle())
					.build();
			spotList.add(item);
		}
		
		SpotListDto<SpotDto> list = new SpotListDto<SpotDto>(spotList, apiData.getTotalCount());

		return list;
	}

	@Override
	public SpotListDto<SpotDto> getKeyword(int areaCode, int contentTypeId, String keyword, int index) throws Exception {
		CommonListDto<CommonBasedDto> apiData = apiService.getKeyword(areaCode, contentTypeId, keyword, index);
		
		List<SpotDto> spotList = new ArrayList<SpotDto>();

		for (CommonBasedDto spot : apiData.getItems()) {
			SpotDto item = new SpotDto.Builder()
					.setAddr1(spot.getAddr1())
					.setAddr2(spot.getAddr2())
					.setAreaCode(spot.getAreaCode())
					.setBookTour(spot.getBookTour())
					.setCat1(spot.getCat1())
					.setCat2(spot.getCat2())
					.setCat3(spot.getCat3())
					.setContentid(spot.getContentid())
					.setContenttypeid(spot.getContenttypeid())
					.setCreatedtime(spot.getCreatedtime())
					.setFirstimage(spot.getFirstimage())
					.setFirstimage2(spot.getFirstimage2())
					//.setLikeCount(likeCount)
					.setMapx(spot.getMapx())
					.setMapy(spot.getMapy())
					.setMlevel(spot.getMlevel())
					.setModifiedtime(spot.getModifiedtime())
					.setReadCount(spot.getReadCount())
					.setSigunguCode(spot.getSigunguCode())
					.setTel(spot.getTel())
					.setTitle(spot.getTitle())
					.build();
			spotList.add(item);
		}
		
		SpotListDto<SpotDto> list = new SpotListDto<SpotDto>(spotList, apiData.getTotalCount());

		return list;
	}

	@Override
	public SpotDetailDto getDetail(int contentId) throws Exception {
		CommonDetailDto detail = apiService.getDetail(contentId);
		//String contentIds = apiData.getItems().stream().map((spot) -> spot.getContentid()).collect(Collectors.joining(","));
		int likeCount = 0;
		
		SpotLikeCountDto spotLikeCount = spotDao.spotLikeCount(contentId);
		if(spotLikeCount != null) {
			likeCount = spotLikeCount.getLikeCount();
		}
		
		SpotDetailDto spotDetail = new SpotDetailDto.Builder()
				.setHomepage(detail.getHomepage())
				.setLikeCount(likeCount)
				.setOverview(detail.getOverview())
				.setTelname(detail.getTelname())
				.setZipcode(detail.getZipcode())
				.build();
			
		return spotDetail;
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
	public SpotLikeCountDto spotLikeCount(int contentIds) {
		return spotDao.spotLikeCount(contentIds);
	}

}
