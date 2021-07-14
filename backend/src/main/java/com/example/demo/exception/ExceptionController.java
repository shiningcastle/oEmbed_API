package com.example.demo.exception;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.apache.http.client.ClientProtocolException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.google.gson.JsonSyntaxException;

@RestControllerAdvice
public class ExceptionController {
	// UrlHandler 에러
	@ExceptionHandler({ UrlException.class, MalformedURLException.class })
	public ResponseEntity<Map<String, String>> UrlExceptionResponse(Exception e) {
		Map<String, String> errorResponse = new HashedMap();
		errorResponse.put("errMsg", e.getMessage());
		return new ResponseEntity<Map<String, String>>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	// Reflection 에러
	@ExceptionHandler({ ReflectionException.class, IllegalAccessException.class, IllegalArgumentException.class,
			InvocationTargetException.class, InstantiationException.class, NoSuchMethodException.class,
			SecurityException.class })
	public ResponseEntity<Map<String, String>> ReflectionExceptionResponse(Exception e) {
		Map<String, String> errorResponse = new HashedMap();
		errorResponse.put("errMsg", e.getMessage());
		return new ResponseEntity<Map<String, String>>(errorResponse, HttpStatus.NOT_FOUND);
	}

	// Provider oEmbed 정보 요청 에러
	@ExceptionHandler({ OembedProviderException.class , ClientProtocolException.class })
	public ResponseEntity<Map<String, String>> ProviderExceptionResponse(Exception e) {
		Map<String, String> errorResponse = new HashedMap();
		errorResponse.put("errMsg", e.getMessage());
		return new ResponseEntity<Map<String, String>>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// HttpHeader 생성 에러
	@ExceptionHandler({ HttpHeaderException.class })
	public ResponseEntity<Map<String, String>> HttpHeaderExceptionResponse(Exception e) {
		Map<String, String> errorResponse = new HashedMap();
		errorResponse.put("errMsg", e.getMessage());
		return new ResponseEntity<Map<String, String>>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// Json 생성 에러
	@ExceptionHandler({ JsonConvertException.class, IOException.class, JsonSyntaxException.class })
	public ResponseEntity<Map<String, String>> JsonConvertExceptionResponse(Exception e) {
		Map<String, String> errorResponse = new HashedMap();
		errorResponse.put("errMsg", e.getMessage());
		return new ResponseEntity<Map<String, String>>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// 그외 Server 처리 에러
	@ExceptionHandler({ ServerException.class })
	public ResponseEntity<Map<String, String>> ServerExceptionResponse(Exception e) {
		Map<String, String> errorResponse = new HashedMap();
		errorResponse.put("errMsg", e.getMessage());
		return new ResponseEntity<Map<String, String>>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}