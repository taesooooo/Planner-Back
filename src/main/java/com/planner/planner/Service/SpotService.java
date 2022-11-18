package com.planner.planner.Service;

import java.util.List;

import com.planner.planner.Dto.SpotDto;
import com.planner.planner.Dto.SpotLikeCountDto;
import com.planner.planner.Dto.OpenApi.AreaCodeDto;
import com.planner.planner.Dto.OpenApi.DetailCommonDto;

public interface SpotService {
	public List<AreaCodeDto> getAreaNum() throws Exception;
	public List<SpotDto> getAreaList(int areaCode,int contentTypeId, int index) throws Exception;
	public List<SpotDto> getLocationBasedList(double mapX, double mapY, int radius, int index) throws Exception;
	public List<SpotDto> getKeyword(int areaCode,int contentTypeId,String keyword, int index) throws Exception;
	public DetailCommonDto getDetail(int contentId) throws Exception;
	
	public boolean spotLike(int accountId, int contentId);
	public boolean spotLikeCancel(int accountId, int contentId);
	public List<SpotLikeCountDto> spotLikeCount(String contentIds);
}