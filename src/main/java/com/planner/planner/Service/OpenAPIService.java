package com.planner.planner.Service;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface OpenAPIService {
	public ObjectNode getAreaNum();
	public ObjectNode getTouristAreaList(int areaCode,int contentTypeId, int index);
	public ObjectNode getTouristKeyword(int areaCode,int contentTypeId,String keyword, int index);
	public ObjectNode getTouristDetail(int contentId);
	public ObjectNode getApiData(String url);
}
