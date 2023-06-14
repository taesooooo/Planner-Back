package com.planner.planner.Service.Impl;

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
import com.planner.planner.Dto.OpenApi.AreaCodeDto;
import com.planner.planner.Dto.OpenApi.CommonBasedDto;
import com.planner.planner.Dto.OpenApi.CommonDetailDto;
import com.planner.planner.Dto.OpenApi.CommonListDto;
import com.planner.planner.Dto.OpenApi.OpenApiDto;
import com.planner.planner.Exception.EmptyData;
import com.planner.planner.Service.OpenAPIService;

@Service
public class OpenAPIServiceImpl implements OpenAPIService {

	private String baseUrl = "http://apis.data.go.kr/B551011/KorService1";

	@Value("${openAPI.serviceKey}")
	private String serviceKey;
	private String mobileOS = "ETC";
	private String mobileApp = "planner";
	// private int numOfRows = 10; 요청으로 받는 값으로 변경

	@Override
	public CommonListDto<AreaCodeDto> getAreaNum() throws Exception{
		String apiUrl = baseUrl+"/areaCode1?ServiceKey="+serviceKey
				+"&MobileOS="+mobileOS
				+"&MobileApp="+mobileApp
				+"&numOfRows=17"
				+"&pageNo=1"
				+"&_type=json";

		JsonNode data = getApiData(apiUrl);
		if(data == null) {
			throw new EmptyData();
		}
		
		int numOfRows = data.get("numOfRows").asInt();
		int pageNo = data.get("pageNo").asInt();
		int totalCount = data.get("totalCount").asInt();
		
		List<AreaCodeDto> areaList = new ArrayList<AreaCodeDto>();
		
		for(JsonNode node : data.get("items").get("item")) {
			AreaCodeDto area = new AreaCodeDto.Builder().setRnum(node.get("rnum").asText()).setCode(node.get("code").asText()).setName(node.get("name").asText()).build();
			areaList.add(area);
		}
		
		CommonListDto<AreaCodeDto> list = new CommonListDto<AreaCodeDto>(areaList, numOfRows, pageNo, totalCount);
		
		return list;
	}

	@Override
	public CommonListDto<CommonBasedDto> getAreaList(OpenApiDto openApiDto) throws Exception {
		String apiUrl = baseUrl+"/areaBasedList1?ServiceKey="+serviceKey
				+"&MobileOS="+mobileOS
				+"&MobileApp="+mobileApp
				+"&numOfRows="+openApiDto.getNumOfRows()
				+"&pageNo="+openApiDto.getPageNo()
				+"&contentTypeId="+openApiDto.getContentTypeId()
				+"&areaCode="+openApiDto.getAreaCode()
				+"&_type=json";

		JsonNode data = getApiData(apiUrl);
		if(data == null) {
			throw new EmptyData();
		}
		
		int numOfRows = data.get("numOfRows").asInt();
		int pageNo = data.get("pageNo").asInt();
		int totalCount = data.get("totalCount").asInt();
		
		List<CommonBasedDto> areaBasedList = new ArrayList<CommonBasedDto>();
		
		for(JsonNode node : data.get("items").get("item")) {
			CommonBasedDto areaBased = new CommonBasedDto.Builder()
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
					.setContentId(node.get("contentid").asText())
					.setContentTypeId(node.get("contenttypeid").asText())
					.setCreatedTime(node.get("createdtime").asText())
					.setFirstImage(node.get("firstimage").asText())
					.setFirstImage2(node.get("firstimage2").asText())
					.setMapx(node.get("mapx").asText())
					.setMapy(node.get("mapy").asText())
					.setMlevel(node.get("mlevel").asText())
					.setModifiedTime(node.get("modifiedtime").asText())
					.setZipcode(node.get("zipcode").asText())
					.build();
			areaBasedList.add(areaBased);
		}
		
		CommonListDto<CommonBasedDto> list = new CommonListDto<CommonBasedDto>(areaBasedList, numOfRows, pageNo, totalCount);
		
		return list;
	}

