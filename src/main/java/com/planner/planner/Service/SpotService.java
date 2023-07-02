package com.planner.planner.Service;

import com.planner.planner.Common.Page;
import com.planner.planner.Dto.CommonRequestParamDto;
import com.planner.planner.Dto.SpotDetailDto;
import com.planner.planner.Dto.SpotDto;
import com.planner.planner.Dto.SpotLikeDto;
import com.planner.planner.Dto.SpotListDto;
import com.planner.planner.Dto.OpenApi.AreaCodeDto;
import com.planner.planner.Dto.OpenApi.OpenApiDto;

public interface SpotService {
	public SpotListDto<AreaCodeDto> getAreaNum() throws Exception;
	public SpotListDto<SpotDto> getAreaList(Integer accountId, OpenApiDto openApiDto) throws Exception;
	public SpotListDto<SpotDto> getLocationBasedList(Integer accountId, OpenApiDto openApiDto) throws Exception;
	public SpotListDto<SpotDto> getKeyword(Integer accountId, OpenApiDto openApiDto) throws Exception;
	public SpotDetailDto getDetail(Integer accountId, int contentId) throws Exception;
	
	public boolean addSpotLike(int accountId, SpotLikeDto spotLikeDto) throws Exception;
	public boolean removeSpotLike(int accountId, int contentId) throws Exception;
	public Page<SpotLikeDto> getSpotLikeList(int accountId, CommonRequestParamDto commonRequestParamDto) throws Exception;
}