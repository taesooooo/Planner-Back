package com.planner.planner.Service;

import com.planner.planner.Dto.SpotDetailDto;
import com.planner.planner.Dto.SpotDto;
import com.planner.planner.Dto.SpotListDto;
import com.planner.planner.Dto.OpenApi.AreaCodeDto;

public interface SpotService {
	public SpotListDto<AreaCodeDto> getAreaNum() throws Exception;
	public SpotListDto<SpotDto> getAreaList(int accountId, int areaCode,int contentTypeId, int index) throws Exception;
	public SpotListDto<SpotDto> getLocationBasedList(int accountId, double mapX, double mapY, int radius, int index) throws Exception;
	public SpotListDto<SpotDto> getKeyword(int accountId, int areaCode,int contentTypeId,String keyword, int index) throws Exception;
	public SpotDetailDto getDetail(int accountId, int contentId) throws Exception;
	
	public boolean addSpotLike(int accountId, int contentId) throws Exception;
	public boolean removeSpotLike(int accountId, int contentId) throws Exception;
}