package com.example.demo.model;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.annotation.OembedAnnotation;
import com.example.demo.exception.OembedProviderException;
import com.example.demo.exception.ReflectionException;

@OembedAnnotation(provider = "www.youtube.com")
public class Youtube extends Oembed {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final String YOUTUBE_OEMBED_URL = "https://www.youtube.com/oembed?url=";

	@Override
	public HttpResponse getOembedInfo(String url) throws ClientProtocolException {
		// 요청 주소 = 기본 Provider oEmbed 주소 + 사용자 입력 파라미터 주소
		String oembedUrl = YOUTUBE_OEMBED_URL + url;
		// 해당 주소로 Http Get요청해서 oEmbed 정보 반환
		HttpClient httpclient = HttpClientBuilder.create().build();
		HttpGet httpget = new HttpGet(oembedUrl);
		HttpResponse oembedInfo = null;
		try {
			oembedInfo = httpclient.execute(httpget);
		} catch (ClientProtocolException e) {
			logger.error("{} Youtube oEmbed API 요청 실패 : {} : {}", url, e, e.getMessage());
			throw new ClientProtocolException("Youtube oEmbed API 요청에 실패했습니다.");
		} catch (IOException e) {
			logger.error("{} Youtube oEmbed API 요청 실패 : {} : {}", url, e, e.getMessage());
			throw new OembedProviderException("Youtube oEmbed API 요청에 실패했습니다.");
		}
		if (oembedInfo == null) {
			logger.error("Youtube oEmbed API 결과 반환 실패 : {}", url);
			throw new ReflectionException("Youtube oEmbed API 결과 반환에 실패했습니다.");
		}
		logger.info("Youtube oEmbed API 결과 반환 성공 : {}", url);
		return oembedInfo;
	}
}