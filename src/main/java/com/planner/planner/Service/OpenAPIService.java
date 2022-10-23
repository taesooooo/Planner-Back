package com.planner.planner.Service;

import java.util.List;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.planner.planner.Dto.SpotDto;

public interface OpenAPIService {
	public ObjectNode getAreaNum() throws Exception;
	public List<SpotDto> getAreaList(int areaCode,int contentTypeId, int index) throws Exception;
	public ObjectNode getLocationBasedList(double mapX, double mapY, int radius, int index) throws Exception;
	public ObjectNode getKeyword(int areaCode,int contentTypeId,String keyword, int index) throws Exception;
	public ObjectNode getDetail(int contentId) throws Exception;
}
