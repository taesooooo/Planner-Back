package com.planner.planner.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planner.planner.Dao.SpotDao;
import com.planner.planner.Dto.SpotDto;
import com.planner.planner.Dto.SpotLikeCountDto;
import com.planner.planner.Dto.OpenApi.AreaCodeDto;
import com.planner.planner.Dto.OpenApi.BasedDto;
import com.planner.planner.Dto.OpenApi.DetailCommonDto;
import com.planner.planner.Entity.SpotLikeCount;

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
	public List<AreaCodeDto> getAreaNum() throws Exception {
		return apiService.getAreaNum();
	}

	@Override
	public List<SpotDto> getAreaList(int areaCode, int contentTypeId, int index) throws Exception {
		List<BasedDto> spots = apiService.getAreaList(areaCode, contentTypeId, index);
		String contentIds = spots.stream().map((spot) -> spot.getContentid()).collect(Collectors.joining(","));

		List<SpotLikeCountDto> counts = spotDao.spotLikeCount(contentIds).stream().map((i) -> SpotLikeCountDto.form(i)).collect(Collectors.toList());

		List<SpotDto> spotList = new ArrayList<SpotDto>();

		for (BasedDto spot : spots) {
			int likeCount = 0;
			if(!counts.isEmpty()) {
				for(SpotLikeCountDto count : counts) {
					if(count.getCotentId() == Integer.parseInt(spot.getContentid())) {
						likeCount = count.getLikeCount();
						break;
					}
				}
			}

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
					.setLikeCount(likeCount)
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

		return spotList;
	}

	@Override
	public List<SpotDto> getLocationBasedList(double mapX, double mapY, int radius, int index) throws Exception {
		List<BasedDto> spots = apiService.getLocationBasedList(mapX, mapY, radius, index);

		String contentIds = spots.stream().map((spot) -> spot.getContentid()).collect(Collectors.joining(","));

		List<SpotLikeCountDto> counts = spotLikeCount(contentIds);

		List<SpotDto> spotList = new ArrayList<SpotDto>();

		for (BasedDto spot : spots) {
			int likeCount = 0;
			if(!counts.isEmpty()) {
				for(SpotLikeCountDto count : counts) {
					if(count.getCotentId() == Integer.parseInt(spot.getContentid())) {
						likeCount = count.getLikeCount();
						break;
					}
				}
			}

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
					.setLikeCount(likeCount)
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

		return spotList;
	}

	@Override
	public List<SpotDto> getKeyword(int areaCode, int contentTypeId, String keyword, int index) throws Exception {
		List<BasedDto> spots = apiService.getKeyword(areaCode, contentTypeId, keyword, index);
		String contentIds = spots.stream().map((spot) -> spot.getContentid()).collect(Collectors.joining(","));

		List<SpotLikeCountDto> counts = spotLikeCount(contentIds);

		List<SpotDto> spotList = new ArrayList<SpotDto>();

		for (BasedDto spot : spots) {
			int likeCount = 0;
			if(!counts.isEmpty()) {
				for(SpotLikeCountDto count : counts) {
					if(count.getCotentId() == Integer.parseInt(spot.getContentid())) {
						likeCount = count.getLikeCount();
						break;
					}
				}
			}

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
					.setLikeCount(likeCount)
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

		return spotList;
	}

	@Override
	public DetailCommonDto getDetail(int contentId) throws Exception {
		DetailCommonDto detail = apiService.getDetail(contentId);
		return detail;
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
	public List<SpotLikeCountDto> spotLikeCount(String contentIds) {
		return spotDao.spotLikeCount(contentIds).stream().map((i) -> SpotLikeCountDto.form(i)).collect(Collectors.toList());
	}

}
