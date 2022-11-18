package com.planner.planner.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.planner.planner.Dto.OpenApi.AreaCodeDto;
import com.planner.planner.Dto.OpenApi.BasedDto;
import com.planner.planner.Dto.OpenApi.DetailCommonDto;
import com.planner.planner.Exception.OpenAPIDataEmpty;

@Service
public class OpenAPIServiceImpl implements OpenAPIService {

	private String baseUrl = "http://apis.data.go.kr/B551011/KorService";

	@Value("${openAPI.serviceKey}")
	private String serviceKey;
	private String mobileOS = "ETC";
	private String mobileApp = "planner";
	private int numOfRows = 10;

	@Override
	public List<AreaCodeDto> getAreaNum() throws Exception{
		String apiUrl = baseUrl+"/areaCode?ServiceKey="+serviceKey
				+"&MobileOS="+mobileOS
				+"&MobileApp="+mobileApp
				+"&numOfRows="+numOfRows
				+"&pageNo=1"
				+"&_type=json";

		ObjectNode data = getApiData(apiUrl);
		
		List<AreaCodeDto> areaList = new ArrayList<AreaCodeDto>();
		
		for (JsonNode root : data) {
			for(JsonNode node : root) {
				AreaCodeDto area = new AreaCodeDto.Builder().setRnum(node.get("rnum").asText()).setCode(node.get("code").asText()).setName(node.get("name").asText()).build();
				areaList.add(area);
			}
		}
		
		return areaList;
	}

	@Override
	public List<BasedDto> getAreaList(int areaCode, int contentTypeId, int index) throws Exception {
		String apiUrl = baseUrl+"/areaBasedList?ServiceKey="+serviceKey
				+"&MobileOS="+mobileOS
				+"&MobileApp="+mobileApp
				+"&numOfRows="+numOfRows
				+"&pageNo="+index
				+"&contentTypeId="+contentTypeId
				+"&areaCode="+areaCode
				+"&_type=json";

		ObjectNode data = getApiData(apiUrl);
		if(data == null) {
			throw new OpenAPIDataEmpty();
		}
		
		List<BasedDto> areaBasedList = new ArrayList<BasedDto>();
		
		for (JsonNode root : data) {
			for(JsonNode node : root) {
				BasedDto areaBased = new BasedDto.Builder()
						.setReadCount(node.get("readcount").asText())
						.setSigunguCode(node.get("sigungucode").asText())
						.setTel(node.get("tel").asText())
						.setTitle(node.get("title").asText())
						.setAddr1(node.get("addr1").asText())
						.setAddr2(node.get("addr2").asText())
						.setAreaCode(node.get("areacode").asText())
						.setBookTour(node.get("booktour").asText())
						.setCat1(node.get("cat1").asText())
						.setCat2(node.get("cat2").asText())
						.setCat3(node.get("cat3").asText())
						.setContentid(node.get("contentid").asText())
						.setContenttypeid(node.get("contenttypeid").asText())
						.setCreatedtime(node.get("createdtime").asText())
						.setFirstimage(node.get("firstimage").asText())
						.setFirstimage2(node.get("firstimage2").asText())
						.setMapx(node.get("mapx").asText())
						.setMapy(node.get("mapy").asText())
						.setMlevel(node.get("mlevel").asText())
						.setModifiedtime(node.get("modifiedtime").asText())
						.setZipcode(node.get("zipcode").asText())
						.build();
				areaBasedList.add(areaBased);
			}
		}
		
		return areaBasedList;
	}

