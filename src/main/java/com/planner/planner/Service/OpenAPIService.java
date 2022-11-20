package com.planner.planner.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.planner.planner.Dto.OpenApi.AreaCodeDto;
import com.planner.planner.Dto.OpenApi.CommonBasedDto;
import com.planner.planner.Dto.OpenApi.CommonDetailDto;
import com.planner.planner.Dto.OpenApi.CommonListDto;

public interface OpenAPIService {
	public CommonListDto<AreaCodeDto> getAreaNum() throws Exception;
	public CommonListDto<CommonBasedDto> getAreaList(int areaCode,int contentTypeId, int index) throws Exception;
	public CommonListDto<CommonBasedDto> getLocationBasedList(double mapX, double mapY, int radius, int index) throws Exception;
	public CommonListDto<CommonBasedDto> getKeyword(int areaCode,int contentTypeId,String keyword, int index) throws Exception;
	public CommonDetailDto getDetail(int contentId) throws Exception;
	public JsonNode getApiData(String url) throws Exception;
}
