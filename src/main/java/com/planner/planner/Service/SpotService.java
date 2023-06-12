package com.planner.planner.Service;

import com.planner.planner.Dto.SpotDetailDto;
import com.planner.planner.Dto.SpotDto;
import com.planner.planner.Dto.SpotListDto;
import com.planner.planner.Dto.OpenApi.AreaCodeDto;
import com.planner.planner.Dto.OpenApi.OpenApiDto;

public interface SpotService {
	public SpotListDto<AreaCodeDto> getAreaNum() throws Exception;
	public SpotListDto<SpotDto> getAreaList(int accountId, OpenApiDto openApiDto) throws Exception;
	public SpotListDto<SpotDto> getLocationBasedList(int accountId, OpenApiDto openApiDto) throws Exception;
	public SpotListDto<SpotDto> getKeyword(int accountId, OpenApiDto openApiDto) throws Exception;
	public SpotDetailDto getDetail(int accountId, int contentId) throws Exception;
	
	public boolean addSpotLike(int accountId, int contentId) throws Exception;
	public boolean removeSpotLike(int accountId, int contentId) throws Exception;
}