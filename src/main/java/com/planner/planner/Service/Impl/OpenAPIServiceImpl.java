package com.planner.planner.Service.Impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.planner.planner.Config.Properites.CommonProperties;
import com.planner.planner.Dto.OpenApi.AreaCodeDto;
import com.planner.planner.Dto.OpenApi.CommonBasedDto;
import com.planner.planner.Dto.OpenApi.CommonDetailDto;
import com.planner.planner.Dto.OpenApi.CommonListDto;
import com.planner.planner.Dto.OpenApi.OpenApiDto;
import com.planner.planner.Exception.EmptyData;
import com.planner.planner.Exception.NoValidArgumentException;
import com.planner.planner.Service.OpenAPIService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OpenAPIServiceImpl implements OpenAPIService {

	private final CommonProperties commonProperties;

	// private int numOfRows = 10; 요청으로 받는 값으로 변경

	@Override
	public CommonListDto<AreaCodeDto> getAreaNum() throws Exception{
		String apiUrl = commonProperties.getOpenApi().getBaseUrl()+"/areaCode1?ServiceKey="+commonProperties.getOpenApi().getServiceKey()
				+"&MobileOS="+commonProperties.getOpenApi().getMobileOS()
				+"&MobileApp="+commonProperties.getOpenApi().getMobileAPP()
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
			AreaCodeDto area = AreaCodeDto.builder().rnum(node.get("rnum").asText()).code(node.get("code").asText()).name(node.get("name").asText()).build();
			areaList.add(area);
		}
		
		CommonListDto<AreaCodeDto> list = new CommonListDto<AreaCodeDto>(areaList, numOfRows, pageNo, totalCount);
		
		return list;
	}

	@Override
	public CommonListDto<CommonBasedDto> getAreaList(OpenApiDto openApiDto) throws Exception {
		String apiUrl = commonProperties.getOpenApi().getBaseUrl()+"/areaBasedList1?ServiceKey="+commonProperties.getOpenApi().getServiceKey()
				+"&MobileOS="+commonProperties.getOpenApi().getMobileOS()
				+"&MobileApp="+commonProperties.getOpenApi().getMobileAPP()
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
			CommonBasedDto areaBased = CommonBasedDto.builder()
					.sigunguCode(node.get("sigungucode").asText())
					.tel(node.get("tel").asText())
					.title(node.get("title").asText())
					.addr1(node.get("addr1").asText())
					.addr2(node.get("addr2").asText())
					.areaCode(node.get("areacode").asText())
					.bookTour(node.get("booktour").asText())
					.cat1(node.get("cat1").asText())
					.cat2(node.get("cat2").asText())
					.cat3(node.get("cat3").asText())
					.contentId(node.get("contentid").asText())
					.contentTypeId(node.get("contenttypeid").asText())
					.createdTime(node.get("createdtime").asText())
					.firstImage(node.get("firstimage").asText())
					.firstImage2(node.get("firstimage2").asText())
					.mapx(node.get("mapx").asText())
					.mapy(node.get("mapy").asText())
					.mlevel(node.get("mlevel").asText())
					.modifiedTime(node.get("modifiedtime").asText())
					.zipcode(node.get("zipcode").asText())
					.build();
			areaBasedList.add(areaBased);
		}
		
		CommonListDto<CommonBasedDto> list = new CommonListDto<CommonBasedDto>(areaBasedList, numOfRows, pageNo, totalCount);
		
		return list;
	}

	@Override
	public CommonListDto<CommonBasedDto> getLocationBasedList(OpenApiDto openApiDto) throws Exception {
		String apiUrl = commonProperties.getOpenApi().getBaseUrl()+"/areaBasedList1?ServiceKey="+commonProperties.getOpenApi().getServiceKey()
				+"&MobileOS="+commonProperties.getOpenApi().getMobileOS()
				+"&MobileApp="+commonProperties.getOpenApi().getMobileAPP()
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
			CommonBasedDto locationBased = CommonBasedDto.builder()
					.sigunguCode(node.get("sigungucode").asText())
					.tel(node.get("tel").asText())
					.title(node.get("title").asText())
					.addr1(node.get("addr1").asText())
					.addr2(node.get("addr2").asText())
					.areaCode(node.get("areacode").asText())
					.bookTour(node.get("booktour").asText())
					.cat1(node.get("cat1").asText())
					.cat2(node.get("cat2").asText())
					.cat3(node.get("cat3").asText())
					.contentId(node.get("contentid").asText())
					.contentTypeId(node.get("contenttypeid").asText())
					.createdTime(node.get("createdtime").asText())
					.firstImage(node.get("firstimage").asText())
					.firstImage2(node.get("firstimage2").asText())
					.mapx(node.get("mapx").asText())
					.mapy(node.get("mapy").asText())
					.mlevel(node.get("mlevel").asText())
					.modifiedTime(node.get("modifiedtime").asText())
					.zipcode(node.get("zipcode").asText())
					.build();
			locationBasedList.add(locationBased);
		}
		
		CommonListDto<CommonBasedDto> list = new CommonListDto<CommonBasedDto>(locationBasedList, numOfRows, pageNo, totalCount);
		
		return list;
	}

	@Override
	public CommonListDto<CommonBasedDto> getKeyword(OpenApiDto openApiDto) throws Exception
	{
		if(openApiDto.getKeyword() == null || openApiDto.getKeyword().isEmpty()) {
			throw new NoValidArgumentException("키워드는 공백이거나 빈칸일 수 었습니다.");
		}
		
		String apiUrl = commonProperties.getOpenApi().getBaseUrl()+"/searchKeyword1?ServiceKey="+commonProperties.getOpenApi().getServiceKey()
				+"&MobileOS="+commonProperties.getOpenApi().getMobileOS()
				+"&MobileApp="+commonProperties.getOpenApi().getMobileAPP()
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
		JsonNode itemList = data.get("items").get("item");
		
		List<CommonBasedDto> keywordBasedList = new ArrayList<CommonBasedDto>();
		
		if(itemList != null) {
			for(JsonNode node : itemList) {
				CommonBasedDto keywordBased = CommonBasedDto.builder()
						.sigunguCode(node.get("sigungucode").asText())
						.tel(node.get("tel").asText())
						.title(node.get("title").asText())
						.addr1(node.get("addr1").asText())
						.addr2(node.get("addr2").asText())
						.areaCode(node.get("areacode").asText())
						.bookTour(node.get("booktour").asText())
						.cat1(node.get("cat1").asText())
						.cat2(node.get("cat2").asText())
						.cat3(node.get("cat3").asText())
						.contentId(node.get("contentid").asText())
						.contentTypeId(node.get("contenttypeid").asText())
						.createdTime(node.get("createdtime").asText())
						.firstImage(node.get("firstimage").asText())
						.firstImage2(node.get("firstimage2").asText())
						.mapx(node.get("mapx").asText())
						.mapy(node.get("mapy").asText())
						.mlevel(node.get("mlevel").asText())
						.modifiedTime(node.get("modifiedtime").asText())
						.build();
				keywordBasedList.add(keywordBased);
			}
		}
		
		CommonListDto<CommonBasedDto> list = new CommonListDto<CommonBasedDto>(keywordBasedList, numOfRows, pageNo, totalCount);
		
		return list;
	}

	@Override
	public CommonDetailDto getDetail(int contentId) throws Exception
	{
		JsonNode data = null;

		String apiUrl = commonProperties.getOpenApi().getBaseUrl()+"/detailCommon1?ServiceKey="+commonProperties.getOpenApi().getServiceKey()
				+"&MobileOS="+commonProperties.getOpenApi().getMobileOS()
				+"&MobileApp="+commonProperties.getOpenApi().getMobileAPP()
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
		
		CommonDetailDto keywordBased = CommonDetailDto.builder()
				.title(node.get("title").asText())
				.addr1(node.get("addr1").asText())
				.addr2(node.get("addr2").asText())
				.firstImage(node.get("firstimage").asText())
				.firstImage2(node.get("firstimage2").asText())
				.mapx(node.get("mapx").asText())
				.mapy(node.get("mapy").asText())
				.homepage(node.get("homepage").asText())
				.telname(node.get("telname").asText())
				.zipcode(node.get("zipcode").asText())
				.overview(node.get("overview").asText())
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
			String readData = reader.readLine();
			JsonNode node = om.readTree(readData);
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
