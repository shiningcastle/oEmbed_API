package com.example.demo.model;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import com.example.demo.annotation.OembedAnnotation;
import com.example.demo.exception.OembedProviderException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@OembedAnnotation(provider = "www.youtube.com")
@Component
public class Youtube extends Oembed {

	private static String oEmbedUrl;

	@Value("${url.youtube}")
	void setOembedUrl(String oEmbedUrl) {
		this.oEmbedUrl = oEmbedUrl;
	}

	@Override
	public HttpResponse getOembedInfo(String url) {
		// 요청 주소 = 기본 Provider oEmbed 주소 + 사용자 입력 파라미터 주소
		String oembedUrl = oEmbedUrl + url;
		// 해당 주소로 Http Get요청해서 oEmbed 정보 반환
		HttpResponse oembedInfo = null;
		try {
			oembedInfo = HttpClientBuilder.create().build().execute(new HttpGet(oembedUrl));
		} catch (ClientProtocolException e) {
			logger.error("{} Youtube oEmbed API 요청 실패 : {} : {}", url, e, e.getMessage());
		} catch (IOException e) {
			logger.error("{} Youtube oEmbed API 요청 실패 : {} : {}", url, e, e.getMessage());
		}
		if (oembedInfo == null) {
			logger.error("Youtube oEmbed API 결과 반환 실패 : {}", url);
			throw new OembedProviderException("Youtube oEmbed API 정보 요청 실패");
		}
		logger.info("Youtube oEmbed API 결과 반환 성공 : {}", url);
		return oembedInfo;
	}
}