	@Override
	public CommonListDto<CommonBasedDto> getLocationBasedList(OpenApiDto openApiDto) throws Exception {
		String apiUrl = baseUrl+"/areaBasedList1?ServiceKey="+serviceKey
				+"&MobileOS="+mobileOS
				+"&MobileApp="+mobileApp
				+"&numOfRows="+openApiDto.getNumOfRows()
				+"&pageNo="+openApiDto.getPageNo()
				+"&mapX="+openApiDto.getMapX()
				+"&mapY="+openApiDto.getMapY()
				+"&radius="+openApiDto.getRadius()
				+"&_type=json";

		JsonNode data = getApiData(apiUrl);
		if(data == null) {
			throw new EmptyData();
		}
		
		int numOfRows = data.get("numOfRows").asInt();
		int pageNo = data.get("pageNo").asInt();
		int totalCount = data.get("totalCount").asInt();
		
		List<CommonBasedDto> locationBasedList = new ArrayList<CommonBasedDto>();
		
		for(JsonNode node : data.get("items").get("item")) {
			CommonBasedDto locationBased = new CommonBasedDto.Builder()
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
					.setContentId(node.get("contentid").asText())
					.setContentTypeId(node.get("contenttypeid").asText())
					.setCreatedTime(node.get("createdtime").asText())
					.setFirstImage(node.get("firstimage").asText())
					.setFirstImage2(node.get("firstimage2").asText())
					.setMapx(node.get("mapx").asText())
					.setMapy(node.get("mapy").asText())
					.setMlevel(node.get("mlevel").asText())
					.setModifiedTime(node.get("modifiedtime").asText())
					.setZipcode(node.get("zipcode").asText())
					.build();
			locationBasedList.add(locationBased);
		}
		
		CommonListDto<CommonBasedDto> list = new CommonListDto<CommonBasedDto>(locationBasedList, numOfRows, pageNo, totalCount);
		
		return list;
	}

	@Override
	public CommonListDto<CommonBasedDto> getKeyword(OpenApiDto openApiDto) throws Exception
	{
		String apiUrl = baseUrl+"/searchKeyword1?ServiceKey="+serviceKey
				+"&MobileOS="+mobileOS
				+"&MobileApp="+mobileApp
				+"&numOfRows="+openApiDto.getNumOfRows()
				+"&pageNo="+openApiDto.getPageNo()
				+"&contentTypeId="+openApiDto.getContentTypeId()
				+"&areaCode="+openApiDto.getAreaCode()
				+"&keyword="+URLEncoder.encode(openApiDto.getKeyword(), "UTF-8")
				+"&_type=json";
		
		JsonNode data = getApiData(apiUrl);
		if(data == null) {
			throw new EmptyData();
		}
		
		int numOfRows = data.get("numOfRows").asInt();
		int pageNo = data.get("pageNo").asInt();
		int totalCount = data.get("totalCount").asInt();
		
		List<CommonBasedDto> keywordBasedList = new ArrayList<CommonBasedDto>();
		
		for(JsonNode node : data.get("items").get("item")) {
			CommonBasedDto keywordBased = new CommonBasedDto.Builder()
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
					.setContentId(node.get("contentid").asText())
					.setContentTypeId(node.get("contenttypeid").asText())
					.setCreatedTime(node.get("createdtime").asText())
					.setFirstImage(node.get("firstimage").asText())
					.setFirstImage2(node.get("firstimage2").asText())
					.setMapx(node.get("mapx").asText())
					.setMapy(node.get("mapy").asText())
					.setMlevel(node.get("mlevel").asText())
					.setModifiedTime(node.get("modifiedtime").asText())
					.build();
			keywordBasedList.add(keywordBased);
		}
		
		CommonListDto<CommonBasedDto> list = new CommonListDto<CommonBasedDto>(keywordBasedList, numOfRows, pageNo, totalCount);
		
		return list;
	}

	@Override
	public CommonDetailDto getDetail(int contentId) throws Exception
	{
		JsonNode data = null;

		String apiUrl = baseUrl+"/detailCommon1?ServiceKey="+serviceKey
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
			throw new EmptyData();
		}
		
		JsonNode node = data.get("items").get("item").get(0);
		
		CommonDetailDto keywordBased = new CommonDetailDto.Builder()
				.setTitle(node.get("title").asText())
				.setAddr1(node.get("addr1").asText())
				.setAddr2(node.get("addr2").asText())
				.setFirstImage(node.get("firstimage").asText())
				.setFirstImage2(node.get("firstimage2").asText())
				.setMapx(node.get("mapx").asText())
				.setMapy(node.get("mapy").asText())
				.setHomepage(node.get("homepage").asText())
				.setTelname(node.get("telname").asText())
				.setZipcode(node.get("zipcode").asText())
				.setOverview(node.get("overview").asText())
				.build();

		return keywordBased;
	}

	@Override
	public JsonNode getApiData(String url) throws Exception
	{
		ObjectMapper om = new ObjectMapper();
		JsonNode data = null;

		URL uri = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) uri.openConnection();

		if(conn.getResponseCode() == 200)
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			JsonNode node = om.readTree(reader.readLine());

//			int totalCount = node.get("response").get("body").get("totalCount").asInt();
//			if(totalCount == 0) {
//				return null;
//			}
			// response > body > items
//			JsonNode dataNode = node.get("response").get("body").get("items");
			// response > body
			data = node.get("response").get("body");
			//data = ((ObjectNode) dataNode).put("totalCount", totalCount);
		}
		else {
			return null;
		}
		
		return data;
	}

}
