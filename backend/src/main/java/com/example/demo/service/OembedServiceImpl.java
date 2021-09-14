package com.example.demo.service;

import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.util.HttpHeaderWork;
import com.example.demo.util.JsonConverter;
import com.example.demo.util.OembedReflection;
import com.example.demo.util.UrlHandler;
import com.google.gson.JsonObject;

@Service
public class OembedServiceImpl implements OembedService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final UrlHandler urlHandler;
	private final OembedReflection oembedReflection;
	private final HttpHeaderWork httpHeaderWork;
	private final JsonConverter jsonConverter;

	public OembedServiceImpl(UrlHandler urlHandler, OembedReflection oembedReflection, HttpHeaderWork httpHeaderWork, JsonConverter jsonConverter) {
		this.urlHandler = urlHandler;
		this.oembedReflection = oembedReflection;
		this.httpHeaderWork = httpHeaderWork;
		this.jsonConverter = jsonConverter;
	}
	// 캐쉬 사용으로 같은 주소값 결과는 빠르게 반환가능, key는 중복 방지위해 주소 전체로 사용
	@Cacheable(key = "#url", value = "oembedcache")
	@Override
	public JsonObject getOembed(String url) {
		logger.info("oEmbed 처리요청 : {}", url);
		// 유효한 url 문자열인지 확인
		if (urlHandler.isValidUrl(url)) {
			// url에서 provider 주소 얻기
			String providerUrl = urlHandler.getProviderUrl(url);
			// Front-End에서 Provider를 구별하기 위한 정보 헤더에 넣기 
			//HttpHeaders httpHeaders = httpHeaderWork.getHttpHeaders(providerUrl);
			httpHeaderWork.getHttpHeaders(providerUrl);
			// reflection으로 oEmbed 정보 요청 결과를 HttpResponse 변환
			HttpResponse httpResponse = (HttpResponse) oembedReflection
					.executeGetOembedInfo(oembedReflection.findOembedProvider(providerUrl), url);
			JsonObject jsonResult = jsonConverter.jsonConvert(httpResponse);
			logger.info("oEmbed 처리완료 : {}", url);
			return jsonResult;
		} else {
			logger.error("oEmbed 처리실패 : {}", url);
			return null;
		}
	}
	
}