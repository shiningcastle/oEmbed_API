package com.example.demo.util;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.demo.exception.UrlException;

@Component
public class UrlHandler {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final UrlValidator urlValidator;

	public UrlHandler() {
		this.urlValidator = new UrlValidator();
	}

	public boolean isValidUrl(String url) {
		// 문자열이 유효한 url이면 true
		boolean result = (urlValidator.isValid(url)) ? true : false;
		// 예외처리
		if (!result) {
			logger.error("유효하지 않은  url 요청시도 : {}", url);
			throw new UrlException("유효하지 않은 주소입니다.");
		}
		logger.info("유효한 url 요청시도 : {}", url);
		return result;
	}

	public String getProviderUrl(String url) throws MalformedURLException {
		// 해당 주소에서 Host 주소 부분만 뽑아내기
		String hostUrl = null;
		try {
			hostUrl = new URL(url).getHost();
		} catch (MalformedURLException e) {
			logger.error("{} Host 주소 반환 불가 - {} : {}", e, e.getMessage());
			throw new MalformedURLException("해당 주소의 Provider를 조회할 수 없습니다.");
		}
		// 예외처리
		if (hostUrl == null) {
			throw new UrlException("Provider가 불분명한 주소입니다.");
		}
		logger.info("유효한 Provider 판별 : {}", url);
		return hostUrl;
	}
}