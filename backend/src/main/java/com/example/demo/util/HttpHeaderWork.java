package com.example.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.example.demo.exception.HttpHeaderException;

@Component
public class HttpHeaderWork {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public HttpHeaders getHttpHeaders(String providerUrl) throws HttpHeaderException {
		// 정보를 담은 헤더 생성
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("provider", providerUrl);
		httpHeaders.add("Access-Control-Expose-Headers", "*");
		// 예외처리
		if (httpHeaders.isEmpty()) {
			logger.error("HttpHeader 생성 실패 : {}", providerUrl);
			throw new HttpHeaderException("HttpHeader 생성을 실패했습니다.");
		}
		logger.info("{} HttpHeader에 생성 성공", providerUrl);
		return httpHeaders;
	}
}