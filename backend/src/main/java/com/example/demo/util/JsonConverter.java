package com.example.demo.util;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.demo.exception.JsonConvertException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

@Component
public class JsonConverter {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final JsonParser jsonParser;
	
	public JsonConverter() {
		this.jsonParser = new JsonParser();
	}
	
	public JsonObject jsonConvert(HttpResponse httpResponse) throws IOException {	
		JsonObject jsonResult = null;
		try {
			jsonResult = (JsonObject) jsonParser.parse(EntityUtils.toString(httpResponse.getEntity()));
		} catch (JsonSyntaxException | IOException e) {
			logger.error("Json변환 입출력 오류 - {} : {}", e, e.getMessage());
			throw new IOException("oEmbed Json 변환 입출력 오류가 발생했습니다.");
		}
		if (jsonResult == null) {
			logger.error("{} Json 결과 생성 실패", httpResponse);
			throw new JsonConvertException("oEmbed 정보를 Json으로 변환할 수 없습니다.");
		}
		return jsonResult;
	}
}
