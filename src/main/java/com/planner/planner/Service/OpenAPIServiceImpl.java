package com.planner.planner.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class OpenAPIServiceImpl implements OpenAPIService {

	private String serviceKey = "QpWblNAbNFefMYdShDqp0elSoLQQ5xs0Ux6wWDIf0slglvoTxEreHROiRDHwbJ5O0iGuF3K4S7C8r43T5j7mkg%3D%3D";
	private String mobileOS = "ETC";
	private String mobileApp = "planner";
	private int numOfRows = 10;

	@Override
	public ObjectNode getAreaNum()
	{
		String apiUrl = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaCode?ServiceKey="+serviceKey
				+"&MobileOS="+mobileOS
				+"&MobileApp="+mobileApp
				+"&numOfRows="+numOfRows
				+"&pageNo=1"
				+"&_type=json";
		
		ObjectNode data = getApiData(apiUrl);
		return data;
	}

	@Override
	public ObjectNode getTouristAreaList(int areaCode, int contentTypeId, int index)
	{
		String apiUrl = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList?ServiceKey="+serviceKey
				+"&MobileOS="+mobileOS
				+"&MobileApp="+mobileApp
				+"&numOfRows="+numOfRows
				+"&pageNo="+index
				+"&contentTypeId="+contentTypeId
				+"&areaCode="+areaCode
				+"&_type=json";
		
		ObjectNode data = getApiData(apiUrl);
		return data;
	}
	

	@Override
	public ObjectNode getTouristKeyword(int areaCode, int contentTypeId, String keyword, int index)
	{
		ObjectNode data = null;
		try
		{
			String apiUrl = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/searchKeyword?ServiceKey="+serviceKey
					+"&MobileOS="+mobileOS
					+"&MobileApp="+mobileApp
					+"&numOfRows="+numOfRows
					+"&pageNo="+index
					+"&contentTypeId="+contentTypeId
					+"&areaCode="+areaCode
					+"&keyword="+URLEncoder.encode(keyword, "UTF-8")
					+"&_type=json";
			data = getApiData(apiUrl);
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
	public ObjectNode getTouristDetail(int contentId)
	{
		ObjectNode data = null;

		String apiUrl = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon?ServiceKey="+serviceKey
				+"&MobileOS="+mobileOS
				+"&MobileApp="+mobileApp
				+"&contentId="+contentId
				+"&defaultYN=Y"
				+"&firstImageYN=Y"
				+"&addrinfoYN=Y"
				+"&overviewYN=Y"
				+"&_type=json";
		
		data = getApiData(apiUrl);
		System.out.println(apiUrl);
		return data;
	}

	@Override
	public ObjectNode getApiData(String url)
	{
		ObjectMapper om = new ObjectMapper();
		ObjectNode data = null;
		try
		{
			URL uri = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) uri.openConnection();

			if(conn.getResponseCode() == 200)
			{
				BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				JsonNode node = om.readTree(reader.readLine());
				
				int totalCount = node.get("response").get("body").get("totalCount").asInt();
				if(totalCount == 0) {
					return new ObjectNode(JsonNodeFactory.instance).putObject("error").put("code", 404).put("message", "데이터가 존재하지 않습니다.");
				}
				// response > body > items
				JsonNode dataNode = node.get("response").get("body").get("items");
				data = ((ObjectNode) dataNode).put("totalCount", totalCount);
			}
			else {
				return new ObjectNode(JsonNodeFactory.instance).putObject("error").put("code", conn.getResponseCode()).put("message", conn.getResponseMessage());
			}
		}
		catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		return data;
	}

}
