package com.planner.planner.Service;

import java.util.List;

import com.planner.planner.Dto.SpotDetailDto;
import com.planner.planner.Dto.SpotDto;
import com.planner.planner.Dto.SpotLikeCountDto;
import com.planner.planner.Dto.SpotListDto;
import com.planner.planner.Dto.OpenApi.AreaCodeDto;

public interface SpotService {
	public SpotListDto<AreaCodeDto> getAreaNum() throws Exception;
	public SpotListDto<SpotDto> getAreaList(int areaCode,int contentTypeId, int index) throws Exception;
	public SpotListDto<SpotDto> getLocationBasedList(double mapX, double mapY, int radius, int index) throws Exception;
	public SpotListDto<SpotDto> getKeyword(int areaCode,int contentTypeId,String keyword, int index) throws Exception;
	public SpotDetailDto getDetail(int contentId) throws Exception;
	
	public boolean spotLike(int accountId, int contentId);
	public boolean spotLikeCancel(int accountId, int contentId);
	public SpotLikeCountDto spotLikeCount(int contentIds);
}