package com.example.demo.service;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.exception.JsonConvertException;
import com.example.demo.exception.ServerException;
import com.example.demo.util.HttpHeaderWork;
import com.example.demo.util.OembedReflection;
import com.example.demo.util.UrlHandler;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

@Service
public class OembedServiceImpl implements OembedService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final JsonParser jsonParser;
	private final UrlHandler urlHandler;
	private final OembedReflection oembedReflection;
	private final HttpHeaderWork httpHeaderWork;

	public OembedServiceImpl(UrlHandler urlHandler, OembedReflection oembedReflection, HttpHeaderWork httpHeaderWork) {
		this.urlHandler = urlHandler;
		this.oembedReflection = oembedReflection;
		this.httpHeaderWork = httpHeaderWork;
		this.jsonParser = new JsonParser();
	}
	// 캐쉬 사용으로 같은 주소값 결과는 빠르게 반환가능, key는 중복 방지위해 주소 전체로 사용
	@Cacheable(key = "#url", value = "oembedcache")
	@Override
	public ResponseEntity<JsonObject> getOembed(String url) throws IOException, NoSuchMethodException {
		logger.info("oEmbed 처리요청 : {}", url);
		// 유효한 url 문자열인지 확인
		if (urlHandler.isValidUrl(url)) {
			// url에서 provider 주소 얻기
			String providerUrl = urlHandler.getProviderUrl(url);
			// Front-End에서 Provider를 구별하기 위한 정보 헤더에 넣기 
			HttpHeaders httpHeaders = httpHeaderWork.getHttpHeaders(providerUrl);
			// reflection으로 oEmbed 정보 요청 결과를 HttpResponse 변환
			HttpResponse httpResponse = (HttpResponse) oembedReflection
					.executeGetOembedInfo(oembedReflection.findOembedProvider(providerUrl), url);
			// util 패키지에 관련 json변환 작업 클래스 작성해 분리 시도했지만 실패
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
			logger.info("oEmbed 처리완료  : {}", url);
			return new ResponseEntity<JsonObject>(jsonResult, httpHeaders, HttpStatus.OK);
		}
		logger.error("oEmbed 처리실패 : {}", url);
		throw new ServerException("해당 주소 정보를 서버에서 처리하지 못했습니다.");
	}
	
}