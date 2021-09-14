package com.example.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

@Component
public class HttpHeaderWork {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final HttpServletResponse httpServletResponse;

	public HttpHeaderWork(HttpServletResponse httpServletResponse) {
		this.httpServletResponse = httpServletResponse;
	}

	public void getHttpHeaders(String providerUrl) {
		// 정보를 담은 헤더 생성
//		httpHeaders.add("provider", providerUrl);
//		httpHeaders.add("Access-Control-Expose-Headers", "*");
		httpServletResponse.addHeader("provider", providerUrl);
		httpServletResponse.addHeader("Access-Control-Expose-Headers", "*");
		logger.info("{} - HttpHeader에 생성", providerUrl);
	}
}