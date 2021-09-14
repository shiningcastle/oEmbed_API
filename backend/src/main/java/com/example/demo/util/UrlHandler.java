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
		if (!urlValidator.isValid(url)) {
			logger.error("유효하지 않은 URL : {}", url);
			throw new UrlException("유효하지 않은 주소입니다.");
		}
		logger.info("유효한 URL : {}", url);
		return true;
	}

	public String getProviderUrl(String url) {
		// 해당 주소에서 Host 주소 부분만 추출
		String hostUrl = null;
		try {
			hostUrl = new URL(url).getHost();
		} catch (MalformedURLException e) {
			logger.error("유효하지 않은 Host : {}", url);
		}
		// 예외처리
		if (hostUrl == null) {
			throw new UrlException("Provider가 불분명한 주소입니다.");
		}
		logger.info("유효한 HOST : {}", hostUrl);
		return hostUrl;
	}
}