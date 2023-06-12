package com.planner.planner.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.planner.planner.Dto.OpenApi.AreaCodeDto;
import com.planner.planner.Dto.OpenApi.CommonBasedDto;
import com.planner.planner.Dto.OpenApi.CommonDetailDto;
import com.planner.planner.Dto.OpenApi.CommonListDto;
import com.planner.planner.Dto.OpenApi.OpenApiDto;

public interface OpenAPIService {
	public CommonListDto<AreaCodeDto> getAreaNum() throws Exception;
	public CommonListDto<CommonBasedDto> getAreaList(OpenApiDto openApiDto) throws Exception;
	public CommonListDto<CommonBasedDto> getLocationBasedList(OpenApiDto openApiDto) throws Exception;
	public CommonListDto<CommonBasedDto> getKeyword(OpenApiDto openApiDto) throws Exception;
	public CommonDetailDto getDetail(int contentId) throws Exception;
	public JsonNode getApiData(String url) throws Exception;
}
