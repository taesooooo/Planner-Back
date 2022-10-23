package com.planner.planner.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Component
public class OpenAPIUtil {

	public OpenAPIUtil() {

	}

	public static ObjectNode getApiData(String url) throws Exception {
		ObjectMapper om = new ObjectMapper();
		ObjectNode data = null;

		URL uri = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) uri.openConnection();

		if (conn.getResponseCode() == 200) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			JsonNode node = om.readTree(reader.readLine());

			int totalCount = node.get("response").get("body").get("totalCount").asInt();
			if (totalCount == 0) {
				throw new Exception("API 서버에서 데이터를 가져올 수 없습니다.");
			}
			// response > body > items
			JsonNode dataNode = node.get("response").get("body").get("items");
			data = ((ObjectNode) dataNode).put("totalCount", totalCount);
		} 
		else {
			return new ObjectNode(JsonNodeFactory.instance)
					.putObject("error")
					.put("code", conn.getResponseCode())
					.put("message", conn.getResponseMessage());
		}

		return data;
	}
}
