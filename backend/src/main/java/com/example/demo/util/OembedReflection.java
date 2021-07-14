package com.example.demo.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.demo.annotation.OembedAnnotation;
import com.example.demo.exception.ReflectionException;
import com.example.demo.model.Oembed;

@Component
public class OembedReflection {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final Reflections reflections = new Reflections("com.example.demo.model");

	public Class<? extends Oembed> findOembedProvider(String providerUrl) {
		// 리플렉션을 사용해서 서버에서 제공하는 provider 클래스들을 조회해서 요청 주소에 해당하면 반환
		Set<Class<? extends Oembed>> subTypes = reflections.getSubTypesOf(Oembed.class);
		for (Class<? extends Oembed> type : subTypes) {
			if (providerUrl.equals(type.getAnnotation(OembedAnnotation.class).provider())) {
				logger.info("{} : {} Provider 일치", providerUrl, type);
				return type;
			}
		}
		// 예외처리
		logger.error("서버 미제공 주소 요청 {}", providerUrl);
		throw new ReflectionException("해당 주소는 서비스에서 제공하는 Provider가 아닙니다.");
	}

	public Object executeGetOembedInfo(Class<? extends Oembed> oEmbedProvider, String url)
			throws NoSuchMethodException {
		// 해당 클래스에서 getOembedInfo 메서드를 찾아서 반환
		Method method = null;
		try {
			method = oEmbedProvider.getDeclaredMethod("getOembedInfo", String.class);
		} catch (NoSuchMethodException | SecurityException e) {
			logger.error("{} 해당하는 메서드 미존재 {} : {}", url, e, e.getMessage());
			throw new ReflectionException("해당 주소의  oEmbed 정보 요청 처리가 불가능 합니다.");
		}
		// 예외처리
		if (method == null) {
			logger.error("oEmbed 정보 반환 실패 : {}", url);
			throw new ReflectionException("해당 주소의  oEmbed 정보 반환에 실패했습니다.");
		}
		// 해당 메서드로 처리한 결과 객체를 반환
		Object result = null;
		try {
			result = method.invoke(oEmbedProvider.newInstance(), url);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| InstantiationException e) {
			logger.error("{} 주소  oEmbed 결과 반환 실패  - {} : {}", url, e, e.getMessage());
			throw new ReflectionException("해당 주소의  oEmbed 정보 반환에 실패했습니다.");
		}
		// 예외처리
		if (result == null) {
			logger.error("oEmbed 정보 반환 실패 : {}", url);
			throw new ReflectionException("해당 주소의  oEmbed 정보 반환에 실패했습니다.");
		}
		logger.info("oEmbed 요청 결과 반환 성공 : {}", url);
		return result;
	}
}