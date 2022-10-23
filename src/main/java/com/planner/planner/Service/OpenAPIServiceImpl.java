package com.planner.planner.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.planner.planner.Dao.SpotDao;
import com.planner.planner.Dto.SpotDto;
import com.planner.planner.Dto.SpotLikeCountDto;
import com.planner.planner.util.OpenAPIUtil;

import net.minidev.json.JSONObject;

@Service
public class OpenAPIServiceImpl implements OpenAPIService {

	private String baseUrl = "http://apis.data.go.kr/B551011/KorService";

	@Value("${openAPI.serviceKey}")
	private String serviceKey;
	private String mobileOS = "ETC";
	private String mobileApp = "planner";
	private int numOfRows = 10;
	
	private SpotDao spotDao;
	
	public OpenAPIServiceImpl(SpotDao spotDao) {
		this.spotDao = spotDao;
	}

	@Override
	public ObjectNode getAreaNum() throws Exception
	{
		String apiUrl = baseUrl+"/areaCode?ServiceKey="+serviceKey
				+"&MobileOS="+mobileOS
				+"&MobileApp="+mobileApp
				+"&numOfRows="+numOfRows
				+"&pageNo=1"
				+"&_type=json";

		ObjectNode data = OpenAPIUtil.getApiData(apiUrl);
		return data;
	}

	@Override
	public List<SpotDto> getAreaList(int areaCode, int contentTypeId, int index) throws Exception
	{
		String apiUrl = baseUrl+"/areaBasedList?ServiceKey="+serviceKey
				+"&MobileOS="+mobileOS
				+"&MobileApp="+mobileApp
				+"&numOfRows="+numOfRows
				+"&pageNo="+index
				+"&contentTypeId="+contentTypeId
				+"&areaCode="+areaCode
				+"&_type=json";

		ObjectNode data = OpenAPIUtil.getApiData(apiUrl);
		
		List<String> contentIds = new ArrayList<String>();
		for(JsonNode node : data.get("item")) {
			contentIds.add(node.get("contentid").toString());
		}
		
		List<SpotLikeCountDto> likeCounts = spotDao.spotLikeCount(contentIds.stream().collect(Collectors.joining(",")));
		List<SpotDto> spotList = new ArrayList<SpotDto>();
		
		for(int i=0;i<data.size();i++) {
			JsonNode node = data.get("item").get(i);
			if(!likeCounts.isEmpty()) {				
				for(int j=0;j<contentIds.size();j++) {
					SpotLikeCountDto spotLikeCount = likeCounts.get(j);String.valueOf(likeCounts.get(j).getConetntId());
					if(node.get("contentid").toString().equals(String.valueOf(spotLikeCount.getConetntId()))) {					
						spotList.add(new SpotDto.Builder().setItem(node.toString()).setlikeCount(spotLikeCount.getLikeCount()).build());
					}
				}
			}
			else {
				spotList.add(new SpotDto.Builder().setItem(node.toString()).setlikeCount(0).build());
			}
		}
		
		return spotList;
	}

	@Override
	public ObjectNode getLocationBasedList(double mapX, double mapY, int radius, int index) throws Exception {
		String apiUrl = baseUrl+"/areaBasedList?ServiceKey="+serviceKey
				+"&MobileOS="+mobileOS
				+"&MobileApp="+mobileApp
				+"&numOfRows="+numOfRows
				+"&pageNo="+index
				+"&mapX="+mapX
				+"&mapY="+mapY
				+"&radius="+radius
				+"&_type=json";

		ObjectNode data = OpenAPIUtil.getApiData(apiUrl);
		return data;
	}

	@Override
	public ObjectNode getKeyword(int areaCode, int contentTypeId, String keyword, int index) throws Exception
	{
		ObjectNode data = null;
		try
		{
			String apiUrl = baseUrl+"/searchKeyword?ServiceKey="+serviceKey
					+"&MobileOS="+mobileOS
					+"&MobileApp="+mobileApp
					+"&numOfRows="+numOfRows
					+"&pageNo="+index
					+"&contentTypeId="+contentTypeId
					+"&areaCode="+areaCode
					+"&keyword="+URLEncoder.encode(keyword, "UTF-8")
					+"&_type=json";
			data = OpenAPIUtil.getApiData(apiUrl);
		}
		catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return data;
	}

	@Override
	public ObjectNode getDetail(int contentId) throws Exception
	{
		ObjectNode data = null;

		String apiUrl = baseUrl+"/detailCommon?ServiceKey="+serviceKey
				+"&MobileOS="+mobileOS
				+"&MobileApp="+mobileApp
				+"&contentId="+contentId
				+"&defaultYN=Y"
				+"&firstImageYN=Y"
				+"&addrinfoYN=Y"
				+"&overviewYN=Y"
				+"&_type=json";

		data = OpenAPIUtil.getApiData(apiUrl);

		return data;
	}

}
