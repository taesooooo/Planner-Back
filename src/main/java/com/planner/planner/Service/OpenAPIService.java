package com.planner.planner.Service;

import java.util.List;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.planner.planner.Dto.OpenApi.AreaCodeDto;
import com.planner.planner.Dto.OpenApi.BasedDto;
import com.planner.planner.Dto.OpenApi.DetailCommonDto;

public interface OpenAPIService {
	public List<AreaCodeDto> getAreaNum() throws Exception;
	public List<BasedDto> getAreaList(int areaCode,int contentTypeId, int index) throws Exception;
	public List<BasedDto> getLocationBasedList(double mapX, double mapY, int radius, int index) throws Exception;
	public List<BasedDto> getKeyword(int areaCode,int contentTypeId,String keyword, int index) throws Exception;
	public DetailCommonDto getDetail(int contentId) throws Exception;
	public ObjectNode getApiData(String url) throws Exception;
}
