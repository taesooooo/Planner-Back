package com.planner.planner.Service;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface OpenAPIService {
	public ObjectNode getAreaNum();
	public ObjectNode getAreaList(int areaCode,int contentTypeId, int index);
	public ObjectNode getLocationBasedList(double mapX, double mapY, int radius, int index);
	public ObjectNode getKeyword(int areaCode,int contentTypeId,String keyword, int index);
	public ObjectNode getDetail(int contentId);
	public ObjectNode getApiData(String url);
}