	@Override
	public List<BasedDto> getLocationBasedList(double mapX, double mapY, int radius, int index) throws Exception {
		String apiUrl = baseUrl+"/areaBasedList?ServiceKey="+serviceKey
				+"&MobileOS="+mobileOS
				+"&MobileApp="+mobileApp
				+"&numOfRows="+numOfRows
				+"&pageNo="+index
				+"&mapX="+mapX
				+"&mapY="+mapY
				+"&radius="+radius
				+"&_type=json";

		ObjectNode data = getApiData(apiUrl);
		if(data == null) {
			throw new OpenAPIDataEmpty();
		}
		
		List<BasedDto> locationBasedList = new ArrayList<BasedDto>();
		
		for (JsonNode root : data) {
			for(JsonNode node : root) {
				BasedDto locationBased = new BasedDto.Builder()
						.setReadCount(node.get("readcount").asText())
						.setSigunguCode(node.get("sigungucode").asText())
						.setTel(node.get("tel").asText())
						.setTitle(node.get("title").asText())
						.setAddr1(node.get("addr1").asText())
						.setAddr2(node.get("addr2").asText())
						.setAreaCode(node.get("areacode").asText())
						.setBookTour(node.get("booktour").asText())
						.setCat1(node.get("cat1").asText())
						.setCat2(node.get("cat2").asText())
						.setCat3(node.get("cat3").asText())
						.setContentid(node.get("contentid").asText())
						.setContenttypeid(node.get("contenttypeid").asText())
						.setCreatedtime(node.get("createdtime").asText())
						.setFirstimage(node.get("firstimage").asText())
						.setFirstimage2(node.get("firstimage2").asText())
						.setMapx(node.get("mapx").asText())
						.setMapy(node.get("mapy").asText())
						.setMlevel(node.get("mlevel").asText())
						.setModifiedtime(node.get("modifiedtime").asText())
						.setZipcode(node.get("zipcode").asText())
						.build();
				locationBasedList.add(locationBased);
			}
		}
		
		return locationBasedList;
	}

	@Override
	public List<BasedDto> getKeyword(int areaCode, int contentTypeId, String keyword, int index) throws Exception
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
		
		ObjectNode data = getApiData(apiUrl);
		if(data == null) {
			throw new OpenAPIDataEmpty();
		}
		
		List<BasedDto> keywordBasedList = new ArrayList<BasedDto>();
		
		for (JsonNode root : data) {
			for(JsonNode node : root) {
				BasedDto keywordBased = new BasedDto.Builder()
						.setReadCount(node.get("readcount").asText())
						.setSigunguCode(node.get("sigungucode").asText())
						.setTel(node.get("tel").asText())
						.setTitle(node.get("title").asText())
						.setAddr1(node.get("addr1").asText())
						.setAddr2(node.get("addr2").asText())
						.setAreaCode(node.get("areacode").asText())
						.setBookTour(node.get("booktour").asText())
						.setCat1(node.get("cat1").asText())
						.setCat2(node.get("cat2").asText())
						.setCat3(node.get("cat3").asText())
						.setContentid(node.get("contentid").asText())
						.setContenttypeid(node.get("contenttypeid").asText())
						.setCreatedtime(node.get("createdtime").asText())
						.setFirstimage(node.get("firstimage").asText())
						.setFirstimage2(node.get("firstimage2").asText())
						.setMapx(node.get("mapx").asText())
						.setMapy(node.get("mapy").asText())
						.setMlevel(node.get("mlevel").asText())
						.setModifiedtime(node.get("modifiedtime").asText())
						.build();
				keywordBasedList.add(keywordBased);
			}
		}
		
		
		return keywordBasedList;
	}

	@Override
	public DetailCommonDto getDetail(int contentId) throws Exception
	{
		ObjectNode data = null;

		String apiUrl = baseUrl+"/detailCommon?ServiceKey="+serviceKey
				+"&MobileOS="+mobileOS
				+"&MobileApp="+mobileApp
				+"&contentId="+contentId
				+"&defaultYN=Y"
				+"&firstImageYN=Y"
				+"&addrinfoYN=Y"
				+"&mapinfoYN=Y"
				+"&overviewYN=Y"
				+"&_type=json";

		data = getApiData(apiUrl);
		if(data == null) {
			throw new OpenAPIDataEmpty();
		}
		
		JsonNode node = data.get("item").get(0);
		
		DetailCommonDto keywordBased = new DetailCommonDto.Builder()
				.setHomepage(node.get("homepage").asText())
				.setTelname(node.get("telname").asText())
				.setZipcode(node.get("zipcode").asText())
				.setOverview(node.get("overview").asText())
				.build();

		return keywordBased;
	}

	@Override
	public ObjectNode getApiData(String url) throws Exception
	{
		ObjectMapper om = new ObjectMapper();
		ObjectNode data = null;

		URL uri = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) uri.openConnection();

		if(conn.getResponseCode() == 200)
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			JsonNode node = om.readTree(reader.readLine());

			int totalCount = node.get("response").get("body").get("totalCount").asInt();
			if(totalCount == 0) {
				return null;
			}
			// response > body > items
			JsonNode dataNode = node.get("response").get("body").get("items");
			data = ((ObjectNode) dataNode).put("totalCount", totalCount);
		}
		else {
			return null;
		}
		
		return data;
	}

}
